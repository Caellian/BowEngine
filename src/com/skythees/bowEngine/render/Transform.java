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

package com.skythees.bowEngine.render;

import com.skythees.bowEngine.core.math.vector.Matrix4f;
import com.skythees.bowEngine.core.math.vector.Quaternion;
import com.skythees.bowEngine.core.math.vector.Vector3f;

public class Transform {
    private Vector3f pos;
    private Quaternion rot;
    private Vector3f scale;

    @SuppressWarnings("UnusedDeclaration")
    public Transform() {
        pos = new Vector3f(0, 0, 0);
        rot = new Quaternion(0, 0, 0, 1);
        scale = new Vector3f(1, 1, 1);
    }

    public Matrix4f getTransformation() {
        Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
        Matrix4f rotationMatrix = rot.toRotationMatrix();
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }

    public void rotate(Vector3f axis, float angle) {
        rot = new Quaternion(axis, angle).mul(rot).normalized();
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f getPos() {
        return pos;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Quaternion getRot() {
        return rot;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRot(Quaternion rot) {
        this.rot = rot;
    }


    @SuppressWarnings("UnusedDeclaration")
    public Vector3f getScale() {
        return scale;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }
}
