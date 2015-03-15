package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.core.math.vector.Matrix4f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

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
    public void updateUniforms(Transform transform, Material material) {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture().bind();

        setUniform("MVP", projectedMatrix);
        setUniform("ambientIntensity", getRenderingEngine().getAmbientLight());
    }
}
