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

package com.skythees.bowEngine.light;

import com.skythees.bowEngine.math.vector.Vector3f;

/**
 * Created on 02.03.15
 */
public class BaseLight {
    @SuppressWarnings("WeakerAccess")
    float intensity;
    private Vector3f color;

    public BaseLight(Vector3f color, float intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public Vector3f getColor() {
        return color;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
