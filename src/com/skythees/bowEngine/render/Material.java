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

import com.skythees.bowEngine.core.math.Vector3f;

import java.util.HashMap;

public class Material {
    private HashMap<String, Texture> textureHashMap;
    private HashMap<String, Vector3f> vector3fHashMap;
    private HashMap<String, Float> floatHashMap;

    public Material() {
        textureHashMap = new HashMap<>();
        vector3fHashMap = new HashMap<>();
        floatHashMap = new HashMap<>();
    }

    public Material addTexture(String name, Texture texture) {
        textureHashMap.put(name, texture);
        return this;
    }

    public Texture getTexture(String name) {
        return textureHashMap.get(name);
    }

    public Texture removeTexture(String name) {
        return textureHashMap.remove(name);
    }

    public Material clearTextures() {
        textureHashMap.clear();
        return this;
    }

    public Material addVector3f(String name, Vector3f texture) {
        vector3fHashMap.put(name, texture);
        return this;
    }

    public Vector3f getVector3f(String name) {
        Vector3f result = vector3fHashMap.get(name);
        return result != null ? result : new Vector3f(0, 0, 0);
    }

    public Vector3f removeVector3f(String name) {
        return vector3fHashMap.remove(name);
    }

    public Material clearVector3f() {
        vector3fHashMap.clear();
        return this;
    }

    public Material addFloat(String name, Float texture) {
        floatHashMap.put(name, texture);
        return this;
    }

    public Float getFloat(String name) {
        Float result = floatHashMap.get(name);
        return result != null ? result : 0;
    }

    public Float removeFloat(String name) {
        return floatHashMap.remove(name);
    }

    public Material clearFloats() {
        floatHashMap.clear();
        return this;
    }


}
