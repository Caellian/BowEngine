package com.skythees.bowEngine.core;

import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

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

    public void addChild(GameObject child) {
        children.add(child);
    }

    public void addComponent(GameComponent component) {
        components.add(component);
    }

    public void input() {
        for (GameComponent component : components) {
            component.input(transform);
        }
        children.forEach(GameObject::input);
    }

    public void update() {
        for (GameComponent component : components) {
            component.update(transform);
        }
        children.forEach(GameObject::update);
    }

    public void render(Shader shader) {
        for (GameComponent component : components) {
            component.render(transform, shader);
        }
        for (GameObject child : children) {
            child.render(shader);
        }
    }

    public Transform getTransform() {
        return transform;
    }
}
