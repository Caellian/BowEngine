/*
 * Software developed by Skythees
 * Copyright (C) 2015 Skythees
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.render;

import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.core.math.Matrix4f;
import com.skythees.bowEngine.core.math.Vector3f;
import com.skythees.bowEngine.core.util.helpers.DataUtil;
import com.skythees.bowEngine.render.components.light.BaseLight;
import com.skythees.bowEngine.render.components.light.DirectionalLight;
import com.skythees.bowEngine.render.components.light.PointLight;
import com.skythees.bowEngine.render.components.light.SpotLight;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;

public abstract class Shader {

    private final int program;
    private HashMap<String, Integer> uniforms = new HashMap<>();

    @SuppressWarnings("WeakerAccess")
    public Shader() {
        program = glCreateProgram();

        if (program == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location in constructor");
            System.exit(1);
        }
    }

    protected static String loadInternalShader(String shader) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;
        try {
            File shaderFile = new File("resources/shaders/" + shader);
            System.out.println(shaderFile.getAbsolutePath());
//            File shaderFile = new File(Reference.getURLPath("resources/shaders/") + shader);
            shaderReader = new BufferedReader(new FileReader(shaderFile));
            String line;

            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static String loadShader(String shader) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;
        try {
            File shaderFile = new File("resources/shaders/" + shader);
            shaderReader = new BufferedReader(new FileReader(shaderFile));
            String line;

            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    @SuppressWarnings("UnusedDeclaration")
    public void bind() {
        glUseProgram(program);
    }

    @SuppressWarnings("UnusedDeclaration")
    public abstract void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine);

    @SuppressWarnings("WeakerAccess")
    public void addUniform(String uniform) {
        int uniformLocation = glGetUniformLocation(program, uniform);

        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Error: Could not find uniform: " + uniform);
            new Exception().printStackTrace();
            System.exit(1);
        }

        uniforms.put(uniform, uniformLocation);
    }

    @SuppressWarnings("WeakerAccess")
    public void addVertexShader(String text) {
        addProgram(text, GL_VERTEX_SHADER);
    }

    @SuppressWarnings("UnusedDeclaration")
    public void addGeometryShader(String text) {
        addProgram(text, GL_GEOMETRY_SHADER);
    }

    @SuppressWarnings("WeakerAccess")
    public void addFragmentShader(String text) {
        addProgram(text, GL_FRAGMENT_SHADER);
    }

    public void setAttributeLocation(String attributeName, int location) {
        glBindAttribLocation(program, location, attributeName);
    }

    @SuppressWarnings("WeakerAccess")
    public void compileShader() {
        glLinkProgram(program);

        if (glGetProgrami(program, GL_LINK_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }

        glValidateProgram(program);

        if (glGetProgrami(program, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(program, 1024));
            System.exit(1);
        }
    }

    private void addProgram(String text, int type) {
        int shader = glCreateShader(type);

        if (shader == 0) {
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
            System.exit(1);
        }

        glShaderSource(shader, text);
        glCompileShader(shader);

        if (glGetShaderi(shader, GL_COMPILE_STATUS) == 0) {
            System.err.println(glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }

    public void setUniform(String uniformName, int value) {
        glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vector3f value) {
        glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
    }

    public void setUniform(String uniformName, Matrix4f value) {
        glUniformMatrix4(uniforms.get(uniformName), true, DataUtil.createFlippedBuffer(value));
    }

    public void setUniformBaseLight(String uniformName, BaseLight baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniform(uniformName + ".intensity", baseLight.getIntensity());
    }

    public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight) {
        setUniformBaseLight(uniformName + ".base", directionalLight);
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    public void setUniformPointLight(String uniformName, PointLight pointLight) {
        setUniformBaseLight(uniformName + ".base", pointLight);
        setUniform(uniformName + ".attenuation.constant", pointLight.getConstant());
        setUniform(uniformName + ".attenuation.linear", pointLight.getLinear());
        setUniform(uniformName + ".attenuation.exponent", pointLight.getExponent());
        setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPosition());
        setUniform(uniformName + ".range", pointLight.getRange());
    }

    public void setUniformSpotLight(String uniformName, SpotLight spotLight) {
        setUniformPointLight(uniformName + ".pointLight", spotLight);
        setUniform(uniformName + ".direction", spotLight.getDirection());
        setUniform(uniformName + ".cutoff", spotLight.getCutoff());
    }
}
