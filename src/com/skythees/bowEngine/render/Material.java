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
import com.sun.istack.internal.NotNull;

import java.util.HashMap;

public class Material
{
	private final HashMap<String, Texture>  textureHashMap;
	private final HashMap<String, Vector3f> vector3fHashMap;
	private final HashMap<String, Float>    floatHashMap;

	@SuppressWarnings("unused")
	public Material()
	{
		textureHashMap = new HashMap<>();
		vector3fHashMap = new HashMap<>();
		floatHashMap = new HashMap<>();
	}

	@SuppressWarnings("unused")
	@NotNull
	public Material addTexture(String name, Texture texture)
	{
		textureHashMap.put(name, texture);
		return this;
	}

	public Texture getTexture(@SuppressWarnings("SameParameterValue") String name)
	{
		return textureHashMap.get(name);
	}

	@SuppressWarnings("unused")
	public Texture removeTexture(String name)
	{
		return textureHashMap.remove(name);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Material clearTextures()
	{
		textureHashMap.clear();
		return this;
	}

	@SuppressWarnings("unused")
	@NotNull
	public Material addVector3f(String name, Vector3f texture)
	{
		vector3fHashMap.put(name, texture);
		return this;
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector3f getVector3f(String name)
	{
		Vector3f result = vector3fHashMap.get(name);
		return result != null ? result : new Vector3f(0, 0, 0);
	}

	@SuppressWarnings("unused")
	public Vector3f removeVector3f(String name)
	{
		return vector3fHashMap.remove(name);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Material clearVector3f()
	{
		vector3fHashMap.clear();
		return this;
	}

	@SuppressWarnings("unused")
	@NotNull
	public Material addFloat(String name, Float texture)
	{
		floatHashMap.put(name, texture);
		return this;
	}

	@NotNull
	public Float getFloat(String name)
	{
		Float result = floatHashMap.get(name);
		return result != null ? result : 0;
	}

	@SuppressWarnings("unused")
	public Float removeFloat(String name)
	{
		return floatHashMap.remove(name);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Material clearFloats()
	{
		floatHashMap.clear();
		return this;
	}

}
