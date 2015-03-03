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

import com.skythees.bowEngine.math.vector.Matrix4f;
import com.skythees.bowEngine.math.vector.Vector3f;

public class Transform {
    private static Camera camera;

    private static float clipNear;
    private static float clipFar;
    private static float width;
    private static float height;
    private static float fow;

    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;

    @SuppressWarnings("UnusedDeclaration")
    public Transform() {
        translation = new Vector3f(0, 0, 0);
        rotation = new Vector3f(0, 0, 0);
        scale = new Vector3f(1, 1, 1);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setProjection(float fow, float width, float height, float clipNear, float clipFar) {
        Transform.fow = fow;
        Transform.width = width;
        Transform.height = height;
        Transform.clipNear = clipNear;
        Transform.clipFar = clipFar;
    }

    public static Camera getCamera() {
        return camera;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void setCamera(Camera camera) {
        Transform.camera = camera;
    }

    public Matrix4f getTransformation() {
        Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.getX(), translation.getY(), translation.getZ());
        Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.getX(), rotation.getY(), rotation.getZ());
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

        return translationMatrix.mul(rotationMatrix.mul(scaleMatrix));
    }

    @SuppressWarnings("UnusedDeclaration")
    public Matrix4f getProjectedTransformation() {
        Matrix4f transformationMatrix = getTransformation();
        Matrix4f projectionMatrix = new Matrix4f().initProjection(fow, width, height, clipNear, clipFar);
        Matrix4f cameraRotation = new Matrix4f().initCamera(camera.getForward(), camera.getUp());
        Matrix4f cameraTranslation = new Matrix4f().initTranslation(-camera.getPos().getX(), -camera.getPos().getY(), -camera.getPos().getZ());

        return projectionMatrix.mul(cameraRotation.mul(cameraTranslation.mul(transformationMatrix)));
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f getTranslation() {
        return translation;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f getRotation() {
        return rotation;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f getScale() {
        return scale;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }
}
