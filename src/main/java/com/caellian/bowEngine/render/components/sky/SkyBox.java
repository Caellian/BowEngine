package com.caellian.bowEngine.render.components.sky;

import com.caellian.bowEngine.core.components.GameComponent;
import com.caellian.bowEngine.render.RenderingEngine;
import com.caellian.bowEngine.render.Shader;
import com.caellian.bowEngine.render.Texture;

import static org.lwjgl.opengl.GL11.*;

/**
 * @author Caellian
 */
public class SkyBox extends GameComponent {
    public SkyBox(Texture skyTexture) {

    }

    @Override
    public void render(Shader shader, RenderingEngine renderingEngine) {
        glCullFace(GL_FRONT);
        super.render(shader, renderingEngine);
        glCullFace(GL_BACK);
    }
}