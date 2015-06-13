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

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 * Created by Caellian on 05.06.15., at 15:59, at 16:01.
 */
public class TextureResource
{
	private final int id;
	private       int referenceCounter;

	public TextureResource()
	{
		this.id = glGenTextures();
		referenceCounter = 1;
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

	public int getId()
	{
		return id;
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		glDeleteBuffers(id);
	}
}
