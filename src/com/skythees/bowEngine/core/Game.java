package com.skythees.bowEngine.core;

import com.skythees.bowEngine.core.components.GameObject;
import com.skythees.bowEngine.render.RenderingEngine;

/**
 * Created on 15.3.2015. at 0:53.
 */
public abstract class Game {

    private GameObject root = new GameObject();

    public void init() {
    }

    public void input(float delta) {
        root.input(delta);
    }

    public void update(float delta) {
        root.update(delta);
    }

    public Game addObject(GameObject gameObject) {
        root.addChild(gameObject);
        return this;
    }

    public void render(RenderingEngine renderingEngine) {
        renderingEngine.render(root);
    }
}
