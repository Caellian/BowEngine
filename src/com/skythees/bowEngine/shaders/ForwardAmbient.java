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

/**
 * Created on 15.3.2015. at 19:16.
 */
public class ForwardAmbient extends Shader {
    private static final ForwardAmbient instance = new ForwardAmbient();

    private ForwardAmbient() {
        super();

        addVertexShader(loadInternalShader("forward-ambient.vsh"));
        addFragmentShader(loadInternalShader("forward-ambient.fsh"));

	    setAttributeLocation("positions", 0);
	    setAttributeLocation("texturePos", 1);

        compileShader();

        addUniform("MVP");
        addUniform("ambientIntensity");
    }

    @SuppressWarnings("UnusedDeclaration")
    public static ForwardAmbient getInstance() {
        return instance;
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture("diffuse").bind();

        setUniform("MVP", projectedMatrix);
        setUniform("ambientIntensity", renderingEngine.getAmbientLight());
    }
}
