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

package com.skythees.bowEngine.core.math.vector;

public class Quaternion {
    private float x;
    private float y;
    private float z;
    private float w;

    public Quaternion() {
        this(0, 0, 0, 1);
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Vector3f axis, float angle) {
        float sinHalfAngle = (float) Math.sin(angle / 2);
        float cosHalfAngle = (float) Math.cos(angle / 2);
        this.x = axis.getX() * sinHalfAngle;
        this.y = axis.getY() * sinHalfAngle;
        this.z = axis.getZ() * sinHalfAngle;
        this.w = cosHalfAngle;
    }

    public Quaternion initRotation(Vector3f axis, float angle) {
        float sinHalfAngle = (float) Math.sin(angle / 2);
        float cosHalfAngle = (float) Math.cos(angle / 2);

        this.x = axis.getX() * sinHalfAngle;
        this.y = axis.getY() * sinHalfAngle;
        this.z = axis.getZ() * sinHalfAngle;
        this.w = cosHalfAngle;

        return this;
    }

    @SuppressWarnings("WeakerAccess")
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Quaternion normalized() {
        float length = length();

        x /= length;
        y /= length;
        z /= length;
        w /= length;

        return new Quaternion(x / length, y / length, z / length, w / length);
    }

    public Quaternion conjugated() {
        return new Quaternion(-x, -y, -z, w);
    }

    public Quaternion mul(Quaternion r) {
        float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Quaternion mul(Vector3f r) {
        float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternion(x_, y_, z_, w_);
    }

    public Matrix4f toRotationMatrix() {
        return new Matrix4f().initRotation(getForward(), getUp(), getRight());
    }

    public Vector3f getForward() {
        return new Vector3f(0, 0, 1).rotate(this);
    }

    public Vector3f getBack() {
        return new Vector3f(0, 0, -1).rotate(this);
    }

    public Vector3f getUp() {
        return new Vector3f(0, 1, 0).rotate(this);
    }

    public Vector3f getDown() {
        return new Vector3f(0, -1, 0).rotate(this);
    }

    public Vector3f getRight() {
        return new Vector3f(1, 0, 0).rotate(this);
    }

    public Vector3f getLeft() {
        return new Vector3f(-1, 0, 0).rotate(this);
    }

    public float getX() {
        return x;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setZ(float z) {
        this.z = z;
    }

    @SuppressWarnings("WeakerAccess")
    public float getW() {
        return w;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setW(float w) {
        this.w = w;
    }
}
