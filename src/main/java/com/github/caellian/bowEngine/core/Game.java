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

package com.github.caellian.bowEngine.core;

import com.github.caellian.bowEngine.core.components.GameObject;
import com.github.caellian.bowEngine.render.RenderingEngine;
import com.sun.istack.internal.NotNull;

/**
 * @author Caellian
 */
public abstract class Game
{
	@NotNull
	private GameObject root = new GameObject();

	public void init()
	{
	}

	public void input(float delta)
	{
		root.inputAll(delta);
	}

	public void update(float delta)
	{
		root.updateAll(delta);
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

	public void setEngine(CoreEngine engine)
	{
		root.setEngine(engine);
	}

	public GameObject getRoot()
	{
		return root;
	}

	public void setRoot(GameObject root)
	{
		this.root = root;
	}
}
