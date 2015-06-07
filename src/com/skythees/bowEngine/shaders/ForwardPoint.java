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
import com.skythees.bowEngine.render.components.light.PointLight;
import com.sun.istack.internal.NotNull;

/**
 * Created on 15.3.2015. at 19:42.
 */
public class ForwardPoint extends Shader {
    private static final ForwardPoint instance = new ForwardPoint();

    private ForwardPoint() {
       super("./resources/shaders/forward-point");
    }

   @NotNull
    @SuppressWarnings("UnusedDeclaration")
    public static ForwardPoint getInstance() {
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
        setUniformPointLight("pointLight", (PointLight) renderingEngine.getActiveLight());
    }

   public void setUniformPointLight(String uniformName, @NotNull PointLight pointLight)
   {
      setUniformBaseLight(uniformName + ".base", pointLight);
      setUniform(uniformName + ".attenuation.constant", pointLight.getConstant());
      setUniform(uniformName + ".attenuation.linear", pointLight.getLinear());
      setUniform(uniformName + ".attenuation.exponent", pointLight.getExponent());
      setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPosition());
      setUniform(uniformName + ".range", pointLight.getRange());
   }

   public void setUniformBaseLight(String uniformName, @NotNull BaseLight baseLight)
   {
      setUniform(uniformName + ".color", baseLight.getColor());
      setUniform(uniformName + ".intensity", baseLight.getIntensity());
    }
}
