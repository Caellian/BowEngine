package com.skythees.bowEngine.core.components;

import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;

import java.util.ArrayList;

/**
 * Created on 15.3.2015. at 2:16.
 */
public class GameObject {
    private ArrayList<GameObject> children;
    private ArrayList<GameComponent> components;
    private Transform transform;

    public GameObject() {
        this.children = new ArrayList<>();
        this.components = new ArrayList<>();
        this.transform = new Transform();
    }

    public GameObject addChild(GameObject child) {
        children.add(child);
        child.getTransform().setParent(transform);
        return this;
    }

    public GameObject addComponent(GameComponent component) {
        components.add(component);
        component.setParent(this);
        return this;
    }

    public void input(float delta) {
        transform.update();

        for (GameComponent component : components) {
            component.input(delta);
        }
        for (GameObject child : children) {
            child.input(delta);
        }
    }

    public void update(float delta) {
        for (GameComponent component : components) {
            component.update(delta);
        }
        for (GameObject child : children) {
            child.update(delta);
        }
    }

    public void render(Shader shader, RenderingEngine renderingEngine) {
        for (GameComponent component : components) {
            component.render(shader, renderingEngine);
        }
        for (GameObject child : children) {
            child.render(shader, renderingEngine);
        }
    }

    public void addToRenderingEngine(RenderingEngine renderingEngine) {
        for (GameComponent component : components) {
            component.addToRenderingEngine(renderingEngine);
        }
        for (GameObject child : children) {
            child.addToRenderingEngine(renderingEngine);
        }
    }


    public Transform getTransform() {
        return transform;
    }
}
