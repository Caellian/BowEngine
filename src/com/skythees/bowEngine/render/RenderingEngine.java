package com.skythees.bowEngine.render;

import com.skythees.bowEngine.core.components.DirectionalLight;
import com.skythees.bowEngine.core.components.GameObject;
import com.skythees.bowEngine.core.components.PointLight;
import com.skythees.bowEngine.core.components.SpotLight;
import com.skythees.bowEngine.core.math.vector.Vector3f;
import com.skythees.bowEngine.render.display.Window;
import com.skythees.bowEngine.shaders.ForwardAmbient;
import com.skythees.bowEngine.shaders.ForwardDirectional;
import com.skythees.bowEngine.shaders.ForwardPoint;
import com.skythees.bowEngine.shaders.ForwardSpot;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created on 15.3.2015. at 2:47.
 */
public class RenderingEngine {

    private Camera mainCamera;
    private Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
    private DirectionalLight activeDirectionalLight;
    private PointLight activePointLight;
    private SpotLight activeSpotLight;

    private ArrayList<DirectionalLight> directionalLights = new ArrayList<>();
    private ArrayList<PointLight> pointLights = new ArrayList<>();
    private ArrayList<SpotLight> spotLights = new ArrayList<>();

    public RenderingEngine() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        mainCamera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.001f, 1000.0f);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void clearScreen() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setTextures(boolean enabled) {
        if (enabled)
            glEnable(GL_TEXTURE_2D);
        else
            glDisable(GL_TEXTURE_2D);
    }

    private static void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    private static void setClearColor(Vector3f color) {
        glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
    }

    public static String openGLVer() {
        return glGetString(GL_VERSION);
    }

    private void clearLightList() {
        directionalLights.clear();
        pointLights.clear();
        spotLights.clear();
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }

    public DirectionalLight getActiveDirectionalLight() {
        return activeDirectionalLight;
    }

    public PointLight getActivePointLight() {
        return activePointLight;
    }

    public SpotLight getActiveSpotLight() {
        return activeSpotLight;
    }

    public void tmpInput(float delta) {
        getMainCamera().freeCamInput(delta);
    }

    public void addDirectionalLight(DirectionalLight directionalLight) {
        directionalLights.add(directionalLight);
    }

    public void addPointLight(PointLight pointLight) {
        pointLights.add(pointLight);
    }

    public void addSpotLight(SpotLight spotLight) {
        spotLights.add(spotLight);
    }

    public void render(GameObject object) {
        clearScreen();
        clearLightList();
        object.addToRenderingEngine(this);

        Shader forwardAmbient = ForwardAmbient.getInstance();
        Shader forwardDirectional = ForwardDirectional.getInstance();
        Shader forwardPoint = ForwardPoint.getInstance();
        Shader forwardSpot = ForwardSpot.getInstance();

        forwardAmbient.setRenderingEngine(this);
        forwardDirectional.setRenderingEngine(this);
        forwardPoint.setRenderingEngine(this);
        forwardSpot.setRenderingEngine(this);

        object.render(forwardAmbient);
        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);
        glDepthMask(false);
        glDepthFunc(GL_EQUAL);

        for (DirectionalLight light : directionalLights) {
            activeDirectionalLight = light;
            object.render(forwardDirectional);
        }
        for (PointLight light : pointLights) {
            activePointLight = light;
            object.render(forwardPoint);
        }
        for (SpotLight light : spotLights) {
            activeSpotLight = light;
            object.render(forwardSpot);
        }

        glDepthFunc(GL_LESS);
        glDepthMask(true);
        glDisable(GL_BLEND);
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
