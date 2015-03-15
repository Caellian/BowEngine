package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.core.components.BaseLight;
import com.skythees.bowEngine.core.components.DirectionalLight;
import com.skythees.bowEngine.core.components.PointLight;
import com.skythees.bowEngine.core.math.vector.Matrix4f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

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
    public void updateUniforms(Transform transform, Material material) {
        Matrix4f worldMatrix = transform.getTransformation();
        Matrix4f projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
        material.getTexture().bind();

        setUniform("model", worldMatrix);
        setUniform("MVP", projectedMatrix);

        setUniform("specularIntensity", material.getSpecularIntensity());
        setUniform("specularExponent", material.getSpecularExponent());

        setUniform("eyePos", getRenderingEngine().getMainCamera().getPos());
        setUniform("pointLight", getRenderingEngine().getActivePointLight());
    }

    public void setUniform(String uniformName, PointLight pointLight) {
        setUniform(uniformName + ".base", pointLight.getBaseLight());
        setUniform(uniformName + ".attenuation.constant", pointLight.getAttenuation().getConstant());
        setUniform(uniformName + ".attenuation.linear", pointLight.getAttenuation().getLinear());
        setUniform(uniformName + ".attenuation.exponent", pointLight.getAttenuation().getExponent());
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniform(uniformName + ".range", pointLight.getRange());
    }

    public void setUniform(String uniformName, DirectionalLight directionalLight) {
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    public void setUniform(String uniformName, BaseLight baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniform(uniformName + ".intensity", baseLight.getIntensity());
    }
}
