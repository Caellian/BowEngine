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
import com.skythees.bowEngine.shaders.ForwardPoint;

/**
 * Created on 02.03.15.
 */
public class PointLight extends BaseLight {

    private Vector3f position;
    private float constant;
    private float linear;
    private float exponent;
    private float range;

    public PointLight(Vector3f color, float intensity, Vector3f position, float constant, float linear, float exponent, float range) {
        super(color, intensity);
        this.position = position;
        this.constant = constant;
        this.linear = linear;
        this.exponent = exponent;
        this.range = range;

        setShader(ForwardPoint.getInstance());
    }

    public Vector3f getPosition() {
        return position;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getRange() {
        return range;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRange(float range) {
        this.range = range;
    }

    public float getConstant() {
        return constant;
    }

    public void setConstant(float constant) {
        this.constant = constant;
    }

    public float getLinear() {
        return linear;
    }

    public void setLinear(float linear) {
        this.linear = linear;
    }

    public float getExponent() {
        return exponent;
    }

    public void setExponent(float exponent) {
        this.exponent = exponent;
    }
}
