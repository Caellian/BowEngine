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

        setAttributeLocation("position", 0);
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
