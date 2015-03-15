package com.skythees.bowEngine.core.components;

import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.Mesh;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

/**
 * Created on 15.3.2015. at 2:33.
 */
public class MeshRenderer extends GameComponent {

    private Mesh mesh;
    private Material material;

    public MeshRenderer(Mesh mesh, Material material) {
        this.mesh = mesh;
        this.material = material;
    }

    @Override
    public void render(Transform transform, Shader shader) {
        shader.bind();
        shader.updateUniforms(transform, material);
        mesh.draw();
    }
}
