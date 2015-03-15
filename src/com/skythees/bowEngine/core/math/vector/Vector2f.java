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

public class Vector2f {
    private float x;
    private float y;

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @SuppressWarnings("WeakerAccess")
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    @SuppressWarnings("UnusedDeclaration")
    public float dot(Vector2f r) {
        return x * r.getX() + y * r.getY();
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f normalized() {
        float length = length();

        return new Vector2f(x / length, y / length);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f rotate(float angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f add(Vector2f r) {
        return new Vector2f(x + r.getX(), y + r.getY());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f add(float r) {
        return new Vector2f(x + r, y + r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f sub(Vector2f r) {
        return new Vector2f(x - r.getX(), y - r.getY());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f sub(float r) {
        return new Vector2f(x - r, y - r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f mul(Vector2f r) {
        return new Vector2f(x * r.getX(), y * r.getY());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f mul(float r) {
        return new Vector2f(x * r, y * r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f div(Vector2f r) {
        return new Vector2f(x / r.getX(), y / r.getY());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f div(float r) {
        return new Vector2f(x / r, y / r);
    }

    @SuppressWarnings("UnusedDeclaration")
    public Vector2f abs() {
        return new Vector2f(Math.abs(x), Math.abs(y));
    }

    public String toString() {
        return "(" + x + " " + y + ")";
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
}
