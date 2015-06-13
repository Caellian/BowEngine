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

package com.skythees.bowEngine.render.resources;

import com.skythees.bowEngine.core.math.Vector3f;

import java.util.HashMap;

/**
 * Created by caellian on 13.06.15., at 11:40.
 */
public abstract class MapStorage
{
	protected final HashMap<String, Vector3f>  vector3fHashMap = new HashMap<>();
	protected final HashMap<String, Byte>      byteHashMap     = new HashMap<>();
	protected final HashMap<String, Short>     shortHashMap    = new HashMap<>();
	protected final HashMap<String, Integer>   integerHashMap  = new HashMap<>();
	protected final HashMap<String, Long>      longHashMap     = new HashMap<>();
	protected final HashMap<String, Float>     floatHashMap    = new HashMap<>();
	protected final HashMap<String, Double>    doubleHashMap   = new HashMap<>();
	protected final HashMap<String, Boolean>   booleanHashMap  = new HashMap<>();
	protected final HashMap<String, Character> charHashMap     = new HashMap<>();

	public HashMap<String, Vector3f> getVector3fHashMap()
	{
		return vector3fHashMap;
	}

	public HashMap<String, Byte> getByteHashMap()
	{
		return byteHashMap;
	}

	public HashMap<String, Short> getShortHashMap()
	{
		return shortHashMap;
	}

	public HashMap<String, Integer> getIntegerHashMap()
	{
		return integerHashMap;
	}

	public HashMap<String, Long> getLongHashMap()
	{
		return longHashMap;
	}

	public HashMap<String, Float> getFloatHashMap()
	{
		return floatHashMap;
	}

	public HashMap<String, Double> getDoubleHashMap()
	{
		return doubleHashMap;
	}

	public HashMap<String, Boolean> getBooleanHashMap()
	{
		return booleanHashMap;
	}

	public HashMap<String, Character> getCharHashMap()
	{
		return charHashMap;
	}
}
