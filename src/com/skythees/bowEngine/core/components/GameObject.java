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

import com.skythees.bowEngine.core.Transform;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.Shader;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

/**
 * Created on 15.3.2015. at 2:16.
 */
public class GameObject
{
	private final ArrayList<GameObject>    children;
	private final ArrayList<GameComponent> components;
	private final Transform                transform;

	public GameObject()
	{
		this.children = new ArrayList<>();
		this.components = new ArrayList<>();
		this.transform = new Transform();
	}

	@NotNull
	public GameObject addChild(@NotNull GameObject child)
	{
		children.add(child);
		child.getTransform().setParent(transform);
		return this;
	}

	public Transform getTransform()
	{
		return transform;
	}

	@SuppressWarnings("unused")
	@NotNull
	public GameObject addComponent(@NotNull GameComponent component)
	{
		components.add(component);
		component.setParent(this);
		return this;
	}

	public void input(float delta)
	{
		transform.update();
		components.forEach(component->component.input(delta));
		children.forEach(child->child.input(delta));
	}

	public void update(float delta)
	{
		components.forEach(component->component.update(delta));
		children.forEach(child->child.update(delta));
	}

	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		components.forEach(component->component.render(shader, renderingEngine));
		children.forEach(child->child.render(shader, renderingEngine));
	}

	public void addToRenderingEngine(RenderingEngine renderingEngine)
	{
		components.forEach(component->component.addToRenderingEngine(renderingEngine));
		children.forEach(child->child.addToRenderingEngine(renderingEngine));
	}
}
