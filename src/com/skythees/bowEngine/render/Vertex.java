package com.skythees.bowEngine.render;

import com.skythees.bowEngine.math.vector.Vector2f;
import com.skythees.bowEngine.math.vector.Vector3f;

public class Vertex {
    public static final int SIZE = 5;

    private Vector3f pos;
    private Vector2f texturePos;

    public Vertex(Vector3f pos) {
        this(pos, new Vector2f(0, 0));
    }

    public Vertex(Vector3f pos, Vector2f texturePos) {
        this.pos = pos;
        this.texturePos = texturePos;
    }

    public Vector3f getPos() {
        return pos;
    }

    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector2f getTexturePos() {
        return texturePos;
    }

    public void setTexturePos(Vector2f texturePos) {
        this.texturePos = texturePos;
    }
}
