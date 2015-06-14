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

package com.skythees.bowEngine.core.components;

import com.skythees.bowEngine.core.CoreEngine;
import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;

/**
 * Created on 15.3.2015. at 2:20.
 */
public abstract class GameComponent
{

	private GameObject parent;

	public void input(float delta)
	{
	}

	@SuppressWarnings({"EmptyMethod", "UnusedParameters"})
	public void update(float delta)
	{
	}

	public void render(Shader shader, RenderingEngine renderingEngine)
	{
	}

	public GameObject getParent()
	{
		return parent;
	}

	public void setParent(GameObject parent)
	{
		this.parent = parent;
	}

	public Transform getTransform()
	{
		return parent.getTransform();
	}

	public void addToEngine(CoreEngine engine)
	{

	}
}
