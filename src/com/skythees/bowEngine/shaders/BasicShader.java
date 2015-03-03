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

package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.math.vector.Matrix4f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.RenderUtil;
import com.skythees.bowEngine.render.Shader;

@SuppressWarnings("UnusedDeclaration")
public class BasicShader extends Shader {
    private static final BasicShader instance = new BasicShader();

    private BasicShader() {
        super();

        addVertexShader(loadInternalShader("basic.vsh"));
        addFragmentShader(loadInternalShader("basic.fsh"));
        compileShader();

        addUniform("transform");
        addUniform("color");
    }

    @SuppressWarnings("UnusedDeclaration")
    public static BasicShader getInstance() {
        return instance;
    }

    @Override
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        if (material.getTexture() != null) {
            material.getTexture().bind();
        } else {
            RenderUtil.unbindTextures();
        }

        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }
}
