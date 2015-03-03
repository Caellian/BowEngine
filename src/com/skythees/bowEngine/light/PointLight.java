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
 * Created on 02.03.15.
 */
public class PointLight extends BaseLight {

    private Attenuation attenuation;
    private Vector3f position;

    private float range;

    public PointLight(BaseLight baseLight, Attenuation attenuation, Vector3f position, float range) {
        super(baseLight.getColor(), baseLight.getIntensity());
        this.attenuation = attenuation;
        this.position = position;
        this.range = range;
    }

    public Attenuation getAttenuation() {
        return attenuation;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setAttenuation(Attenuation attenuation) {
        this.attenuation = attenuation;
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
}
