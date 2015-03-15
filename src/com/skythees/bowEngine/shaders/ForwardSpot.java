package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.core.components.BaseLight;
import com.skythees.bowEngine.core.components.DirectionalLight;
import com.skythees.bowEngine.core.components.PointLight;
import com.skythees.bowEngine.core.components.SpotLight;
import com.skythees.bowEngine.core.math.vector.Matrix4f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

/**
 * Created on 15.3.2015. at 19:42.
 */
public class ForwardSpot extends Shader {
    private static final ForwardSpot instance = new ForwardSpot();

    private ForwardSpot() {
        super();

        addVertexShader(loadInternalShader("forward-spot.vsh"));
        addFragmentShader(loadInternalShader("forward-spot.fsh"));

        setAttributeLocation("position", 0);
        setAttributeLocation("texturePos", 1);
        setAttributeLocation("normal", 2);

        compileShader();

        addUniform("model");
        addUniform("MVP");

        addUniform("specularIntensity");
        addUniform("specularExponent");
        addUniform("eyePos");

        addUniform("spotLight.pointLight.base.color");
        addUniform("spotLight.pointLight.base.intensity");
        addUniform("spotLight.pointLight.attenuation.constant");
        addUniform("spotLight.pointLight.attenuation.linear");
        addUniform("spotLight.pointLight.attenuation.exponent");
        addUniform("spotLight.pointLight.position");
        addUniform("spotLight.pointLight.range");
        addUniform("spotLight.direction");
        addUniform("spotLight.cutoff");
    }

    @SuppressWarnings("UnusedDeclaration")
    public static ForwardSpot getInstance() {
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
        setUniform("spotLight", getRenderingEngine().getActiveSpotLight());
    }

    public void setUniform(String uniformName, SpotLight spotLight) {
        setUniform(uniformName + ".pointLight", spotLight.getPointLight());
        setUniform(uniformName + ".direction", spotLight.getDirection());
        setUniform(uniformName + ".cutoff", spotLight.getCutoff());
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
