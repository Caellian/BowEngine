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

public class CoreEngine {

    private final Game game;
    private boolean isRunning;
    private RenderingEngine renderingEngine;
    private PhysicsEngine physicsEngine;
    private int width;
    private int height;
    private double frametime;

    public CoreEngine(int width, int height, int framerate, Game parGame) {
        isRunning = false;
        game = parGame;
        this.width = width;
        this.height = height;
        this.frametime = 1.0 / framerate;
    }

    public void createWindow(String title) {
        Window.createWindow(width, height, title);
        this.renderingEngine = new RenderingEngine();
        this.physicsEngine = new PhysicsEngine();
    }

    public void start() {
        if (isRunning)
            return;

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

			while (unprocessedTime > frametime)
			{
				render = true;

				unprocessedTime -= frametime;

				if (Window.isCloseRequested())
				{
					stop();
				}

				game.input((float) frametime);
				InputHelper.update();

				game.update((float) frametime);

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

    public void stop() {
        if (!isRunning)
            return;

        isRunning = false;
    }

	private void cleanUp()
	{
		Window.dispose();
	}

	private void render()
	{
	}

    public Game getGame() {
        return game;
    }

    public double getFrametime() {
        return frametime;
    }

    public void setFramerate(int framerate) {
        this.frametime = 1.0 / framerate;
    }

    public void setDimensions(Dimension dimensions) {
        Window.setDimensions(dimensions);
    }
}
