package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.core.math.Matrix4f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.components.light.PointLight;

/**
 * Created on 15.3.2015. at 19:42.
 */
public class ForwardPoint extends Shader {
    private static final ForwardPoint instance = new ForwardPoint();

    private ForwardPoint() {
        super();
        addVertexShader(loadInternalShader("forward-point.vsh"));
        addFragmentShader(loadInternalShader("forward-point.fsh"));

        setAttributeLocation("position", 0);
        setAttributeLocation("texturePos", 1);
        setAttributeLocation("normal", 2);

        compileShader();

        addUniform("model");
        addUniform("MVP");

        addUniform("specularIntensity");
        addUniform("specularExponent");
        addUniform("eyePos");

        addUniform("pointLight.base.color");
        addUniform("pointLight.base.intensity");
        addUniform("pointLight.attenuation.constant");
        addUniform("pointLight.attenuation.linear");
        addUniform("pointLight.attenuation.exponent");
        addUniform("pointLight.position");
        addUniform("pointLight.range");
    }

    @SuppressWarnings("UnusedDeclaration")
    public static ForwardPoint getInstance() {
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
        setUniformPointLight("pointLight", (PointLight) renderingEngine.getActiveLight());
    }
}
