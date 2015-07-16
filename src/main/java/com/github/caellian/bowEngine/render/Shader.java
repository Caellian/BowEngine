/*
 * BowEngine, modular and easy to use game engine
 * Copyright (C) 2015 Caellian
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.caellian.bowEngine.render;

import com.github.caellian.bowEngine.core.Transform;
import com.github.caellian.bowEngine.core.lib.Reference;
import com.github.caellian.bowEngine.core.math.Matrix4f;
import com.github.caellian.bowEngine.core.math.Vector3f;
import com.github.caellian.bowEngine.core.util.helpers.DataUtil;
import com.github.caellian.bowEngine.render.components.light.BaseLight;
import com.github.caellian.bowEngine.render.components.light.DirectionalLight;
import com.github.caellian.bowEngine.render.components.light.PointLight;
import com.github.caellian.bowEngine.render.components.light.SpotLight;
import com.github.caellian.bowEngine.render.resources.ShaderResource;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public class Shader
{
	private static final HashMap<String, ShaderResource> loadedShaders = new HashMap<>();

	private ShaderResource resource;

	private String fileName;

	public Shader(String fileName)
	{
		this.fileName = fileName;
		resource = loadedShaders.get(fileName);

		if (resource != null)
		{
			resource.increaseReference();
		}
		else
		{
			resource = new ShaderResource();
			if (resource.getProgram() == 0)
			{
				System.err.println("Shader creation failed: Could not find valid memory location in constructor");
				System.exit(1);
			}

			String vertexShaderText = loadShader(fileName + Reference.Extensions.VERTEX_SHADER_EXTENSION);
			String fragmentShaderText = loadShader(fileName + Reference.Extensions.FRAGMENT_SHADER_EXTENSION);

			addVertexShader(vertexShaderText);
			addFragmentShader(fragmentShaderText);
			addAllAttributes(vertexShaderText);

			compileShader();

			addAllUniforms(vertexShaderText);
			addAllUniforms(fragmentShaderText);

			loadedShaders.put(fileName, resource);
		}
	}

	private static String loadShader(@NotNull String fileName)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader;

		try
		{
			shaderReader = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = shaderReader.readLine()) != null)
			{
				if (line.startsWith(Reference.FileParsing.INCLUDE_DIRECTIVE))
				{
					int BSB = 0;
					for (char character : line.toCharArray())
					{
						if (character == ' ')
						{
							BSB++;
						}
						else
						{
							break;
						}
					}
					int BSA = 0;
					CharBuffer buffer = DataUtil.createFlippedBuffer(line.toCharArray());
					for (char character : buffer.toString().toCharArray())
					{
						if (character == ' ')
						{
							BSA++;
						}
						else
						{
							break;
						}
					}
					String name = line.substring(BSB + Reference.FileParsing.INCLUDE_DIRECTIVE.length() + 2, line.length() - 1 - BSA);
					shaderSource.append(loadShader(name));
				}
				else
				{
					shaderSource.append(line).append("\n");
				}
			}

			shaderReader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		return shaderSource.toString();
	}

	public void bind()
	{
		glUseProgram(resource.getProgram());
	}

	public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f MVPMatrix = renderingEngine.getCurrentCamera().getViewProjection().mul(worldMatrix);

		for (int uniformIndex = 0; uniformIndex < resource.getUniformNames().size(); uniformIndex++)
		{
			try
			{
				String uniformName = resource.getUniformNames().get(uniformIndex);
				String uniformType = resource.getUniformTypes().get(uniformIndex);

				switch (uniformName.substring(0, 2))
				{
					case "T_":
						switch (uniformName)
						{
							case "T_MVP":
								setUniform(uniformName, MVPMatrix);
								break;
							case "T_model":
								setUniform(uniformName, worldMatrix);
								break;
							default:
								throw new IllegalArgumentException(uniformName + " is not a valid component of Transform!");
						}
						break;
					case "R_":
						String rUnprefixedUniformName = uniformName.substring(2);
						switch (uniformType)
						{
							case "sampler2D":
								int samplerSlot = renderingEngine.getSamplerSlot(rUnprefixedUniformName);
								material.getTexture(rUnprefixedUniformName).bind(samplerSlot);
								setUniform(uniformName, samplerSlot);
								break;
							case "vec3":
								setUniform(uniformName, renderingEngine.getVector3fHashMap().get(rUnprefixedUniformName));
								break;
							case "float":
								setUniform(uniformName, renderingEngine.getFloatHashMap().get(rUnprefixedUniformName));
								break;
							case "DirectionalLight":
								setUniformDirectionalLight(uniformName, (DirectionalLight) renderingEngine.getActiveLight());
								break;
							case "PointLight":
								setUniformPointLight(uniformName, (PointLight) renderingEngine.getActiveLight());
								break;
							case "SpotLight":
								setUniformSpotLight(uniformName, (SpotLight) renderingEngine.getActiveLight());
								break;
							default:
								renderingEngine.updateUniformStruct(transform, material, this, uniformName, uniformType);
						}
						break;
					case "C_":
						switch (uniformName)
						{
							case "C_eyePos":
								setUniform(uniformName, renderingEngine.getCurrentCamera().getTransform().getTransformedPosition());
								break;
							default:
								renderingEngine.getCurrentCamera().updateUniformStruct(transform, material, this, uniformName, uniformType);
						}
						break;
					case "M_":
						String mUnprefixedUniformName = uniformName.substring(2);
						switch (uniformType)
						{
							case "vec3":
								setUniform(uniformName, material.getVector3fHashMap().get(mUnprefixedUniformName));
								break;
							case "float":
								setUniform(uniformName, material.getFloatHashMap().get(mUnprefixedUniformName));
								break;
							default:
								material.updateUniformStruct(transform, material, this, uniformName, uniformType);
						}
						break;
					default:
						renderingEngine.updateUndefinedUniformStruct(transform, material, this, uniformName, uniformType);
				}
			} catch (NullPointerException npe)
			{
				System.err.println("Received Null Pointer Exception: for uniform '" + resource.getUniformNames().get(uniformIndex) + "', of type: '" + resource.getUniformTypes().get(uniformIndex) + "', while rendering:" + transform.toString());
			}
		}
	}

	private void addAllAttributes(@NotNull String shaderText)
	{
		int attributeStartLocation = shaderText.indexOf(Reference.FileParsing.OPENGL_ATTRIBUTE);
		int attributeNumber = 0;
		while (attributeStartLocation != -1)
		{
			int begin = attributeStartLocation + Reference.FileParsing.OPENGL_ATTRIBUTE.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String attributeLine = shaderText.substring(begin, end);
			String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length());

			setAttributeLocation(attributeName, attributeNumber++);

			attributeStartLocation = shaderText.indexOf(Reference.FileParsing.OPENGL_ATTRIBUTE, attributeStartLocation + Reference.FileParsing.OPENGL_ATTRIBUTE.length());
		}
	}

	@NotNull
	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(@NotNull String shaderText)
	{
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<>();

		int structStartLocation = shaderText.indexOf(Reference.FileParsing.OPENGL_STRUCTURE);
		while (structStartLocation != -1)
		{
			int nameBegin = structStartLocation + Reference.FileParsing.OPENGL_STRUCTURE.length() + 1;
			int braceBegin = shaderText.indexOf("{", nameBegin);
			int braceEnd = shaderText.indexOf("}", braceBegin);

			String structName = shaderText.substring(nameBegin, braceBegin).trim();
			ArrayList<GLSLStruct> glslStructs = new ArrayList<>();

			int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
			while (componentSemicolonPos != -1 && componentSemicolonPos < braceEnd)
			{
				int componentNameStart = componentSemicolonPos;

				while (!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
				{
					componentNameStart--;
				}

				int componentTypeEnd = componentNameStart - 1;
				int componentTypeStart = componentTypeEnd;

				while (!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
				{
					componentTypeStart--;
				}

				String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
				String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

				GLSLStruct glslStruct = new GLSLStruct();
				glslStruct.name = componentName;
				glslStruct.type = componentType;

				glslStructs.add(glslStruct);

				componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
			}

			result.put(structName, glslStructs);

			structStartLocation = shaderText.indexOf(Reference.FileParsing.OPENGL_STRUCTURE, structStartLocation + Reference.FileParsing.OPENGL_STRUCTURE.length());
		}

		return result;
	}

	private void addAllUniforms(@NotNull String shaderText)
	{
		HashMap<String, ArrayList<GLSLStruct>> structs = findUniformStructs(shaderText);

		int uniformStartLocation = shaderText.indexOf(Reference.FileParsing.OPENGL_UNIFORM);
		while (uniformStartLocation != -1)
		{
			int begin = uniformStartLocation + Reference.FileParsing.OPENGL_UNIFORM.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String uniformLine = shaderText.substring(begin, end);

			String uniformName = uniformLine.split(" ")[1];
			String uniformType = uniformLine.split(" ")[0];

			resource.getUniformNames().add(uniformName);
			resource.getUniformTypes().add(uniformType);
			addUniform(uniformName, uniformType, structs);

			uniformStartLocation = shaderText.indexOf(Reference.FileParsing.OPENGL_UNIFORM, uniformStartLocation + Reference.FileParsing.OPENGL_UNIFORM.length());
		}
	}

	private void addUniform(@NotNull String uniformName, String uniformType, @NotNull HashMap<String, ArrayList<GLSLStruct>> structs)
	{
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

		if (structComponents != null)
		{
			addThis = false;
			for (GLSLStruct struct : structComponents)
			{
				addUniform(uniformName + "." + struct.name, struct.type, structs);
			}
		}

		if (addThis)
		{
			int uniformLocation = glGetUniformLocation(resource.getProgram(), uniformName);

			if (uniformLocation == 0xFFFFFFFF)
			{
				System.err.println("Error: Could not find uniform: " + uniformName);
				new Exception().printStackTrace();
				System.exit(1);
			}

			resource.getUniforms().put(uniformName, uniformLocation);
		}
	}

	private void addVertexShader(@NotNull String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}

	@SuppressWarnings("unused")
	private void addGeometryShader(@NotNull String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}

	private void addProgram(@NotNull String text, int type)
	{
		int shader = glCreateShader(type);

		if (shader == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}

		glShaderSource(shader, text);
		glCompileShader(shader);

		if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}

		glAttachShader(resource.getProgram(), shader);
	}

	private void addFragmentShader(@NotNull String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}

	private void setAttributeLocation(@NotNull String attributeName, int location)
	{
		glBindAttribLocation(resource.getProgram(), location, attributeName);
	}

	private void compileShader()
	{
		glLinkProgram(resource.getProgram());

		if (glGetProgrami(resource.getProgram(), GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}

		glValidateProgram(resource.getProgram());

		if (glGetProgrami(resource.getProgram(), GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
	}

	@SuppressWarnings("unused")
	protected void setUniform(String uniformName, int value)
	{
		glUniform1i(resource.getUniforms().get(uniformName), value);
	}

	@SuppressWarnings("WeakerAccess")
	protected void setUniform(String uniformName, float value)
	{
		glUniform1f(resource.getUniforms().get(uniformName), value);
	}

	@SuppressWarnings("WeakerAccess")
	protected void setUniform(String uniformName, @NotNull Vector3f value)
	{
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}

	@SuppressWarnings("WeakerAccess")
	protected void setUniform(String uniformName, @NotNull Matrix4f value)
	{
		glUniformMatrix4(resource.getUniforms().get(uniformName), true, DataUtil.createFlippedBuffer(value));
	}

	protected void setUniformSpotLight(String uniformName, @NotNull SpotLight spotLight)
	{
		setUniformPointLight(uniformName + ".pointLight", spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniform(uniformName + ".cutoff", spotLight.getCutoff());
	}

	protected void setUniformPointLight(String uniformName, @NotNull PointLight pointLight)
	{
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniform(uniformName + ".attenuation.constant", pointLight.getAttenuation().getConstant());
		setUniform(uniformName + ".attenuation.linear", pointLight.getAttenuation().getLinear());
		setUniform(uniformName + ".attenuation.exponent", pointLight.getAttenuation().getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPosition());
		setUniform(uniformName + ".range", pointLight.getRange());
	}

	protected void setUniformBaseLight(String uniformName, @NotNull BaseLight baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}

	protected void setUniformDirectionalLight(@SuppressWarnings("SameParameterValue") String uniformName, @NotNull DirectionalLight directionalLight)
	{
		setUniformBaseLight(uniformName + ".base", directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}

	private class GLSLStruct
	{
		public String name;
		public String type;
	}
}
