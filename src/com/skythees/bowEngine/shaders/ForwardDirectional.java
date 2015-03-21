package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.core.math.Matrix4f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.components.light.DirectionalLight;

/**
 * Created on 15.3.2015. at 19:42.
 */
public class ForwardDirectional extends Shader {
    private static final ForwardDirectional instance = new ForwardDirectional();

    private ForwardDirectional() {
        super();

        addVertexShader(loadInternalShader("forward-directional.vsh"));
        addFragmentShader(loadInternalShader("forward-directional.fsh"));

        setAttributeLocation("position", 0);
        setAttributeLocation("texturePos", 1);
        setAttributeLocation("normal", 2);

        compileShader();

        addUniform("model");
        addUniform("MVP");

        addUniform("specularIntensity");
        addUniform("specularExponent");
        addUniform("eyePos");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");
    }

    @SuppressWarnings("UnusedDeclaration")
    public static ForwardDirectional getInstance() {
        return instance;
    }

    @Override
    public void updateUniforms(Transform transform, Material material, RenderingEngine renderingEngine) {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture("diffuse").bind();

        setUniform("model", worldMatrix);
        setUniform("MVP", projectedMatrix);

        setUniform("specularIntensity", material.getFloat("specularIntensity"));
        setUniform("specularExponent", material.getFloat("specularExponent"));

        setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTransformedPosition());
        setUniformDirectionalLight("directionalLight", (DirectionalLight) renderingEngine.getActiveLight());
    }
}
