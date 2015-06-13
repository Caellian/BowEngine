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

package com.skythees.bowEngine.core;

import com.skythees.bowEngine.core.components.GameObject;
import com.skythees.bowEngine.render.RenderingEngine;
import com.sun.istack.internal.NotNull;

/**
 * Created on 15.3.2015. at 0:53.
 */
@SuppressWarnings("unused")
public abstract class Game
{
	@SuppressWarnings("CanBeFinal")
	@NotNull
	private GameObject root = new GameObject();

	public void init()
	{
	}

	public void input(float delta)
	{
		root.input(delta);
	}

	public void update(float delta)
	{
		root.update(delta);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Game addObject(GameObject gameObject)
	{
		root.addChild(gameObject);
		return this;
	}

	public void render(@NotNull RenderingEngine renderingEngine)
	{
		renderingEngine.render(root);
	}
}
