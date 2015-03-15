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
import com.skythees.bowEngine.core.math.vector.Vector3f;
import com.skythees.bowEngine.core.util.Time;
import com.skythees.bowEngine.core.util.input.InputHelper;
import org.lwjgl.input.Keyboard;

public class Camera {
    private static final Vector3f yAxis = new Vector3f(0, 1, 0);

    @SuppressWarnings("WeakerAccess")
    public Vector3f pos;
    private Vector3f forward;
    private Vector3f up;
    private Matrix4f projection;

    @SuppressWarnings("WeakerAccess")
    public Camera(float fov, float aspectRatio, float zNear, float zFar) {
        this.pos = new Vector3f(0, 0, 0);
        this.forward = new Vector3f(0, 0, 1).normalized();
        this.up = new Vector3f(0, 1, 0).normalized();
        this.projection = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
    }

    public Matrix4f getViewProjection() {
        Matrix4f cameraRotation = new Matrix4f().initRotation(forward, up);
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-pos.getX(), -pos.getY(), -pos.getZ());

        return projection.mul(cameraRotation.mul(cameraTranslation));
    }

    public void freeCamInput() {
        float moveAmount = (float) (10 * Time.getDelta());
        float rotationAmount = (float) (100 * Time.getDelta());

        if (InputHelper.getKey(Keyboard.KEY_W)) {
            move(getForward(), InputHelper.getKey(Keyboard.KEY_LCONTROL) ? moveAmount * 5 : moveAmount);
        }
        if (InputHelper.getKey(Keyboard.KEY_A)) {
            move(getLeft(), -(InputHelper.getKey(Keyboard.KEY_LCONTROL) ? moveAmount * 5 : moveAmount));
        }
        if (InputHelper.getKey(Keyboard.KEY_S)) {
            move(getForward(), -(InputHelper.getKey(Keyboard.KEY_LCONTROL) ? moveAmount * 5 : moveAmount));
        }
        if (InputHelper.getKey(Keyboard.KEY_D)) {
            move(getRight(), -(InputHelper.getKey(Keyboard.KEY_LCONTROL) ? moveAmount * 5 : moveAmount));
        }

        if (InputHelper.getKey(Keyboard.KEY_UP)) {
            rotateX(-rotationAmount);
        }
        if (InputHelper.getKey(Keyboard.KEY_LEFT)) {
            rotateY(-rotationAmount);
        }
        if (InputHelper.getKey(Keyboard.KEY_DOWN)) {
            rotateX(rotationAmount);
        }
        if (InputHelper.getKey(Keyboard.KEY_RIGHT)) {
            rotateY(rotationAmount);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void move(Vector3f dir, float amount) {
        pos = pos.add(dir.mul(amount));
    }

    @SuppressWarnings("WeakerAccess")
    public void rotateY(float angle) {
        Vector3f hAxis = yAxis.cross(forward).normalized();
        forward = forward.rotated(angle, yAxis).normalized();
        up = forward.cross(hAxis).normalized();
    }

    @SuppressWarnings("WeakerAccess")
    public void rotateX(float angle) {
        Vector3f hAxis = yAxis.cross(forward).normalized();
        forward = forward.rotated(angle, hAxis).normalized();
        up = forward.cross(hAxis).normalized();
    }

    @SuppressWarnings("WeakerAccess")
    public Vector3f getLeft() {
        Vector3f left = up.cross(forward);
        left.normalized();
        return left;
    }

    @SuppressWarnings("WeakerAccess")
    public Vector3f getRight() {
        Vector3f right = forward.cross(up);
        right.normalized();
        return right;
    }

    public Vector3f getPos() {
        return this.pos;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setPos(Vector3f pos) {
        this.pos = pos;
    }

    public Vector3f getUp() {
        return up;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUp(Vector3f up) {
        this.up = up;
    }

    public Vector3f getForward() {
        return forward;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setForward(Vector3f forward) {
        this.forward = forward;
    }
}
