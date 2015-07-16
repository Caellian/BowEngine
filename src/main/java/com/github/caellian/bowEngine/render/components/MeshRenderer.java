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

package com.github.caellian.bowEngine.render.components;

import com.github.caellian.bowEngine.core.components.GameComponent;
import com.github.caellian.bowEngine.render.Material;
import com.github.caellian.bowEngine.render.Mesh;
import com.github.caellian.bowEngine.render.RenderingEngine;
import com.github.caellian.bowEngine.render.Shader;
import com.sun.istack.internal.NotNull;

/**
 * Created on 15.3.2015. at 2:33.
 */
public class MeshRenderer extends GameComponent
{
	@SuppressWarnings("CanBeFinal")
	private Mesh     mesh;
	@SuppressWarnings("CanBeFinal")
	private Material material;

	@SuppressWarnings("unused")
	public MeshRenderer(Mesh mesh, Material material)
	{
		this.mesh = mesh;
		this.material = material;
	}

	@Override
	public void render(@NotNull Shader shader, RenderingEngine renderingEngine)
	{
		shader.bind();
		shader.updateUniforms(getTransform(), material, renderingEngine);
		mesh.draw();
	}
}
