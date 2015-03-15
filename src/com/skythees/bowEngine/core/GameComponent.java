package com.skythees.bowEngine.core;

import com.skythees.bowEngine.render.Shader;
import com.skythees.bowEngine.render.Transform;

/**
 * Created on 15.3.2015. at 2:20.
 */
public abstract class GameComponent {

    public void input(Transform transform) {
    }

    public void update(Transform transform) {
    }

    public void render(Transform transform, Shader shader) {
    }
}
