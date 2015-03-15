package com.skythees.bowEngine.core;

/**
 * Created on 15.3.2015. at 0:53.
 */
public abstract class Game {

    private GameObject root = new GameObject();

    public void init() {
    }

    public void input() {
        getRootObject().input();
    }

    public void update() {
        getRootObject().update();
    }

    public GameObject getRootObject() {
        return root;
    }
}
