package com.skythees.bowEngine.render;

import com.skythees.bowEngine.core.GameObject;
import com.skythees.bowEngine.core.math.vector.Vector3f;
import com.skythees.bowEngine.render.display.Window;
import com.skythees.bowEngine.shaders.BasicShader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created on 15.3.2015. at 2:47.
 */
public class RenderingEngine {

    private Camera mainCamera;

    public RenderingEngine() {
        System.out.println("Running OpenGL version:" + openGLVer());

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        glFrontFace(GL_CW);
        glCullFace(GL_BACK);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_DEPTH_CLAMP);

        glEnable(GL_TEXTURE_2D);

        mainCamera = new Camera((float) Math.toRadians(70.0f), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f);
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

    public void tmpInput() {
        getMainCamera().freeCamInput();
    }

    public void render(GameObject object) {
        clearScreen();

        Shader shader = BasicShader.getInstance();
        shader.setRenderingEngine(this);

        object.render(BasicShader.getInstance());
    }

    public Camera getMainCamera() {
        return mainCamera;
    }

    public void setMainCamera(Camera mainCamera) {
        this.mainCamera = mainCamera;
    }
}
