package com.skythees.bowEngine.core;

import com.skythees.bowEngine.core.components.GameObject;

/**
 * Created on 15.3.2015. at 0:53.
 */
public abstract class Game {

    private GameObject root = new GameObject();

    public void init() {
    }

    public void input(float delta) {
        getRootObject().input(delta);
    }

    public void update(float delta) {
        getRootObject().update(delta);
    }

    public GameObject getRootObject() {
        return root;
    }
}
