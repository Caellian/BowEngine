package com.skythees.bowEngine.render.shaders;

import com.skythees.bowEngine.math.vector.Vector3f;
import com.skythees.bowEngine.render.Texture;

public class Material {
    private Texture texture;
    private Vector3f color;

    public Material(Texture texture, Vector3f color) {
        this.texture = texture;
        this.color = color;
    }

    public Material(Texture texture) {
        this(texture, new Vector3f(0.5f, 0.5f, 0.5f));
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }
}
