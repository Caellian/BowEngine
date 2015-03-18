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

public class Vector3f {
    private float x;
    private float y;
    private float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @SuppressWarnings("WeakerAccess")
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    public float max() {
        return Math.max(x, Math.max(y, z));
    }

    @SuppressWarnings("UnusedDeclaration")
    public float dot(Vector3f r) {
        return x * r.getX() + y * r.getY() + z * r.getZ();
    }

    public Vector3f cross(Vector3f r) {
        float x_ = y * r.getZ() - z * r.getY();
        float y_ = z * r.getX() - x * r.getZ();
        float z_ = x * r.getY() - y * r.getX();

        return new Vector3f(x_, y_, z_);
    }

    public Vector3f normalized() {
        float length = length();

        return new Vector3f(x / length, y / length, z / length);
    }

    public Vector3f rotate(Vector3f axis, float angle) {
        float sinAngle = (float) Math.sin(-angle);
        float cosAngle = (float) Math.cos(-angle);
        return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
    }

    public Vector3f rotate(Quaternion rotation) {
        Quaternion conjugate = rotation.conjugated();
        Quaternion w = rotation.mul(this).mul(conjugate);
        return new Vector3f(w.getX(), w.getY(), w.getZ());
    }

    public Vector3f lerp(Vector3f dest, Vector3f lerpFactor) {
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getXY() {
        return new Vector2f(x, y);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getYZ() {
        return new Vector2f(y, z);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getZX() {
        return new Vector2f(z, x);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getYX() {
        return new Vector2f(y, x);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getZY() {
        return new Vector2f(z, y);
    }

    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getXZ() {
        return new Vector2f(x, z);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f add(Vector3f r) {
        return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f add(float r) {
        return new Vector3f(x + r, y + r, z + r);
    }

    public Vector3f sub(Vector3f r) {
        return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f sub(float r) {
        return new Vector3f(x - r, y - r, z - r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f mul(Vector3f r) {
        return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
    }

    public Vector3f mul(float r) {
        return new Vector3f(x * r, y * r, z * r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f div(Vector3f r) {
        return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f div(float r) {
        return new Vector3f(x / r, y / r, z / r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
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

    public boolean equals(Vector3f compared) {
        return this == compared || compared != null && Float.compare(compared.x, x) == 0 && Float.compare(compared.y, y) == 0 && Float.compare(compared.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }
}
