package com.skythees.bowEngine.core.components;

import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;

/**
 * Created on 15.3.2015. at 2:20.
 */
public abstract class GameComponent {

    private GameObject parent;

    public void input(float delta) {
    }

    public void update(float delta) {
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
    }

    public GameObject getParent() {
        return parent;
    }

    public void setParent(GameObject parent) {
        this.parent = parent;
    }

    public Transform getTransform() {
        return parent.getTransform();
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {

    }
}
