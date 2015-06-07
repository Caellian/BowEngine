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

package com.skythees.bowEngine.core.math;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class Vector3f {
    private float x;
    private float y;
    private float z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float max()
    {
        return Math.max(x, Math.max(y, z));
    }

    @NotNull
    public Vector3f normalized()
    {
        float length = length();

        return new Vector3f(x / length, y / length, z / length);
    }

    @SuppressWarnings("WeakerAccess")
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    @NotNull
    public Vector3f rotate(@NotNull Vector3f axis, float angle)
    {
        float sinAngle = (float) Math.sin(-angle);
        float cosAngle = (float) Math.cos(-angle);
        return this.cross(axis.mul(sinAngle)).add((this.mul(cosAngle)).add(axis.mul(this.dot(axis.mul(1 - cosAngle)))));
    }

    @NotNull
    public Vector3f add(@NotNull Vector3f r)
    {
        return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
    }

    @NotNull
    public Vector3f cross(@NotNull Vector3f r)
    {
        float x_ = y * r.getZ() - z * r.getY();
        float y_ = z * r.getX() - x * r.getZ();
        float z_ = x * r.getY() - y * r.getX();

        return new Vector3f(x_, y_, z_);
    }

    @NotNull
    public Vector3f mul(float r)
    {
        return new Vector3f(x * r, y * r, z * r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public float dot(@NotNull Vector3f r)
    {
        return x * r.getX() + y * r.getY() + z * r.getZ();
    }

    public float getX()
    {
        return x;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setY(float y)
    {
        this.y = y;
    }

    public float getZ()
    {
        return z;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setZ(float z)
    {
        this.z = z;
    }

    @NotNull
    public Vector3f rotate(@NotNull Quaternion rotation)
    {
        Quaternion conjugate = rotation.conjugated();
        Quaternion w = rotation.mul(this).mul(conjugate);
        return new Vector3f(w.getX(), w.getY(), w.getZ());
    }

    @NotNull
    public Vector3f lerp(@NotNull Vector3f dest, @NotNull Vector3f lerpFactor)
    {
        return dest.sub(this).mul(lerpFactor).add(this);
    }

    @NotNull
    @SuppressWarnings("UnusedDeclaration")
    public Vector3f mul(@NotNull Vector3f r)
    {
        return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
    }

    @NotNull
    public Vector3f sub(@NotNull Vector3f r)
    {
        return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
    }

    @NotNull
    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getXY() {
        return new Vector2f(x, y);
    }

    @NotNull
    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getYZ() {
        return new Vector2f(y, z);
    }

    @NotNull
    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getZX() {
        return new Vector2f(z, x);
    }

    @NotNull
    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getYX() {
        return new Vector2f(y, x);
    }

    @NotNull
    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getZY() {
        return new Vector2f(z, y);
    }

    @NotNull
    @SuppressWarnings("SuspiciousNameCombination")
    public Vector2f getXZ() {
        return new Vector2f(x, z);
    }

    @NotNull
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    @NotNull
    public Vector3f set(@NotNull Vector3f r)
    {
        this.x = r.getX();
        this.y = r.getY();
        this.z = r.getZ();

        return this;
    }

    @NotNull
    @SuppressWarnings("UnusedDeclaration")
    public Vector3f add(float r) {
        return new Vector3f(x + r, y + r, z + r);
    }

    @NotNull
    @SuppressWarnings("UnusedDeclaration")
    public Vector3f sub(float r) {
        return new Vector3f(x - r, y - r, z - r);
    }

    @NotNull
    @SuppressWarnings("UnusedDeclaration")
    public Vector3f div(@NotNull Vector3f r)
    {
        return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
    }

    @NotNull
    @SuppressWarnings("UnusedDeclaration")
    public Vector3f div(float r) {
        return new Vector3f(x / r, y / r, z / r);
    }

    @NotNull
    @SuppressWarnings("UnusedDeclaration")
    public Vector3f abs() {
        return new Vector3f(Math.abs(x), Math.abs(y), Math.abs(z));
    }

    public boolean equals(@Nullable Vector3f compared)
    {
        return this == compared || compared != null && Float.compare(compared.x, x) == 0 && Float.compare(compared.y, y) == 0 && Float.compare(compared.z, z) == 0;
    }

    @Override
    public int hashCode() {
        int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
        result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
        result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
        return result;
    }

    @NotNull
    @Override
    public String toString() {
        return "(" + x + " " + y + " " + z + ")";
    }
}
