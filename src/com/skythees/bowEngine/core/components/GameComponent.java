package com.skythees.bowEngine.core.components;

import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

/**
 * Created on 15.3.2015. at 2:20.
 */
public abstract class GameComponent {

    public void input(Transform transform, float delta) {
    }

    public void update(Transform transform, float delta) {
    }

    public void render(Transform transform, Shader shader) {
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {

    }
}
