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

package com.caellian.bowEngine.render;

import com.caellian.bowEngine.core.math.Vector2f;
import com.caellian.bowEngine.core.math.Vector3f;

public class Vertex
{
	public static final int SIZE = 8;

	private Vector3f pos;
	private Vector2f texturePos;
	private Vector3f normal;

	@SuppressWarnings("unused")
	public Vertex(Vector3f pos)
	{
		this(pos, new Vector2f(0, 0));
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Vertex(Vector3f pos, Vector2f texturePos)
	{
		this(pos, texturePos, new Vector3f(0, 0, 0));
	}

	@SuppressWarnings({"UnusedDeclaration", "WeakerAccess", "unused"})
	public Vertex(Vector3f pos, Vector2f texturePos, Vector3f normal)
	{
		this.pos = pos;
		this.texturePos = texturePos;
		this.normal = normal;
	}

	public Vector3f getPos()
	{
		return pos;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setPos(Vector3f pos)
	{
		this.pos = pos;
	}

	public Vector2f getTexturePos()
	{
		return texturePos;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setTexturePos(Vector2f texturePos)
	{
		this.texturePos = texturePos;
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public void setNormal(Vector3f normal)
	{
		this.normal = normal;
	}
}
