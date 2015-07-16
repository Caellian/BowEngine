/*
 * BowEngine, modular and easy to use game engine
 * Copyright (C) 2015 Caellian
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

package com.github.caellian.bowEngine.render.resources;

import com.github.caellian.bowEngine.core.math.Vector3f;

import java.util.HashMap;

/**
 * @author Caellian
 */
public abstract class MapStorage
{
	protected HashMap<String, Vector3f>  vector3fHashMap;
	protected HashMap<String, Byte>      byteHashMap;
	protected HashMap<String, Short>     shortHashMap;
	protected HashMap<String, Integer>   integerHashMap;
	protected HashMap<String, Long>      longHashMap;
	protected HashMap<String, Float>     floatHashMap;
	protected HashMap<String, Double>    doubleHashMap;
	protected HashMap<String, Boolean>   booleanHashMap;
	protected HashMap<String, Character> charHashMap;

	public HashMap<String, Vector3f> getVector3fHashMap()
	{
		if (vector3fHashMap == null)
		{
			vector3fHashMap = new HashMap<>(0);
		}
		return vector3fHashMap;
	}

	public HashMap<String, Byte> getByteHashMap()
	{
		if (byteHashMap == null)
		{
			byteHashMap = new HashMap<>(0);
		}
		return byteHashMap;
	}

	public HashMap<String, Short> getShortHashMap()
	{
		if (shortHashMap == null)
		{
			shortHashMap = new HashMap<>(0);
		}
		return shortHashMap;
	}

	public HashMap<String, Integer> getIntegerHashMap()
	{
		if (integerHashMap == null)
		{
			integerHashMap = new HashMap<>(0);
		}
		return integerHashMap;
	}

	public HashMap<String, Long> getLongHashMap()
	{
		if (longHashMap == null)
		{
			longHashMap = new HashMap<>(0);
		}
		return longHashMap;
	}

	public HashMap<String, Float> getFloatHashMap()
	{
		if (floatHashMap == null)
		{
			floatHashMap = new HashMap<>(0);
		}
		return floatHashMap;
	}

	public HashMap<String, Double> getDoubleHashMap()
	{
		if (doubleHashMap == null)
		{
			doubleHashMap = new HashMap<>(0);
		}
		return doubleHashMap;
	}

	public HashMap<String, Boolean> getBooleanHashMap()
	{
		if (booleanHashMap == null)
		{
			booleanHashMap = new HashMap<>(0);
		}
		return booleanHashMap;
	}

	public HashMap<String, Character> getCharHashMap()
	{
		if (charHashMap == null)
		{
			charHashMap = new HashMap<>(0);
		}
		return charHashMap;
	}
}
