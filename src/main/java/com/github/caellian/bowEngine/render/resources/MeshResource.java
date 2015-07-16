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

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * Created on 1.4.2015. at 18:09.
 */
public class MeshResource
{
	private final int vbo;
	private final int ibo;
	private final int size;
	private       int referenceCounter;

	public MeshResource(int size)
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
	}

	public void increaseReference()
	{
		referenceCounter++;
	}

	public boolean decreaseReference()
	{
		referenceCounter--;
		return referenceCounter == 0;
	}

	public int getVbo()
	{
		return vbo;
	}

	public int getIbo()
	{
		return ibo;
	}

	public int getSize()
	{
		return size;
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}
}
