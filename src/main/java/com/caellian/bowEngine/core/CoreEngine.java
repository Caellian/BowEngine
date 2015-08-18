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

package com.caellian.bowEngine.core;

import com.caellian.bowEngine.core.util.Time;
import com.caellian.bowEngine.core.util.input.InputHelper;
import com.caellian.bowEngine.physics.PhysicsEngine;
import com.caellian.bowEngine.render.RenderingEngine;
import com.caellian.bowEngine.render.display.Window;
import org.lwjgl.util.Dimension;

public class CoreEngine
{

	private final Game            game;
	private       boolean         isRunning;
	private       RenderingEngine renderingEngine;
	private       PhysicsEngine   physicsEngine;
	private       int             width;
	private       int             height;
	private       double          frameTime;
	
	public CoreEngine(int width, int height, int frameRate, Game parGame)
	{
		this(width, height, frameRate, parGame, new PhysicsEngine());
	}

	public CoreEngine(int width, int height, int frameRate, Game parGame, PhysicsEngine physicsEngine)
	{
		isRunning = false;
		game = parGame;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0 / frameRate;
		game.setEngine(this);
		
		this.physicsEngine = physicsEngine;
	}

	public void setFrameRate(int frameRate)
	{
		this.frameTime = 1.0 / frameRate;
	}

	public void createWindow(String title)
	{
		Window.createWindow(width, height, title);
		this.renderingEngine = new RenderingEngine();
	}

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

			physicsEngine.update(game.getRoot());
			while (unprocessedTime > frameTime)
			{
				render = true;

				unprocessedTime -= frameTime;

				if (Window.isCloseRequested()) {
					stop();
				}

				game.input((float) frameTime);
				InputHelper.update();
				
				game.update((float) frameTime);

				if (frameCounter >= 1f)
				{
					frameCounter = 0;
				}
			}
			if (render)
			{
				game.render(renderingEngine);
				renderingEngine.renderGUI();
				Window.render();
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

	public Game getGame()
	{
		return game;
	}

	public double getFrameTime()
	{
		return frameTime;
	}

	public void setDimensions(Dimension dimensions)
	{
		Window.setDimensions(dimensions);
	}

	public RenderingEngine getRenderingEngine()
	{
		return renderingEngine;
	}

	public PhysicsEngine getPhysicsEngine()
	{
		return physicsEngine;
	}
}
