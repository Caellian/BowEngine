/*
 Bow Engine, modular game engine
 Copyright (C) 2015 Skythees

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.core.math.Matrix4f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.components.light.BaseLight;
import com.skythees.bowEngine.render.components.light.DirectionalLight;
import com.sun.istack.internal.NotNull;

/**
 * Created on 15.3.2015. at 19:42.
 */
public class ForwardDirectional extends Shader
{
	private static final ForwardDirectional instance = new ForwardDirectional();

	private ForwardDirectional()
	{
		super("./resources/shaders/forward-directional");
	}

	@NotNull
	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public static ForwardDirectional getInstance()
	{
		return instance;
	}

	@Override
	public void updateUniforms(@NotNull Transform transform, @NotNull Material material, @NotNull RenderingEngine renderingEngine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniform("specularIntensity", material.getFloat("specularIntensity"));
		setUniform("specularPower", material.getFloat("specularPower"));

		setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTransformedPosition());
		setUniformDirectionalLight("directionalLight", (DirectionalLight) renderingEngine.getActiveLight());
	}

	@SuppressWarnings("WeakerAccess")
	public void setUniformDirectionalLight(@SuppressWarnings("SameParameterValue") String uniformName, @NotNull DirectionalLight directionalLight)
	{
		setUniformBaseLight(uniformName + ".base", directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}

	private void setUniformBaseLight(String uniformName, @NotNull BaseLight baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}
}
