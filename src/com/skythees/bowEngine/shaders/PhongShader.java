/*
 * Software developed by Skythees
 * Copyright (C) 2015 Skythees
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.shaders;

import com.skythees.bowEngine.light.BaseLight;
import com.skythees.bowEngine.light.DirectionalLight;
import com.skythees.bowEngine.light.PointLight;
import com.skythees.bowEngine.light.SpotLight;
import com.skythees.bowEngine.managers.DataUtil;
import com.skythees.bowEngine.math.vector.Matrix4f;
import com.skythees.bowEngine.math.vector.Vector3f;
import com.skythees.bowEngine.render.Material;
import com.skythees.bowEngine.render.RenderUtil;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

import java.util.Objects;

public class PhongShader extends Shader {

    private static final PhongShader instance = new PhongShader();
    private static int MAX_LIGHTS;
    private static Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.5f);
    private static DirectionalLight directionalLight = new DirectionalLight(new BaseLight(new Vector3f(0, 0, 0), 0), new Vector3f(0, 0, 0));
    private static PointLight[] pointLights = new PointLight[]{};
    private static SpotLight[] spotLights = new SpotLight[]{};

    private PhongShader() {
        super();

        parseShaderConstants(loadInternalShader("phong.fsh"));
        addVertexShader(loadInternalShader("phong.vsh"));
        addFragmentShader(loadInternalShader("phong.fsh"));
        compileShader();

        addUniform("transform");
        addUniform("transformProjected");
        addUniform("baseColor");
        addUniform("ambientLight");

        addUniform("specularIntensity");
        addUniform("specularExponent");
        addUniform("eyePos");

        addUniform("directionalLight.base.color");
        addUniform("directionalLight.base.intensity");
        addUniform("directionalLight.direction");

        for (int count = 0; count < MAX_LIGHTS; count++) {
            addUniform("pointLights[" + count + "].base.color");
            addUniform("pointLights[" + count + "].base.intensity");
            addUniform("pointLights[" + count + "].attenuation.constant");
            addUniform("pointLights[" + count + "].attenuation.linear");
            addUniform("pointLights[" + count + "].attenuation.exponent");
            addUniform("pointLights[" + count + "].position");
            addUniform("pointLights[" + count + "].range");
        }

        for (int count = 0; count < MAX_LIGHTS; count++) {
            addUniform("spotLights[" + count + "].pointLight.base.color");
            addUniform("spotLights[" + count + "].pointLight.base.intensity");
            addUniform("spotLights[" + count + "].pointLight.attenuation.constant");
            addUniform("spotLights[" + count + "].pointLight.attenuation.linear");
            addUniform("spotLights[" + count + "].pointLight.attenuation.exponent");
            addUniform("spotLights[" + count + "].pointLight.position");
            addUniform("spotLights[" + count + "].pointLight.range");

            addUniform("spotLights[" + count + "].direction");
            addUniform("spotLights[" + count + "].cutoff");
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static PhongShader getInstance() {
        return instance;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static Vector3f getAmbientLight() {
        return ambientLight;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setAmbientLight(Vector3f ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setDirectionalLight(DirectionalLight directionalLight) {
        PhongShader.directionalLight = directionalLight;
    }

    @SuppressWarnings({"UnusedDeclaration", "WeakerAccess"})
    public static void setPointLights(PointLight[] pointLights) {
        if (pointLights.length > MAX_LIGHTS) {
            System.err.println("Error: Received too many point lights.\n" +
                    "Max allowed is " + MAX_LIGHTS + ", you passed in " + pointLights.length + ".\n" +
                    "Cropping down the passed array.");
            new Exception().printStackTrace();
            PointLight[] fixedLights = new PointLight[MAX_LIGHTS];
            System.arraycopy(pointLights, 0, fixedLights, 0, MAX_LIGHTS);
            setPointLights(fixedLights);
        } else {
            PhongShader.pointLights = pointLights;
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setSpotLights(SpotLight[] spotLights) {
        if (spotLights.length > MAX_LIGHTS) {
            System.err.println("Error: Received too many spot lights.\n" +
                    "Max allowed is " + MAX_LIGHTS + ", you passed in " + spotLights.length + ".\n" +
                    "Cropping down the passed array.");
            new Exception().printStackTrace();
            SpotLight[] fixedLights = new SpotLight[MAX_LIGHTS];
            System.arraycopy(spotLights, 0, fixedLights, 0, MAX_LIGHTS);
            setSpotLights(fixedLights);
        } else {
            PhongShader.spotLights = spotLights;
        }
    }

    private void parseShaderConstants(String input) {
        if (input.split("\n").length == 0) {
            return;
        }
        for (final String line : input.split("\n")) {
            String[] tokens = line.split(" ");
            tokens = DataUtil.removeEmptyStrings(tokens);
            if (tokens.length >= 4 && Objects.equals(tokens[2], "MAX_LIGHTS")) {
                MAX_LIGHTS = Integer.parseInt(tokens[4].replace(";", ""));
                return;
            }
        }
        MAX_LIGHTS = 10;
    }

    @Override
    public void updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        if (material.getTexture() != null) {
            material.getTexture().bind();
        } else {
            RenderUtil.unbindTextures();
        }

        setUniform("transformProjected", projectedMatrix);
        setUniform("transform", worldMatrix);
        setUniform("baseColor", material.getColor());

        setUniform("ambientLight", ambientLight);
        setUniform("directionalLight", directionalLight);

        int counter = -1;
        for (final PointLight ignored : pointLights) {
            setUniform("pointLights[" + ++counter + "]", pointLights[counter]);
        }

        counter = -1;
        for (final SpotLight ignored : spotLights) {
            setUniform("spotLights[" + ++counter + "]", spotLights[counter]);
        }

        setUniform("specularIntensity", material.getSpecularIntensity());
        setUniform("specularExponent", material.getSpecularExponent());

        setUniform("eyePos", Transform.getCamera().getPos());
    }

    @SuppressWarnings("WeakerAccess")
    public void setUniform(String uniformName, SpotLight spotLight) {
        setUniform(uniformName + ".pointLight", new PointLight(new BaseLight(spotLight.getColor(), spotLight.getIntensity()), spotLight.getAttenuation(), spotLight.getPosition(), spotLight.getRange()));
        setUniform(uniformName + ".direction", spotLight.getDirection());
        setUniform(uniformName + ".cutoff", spotLight.getCutoff());
    }

    @SuppressWarnings("WeakerAccess")
    public void setUniform(String uniformName, PointLight pointLight) {
        setUniform(uniformName + ".base", new BaseLight(pointLight.getColor(), pointLight.getIntensity()));
        setUniform(uniformName + ".attenuation.constant", pointLight.getAttenuation().getConstant());
        setUniform(uniformName + ".attenuation.linear", pointLight.getAttenuation().getLinear());
        setUniform(uniformName + ".attenuation.exponent", pointLight.getAttenuation().getExponent());
        setUniform(uniformName + ".position", pointLight.getPosition());
        setUniform(uniformName + ".range", pointLight.getRange());

    }

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public void setUniform(String uniformName, DirectionalLight directionalLight) {
        setUniform(uniformName + ".base", directionalLight.getBase());
        setUniform(uniformName + ".direction", directionalLight.getDirection());
    }

    @SuppressWarnings("WeakerAccess")
    public void setUniform(String uniformName, BaseLight baseLight) {
        setUniform(uniformName + ".color", baseLight.getColor());
        setUniform(uniformName + ".intensity", baseLight.getIntensity());
    }
}
