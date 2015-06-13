/*
 * BowEngine, modular and easy to use game engine
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.render;

import com.skythees.bowEngine.render.resources.MapStorage;
import com.sun.istack.internal.NotNull;

import java.util.HashMap;

public class Material extends MapStorage
{
	private final HashMap<String, Texture> textureHashMap;

	@SuppressWarnings("unused")
	public Material()
	{
		textureHashMap = new HashMap<>();
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
}
