package com.skythees.bowEngine.render.shaders;

import com.skythees.bowEngine.managers.ResourceLoader;
import com.skythees.bowEngine.math.vector.Matrix4f;
import com.skythees.bowEngine.render.RenderUtil;
import com.skythees.bowEngine.render.Shader;

public class ShaderBase extends Shader {
    public ShaderBase(String shaderName) {
        super();

        addVertexShader(ResourceLoader.loadShader("vs_" + shaderName));
        addFragmentShader(ResourceLoader.loadShader("fs_" + shaderName));
        compileShader();

        addUniform("transform");
        addUniform("color");
    }

    @Override
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        if (material.getTexture() != null)
            material.getTexture().bind();
        else
            RenderUtil.unbindTextures();

        setUniform("transform", projectedMatrix);
        setUniform("color", material.getColor());
    }
}
