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
