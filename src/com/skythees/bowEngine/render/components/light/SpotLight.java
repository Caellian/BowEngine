/*
 * Software developed by Skythees
 * Copyright (C) 2015 Skythees
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.render.components.light;

import com.skythees.bowEngine.core.math.vector.Vector3f;
import com.skythees.bowEngine.shaders.ForwardSpot;

/**
 * Created on 03.03.15.
 */
public class SpotLight extends PointLight {
    private Vector3f direction;
    private float cutoff;

    public SpotLight(Vector3f color, float intensity, Vector3f position, float constant, float linear, float exponent, float range, Vector3f direction, float cutoff) {
        super(color, intensity, position, constant, linear, exponent, range);
        this.direction = direction;
        this.cutoff = cutoff;

        setShader(ForwardSpot.getInstance());
    }

    public Vector3f getDirection() {
        return direction;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setDirection(Vector3f direction) {
        this.direction = direction.normalized();
    }

    public float getCutoff() {
        return cutoff;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }
}
