/*
 Bow Engine, modular game engine
 Copyright (C) 2015 Skythees

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.core;

import com.skythees.bowEngine.core.util.Time;
import com.skythees.bowEngine.core.util.input.InputHelper;
import com.skythees.bowEngine.physics.PhysicsEngine;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.display.Window;
import org.lwjgl.util.Dimension;

public class CoreEngine
{

	private final Game            game;
	private       boolean         isRunning;
	private       RenderingEngine renderingEngine;
	@SuppressWarnings({"FieldCanBeLocal", "unused"})
	private       PhysicsEngine   physicsEngine;
	@SuppressWarnings("CanBeFinal")
	private       int             width;
	@SuppressWarnings("CanBeFinal")
	private       int             height;
	private       double          frameTime;

	@SuppressWarnings("unused")
	public CoreEngine(int width, int height, int frameRate, Game parGame)
	{
		isRunning = false;
		game = parGame;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0 / frameRate;
	}

	@SuppressWarnings("unused")
	public void setFrameRate(int frameRate)
	{
		this.frameTime = 1.0 / frameRate;
	}

	@SuppressWarnings("unused")
	public void createWindow(String title)
	{
		Window.createWindow(width, height, title);
		this.renderingEngine = new RenderingEngine();
		this.physicsEngine = new PhysicsEngine();
	}

	@SuppressWarnings("unused")
	public void start()
	{
		if (isRunning)
		{
			return;
		}
		run();
	}

	private void run()
	{
		isRunning = true;

		int frames = 0;
		float frameCounter = 0f;

		game.init();

		double lastTime = Time.getTime();
		double unprocessedTime = 0d;

		while (isRunning)
		{
			boolean render = false;

			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;

			unprocessedTime += passedTime;
			frameCounter += passedTime;

			while (unprocessedTime > frameTime)
			{
				render = true;

				unprocessedTime -= frameTime;

				if (Window.isCloseRequested())
				{
					stop();
				}

				game.input((float) frameTime);
				InputHelper.update();

				game.update((float) frameTime);

				if (frameCounter >= 1f)
				{
					frames = 0;
					frameCounter = 0;
				}
			}
			if (render)
			{
				game.render(renderingEngine);
				Window.render();
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		cleanUp();
	}

	@SuppressWarnings("WeakerAccess")
	public void stop()
	{
		if (!isRunning)
		{
			return;
		}

		isRunning = false;
	}

	private void cleanUp()
	{
		Window.dispose();
	}

	@SuppressWarnings({"EmptyMethod", "unused"})
	private void render()
	{
	}

	@SuppressWarnings("unused")
	public Game getGame()
	{
		return game;
	}

	@SuppressWarnings("unused")
	public double getFrameTime()
	{
		return frameTime;
	}

	@SuppressWarnings("unused")
	public void setDimensions(Dimension dimensions)
	{
		Window.setDimensions(dimensions);
	}
}
