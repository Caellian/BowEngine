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

package com.caellian.bowEngine.core.components;

import com.caellian.bowEngine.core.CoreEngine;
import com.caellian.bowEngine.core.Transform;
import com.caellian.bowEngine.render.RenderingEngine;
import com.caellian.bowEngine.render.Shader;

/**
 * @author Caellian
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
