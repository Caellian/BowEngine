package com.skythees.bowEngine.render.components;

import com.skythees.bowEngine.core.components.GameComponent;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.Mesh;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;
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
