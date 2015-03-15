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

public class Matrix4f {
    private float[][] matrix;

    public Matrix4f() {
        matrix = new float[4][4];
    }

    @SuppressWarnings("UnusedDeclaration")
    public Matrix4f initIdentity() {
        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z) {
        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = x;
        matrix[1][0] = 0;
        matrix[1][1] = 1;
        matrix[1][2] = 0;
        matrix[1][3] = y;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = 1;
        matrix[2][3] = z;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f initRotation(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        x = (float) Math.toRadians(x);
        y = (float) Math.toRadians(y);
        z = (float) Math.toRadians(z);

        rz.matrix[0][0] = (float) Math.cos(z);
        rz.matrix[0][1] = -(float) Math.sin(z);
        rz.matrix[0][2] = 0;
        rz.matrix[0][3] = 0;
        rz.matrix[1][0] = (float) Math.sin(z);
        rz.matrix[1][1] = (float) Math.cos(z);
        rz.matrix[1][2] = 0;
        rz.matrix[1][3] = 0;
        rz.matrix[2][0] = 0;
        rz.matrix[2][1] = 0;
        rz.matrix[2][2] = 1;
        rz.matrix[2][3] = 0;
        rz.matrix[3][0] = 0;
        rz.matrix[3][1] = 0;
        rz.matrix[3][2] = 0;
        rz.matrix[3][3] = 1;

        rx.matrix[0][0] = 1;
        rx.matrix[0][1] = 0;
        rx.matrix[0][2] = 0;
        rx.matrix[0][3] = 0;
        rx.matrix[1][0] = 0;
        rx.matrix[1][1] = (float) Math.cos(x);
        rx.matrix[1][2] = -(float) Math.sin(x);
        rx.matrix[1][3] = 0;
        rx.matrix[2][0] = 0;
        rx.matrix[2][1] = (float) Math.sin(x);
        rx.matrix[2][2] = (float) Math.cos(x);
        rx.matrix[2][3] = 0;
        rx.matrix[3][0] = 0;
        rx.matrix[3][1] = 0;
        rx.matrix[3][2] = 0;
        rx.matrix[3][3] = 1;

        ry.matrix[0][0] = (float) Math.cos(y);
        ry.matrix[0][1] = 0;
        ry.matrix[0][2] = -(float) Math.sin(y);
        ry.matrix[0][3] = 0;
        ry.matrix[1][0] = 0;
        ry.matrix[1][1] = 1;
        ry.matrix[1][2] = 0;
        ry.matrix[1][3] = 0;
        ry.matrix[2][0] = (float) Math.sin(y);
        ry.matrix[2][1] = 0;
        ry.matrix[2][2] = (float) Math.cos(y);
        ry.matrix[2][3] = 0;
        ry.matrix[3][0] = 0;
        ry.matrix[3][1] = 0;
        ry.matrix[3][2] = 0;
        ry.matrix[3][3] = 1;

        matrix = rz.mul(ry.mul(rx)).getMatrix();

        return this;
    }

    public Matrix4f initScale(float x, float y, float z) {
        matrix[0][0] = x;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = y;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = z;
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f initPerspective(float fow, float aspectRatio, float clipNear, float clipFar) {
        float fowAngle = (float) Math.tan(fow / 2);
        float clipRange = clipNear - clipFar;

        matrix[0][0] = 1 / (fowAngle * aspectRatio);
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[0][3] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 1 / fowAngle;
        matrix[1][2] = 0;
        matrix[1][3] = 0;
        matrix[2][0] = 0;
        matrix[2][1] = 0;
        matrix[2][2] = (-clipNear - clipFar) / clipRange;
        matrix[2][3] = 2 * clipFar * clipNear / clipRange;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 1;
        matrix[3][3] = 0;

        return this;
    }

    public Matrix4f initRotation(Vector3f forward, Vector3f up) {

        Vector3f forward0 = forward.normalized();

        Vector3f r = up;
        r.normalized();
        r = r.cross(forward0);

        Vector3f u = forward0.cross(r);

        matrix[0][0] = r.getX();
        matrix[0][1] = r.getY();
        matrix[0][2] = r.getZ();
        matrix[0][3] = 0;
        matrix[1][0] = u.getX();
        matrix[1][1] = u.getY();
        matrix[1][2] = u.getZ();
        matrix[1][3] = 0;
        matrix[2][0] = forward0.getX();
        matrix[2][1] = forward0.getY();
        matrix[2][2] = forward0.getZ();
        matrix[2][3] = 0;
        matrix[3][0] = 0;
        matrix[3][1] = 0;
        matrix[3][2] = 0;
        matrix[3][3] = 1;

        return this;
    }

    public Matrix4f mul(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j, matrix[i][0] * r.get(0, j) +
                        matrix[i][1] * r.get(1, j) +
                        matrix[i][2] * r.get(2, j) +
                        matrix[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    @SuppressWarnings("WeakerAccess")
    public float[][] getMatrix() {
        float[][] result = new float[4][4];

        for (final int posX : new int[]{0, 1, 2, 3})
            for (final int posY : new int[]{0, 1, 2, 3})
                result[posX][posY] = matrix[posX][posY];

        return result;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setMatrix(float[][] matrix) {
        this.matrix = matrix;
    }

    public float get(int x, int y) {
        return matrix[x][y];
    }

    @SuppressWarnings("WeakerAccess")
    public void set(int x, int y, float value) {
        matrix[x][y] = value;
    }
}
