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
	private CoreEngine engine;

	public GameObject()
	{
		this.children = new ArrayList<>();
		this.components = new ArrayList<>();
		this.transform = new Transform();

		engine = null;
	}

	@NotNull
	public GameObject addChild(@NotNull GameObject child)
	{
		children.add(child);
		child.setEngine(engine);
		child.getTransform().setParent(transform);
		return this;
	}

	public ArrayList<GameObject> getAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<>();
		children.forEach(child->result.addAll(child.getAllAttached()));
		result.add(this);
		return result;
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

	public void inputAll(float delta)
	{
		input(delta);

		transform.update();
		components.forEach(component->component.input(delta));
		children.forEach(child->child.inputAll(delta));
	}

	public void updateAll(float delta)
	{
		update(delta);

		components.forEach(component->component.update(delta));
		children.forEach(child->child.updateAll(delta));
	}

	public void renderAll(Shader shader, RenderingEngine renderingEngine)
	{
		render(shader, renderingEngine);

		components.forEach(component->component.render(shader, renderingEngine));
		children.forEach(child->child.renderAll(shader, renderingEngine));
	}

	public void input(float delta)
	{
		transform.update();
		components.forEach(component->component.input(delta));
	}

	public void update(float delta)
	{
		components.forEach(component->component.update(delta));
	}

	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		components.forEach(component->component.render(shader, renderingEngine));
	}

	public void setEngine(CoreEngine engine)
	{
		if (this.engine != engine)
		{
			this.engine = engine;

			components.forEach(component->component.addToEngine(engine));
			children.forEach(child->child.setEngine(engine));
		}
	}
}
