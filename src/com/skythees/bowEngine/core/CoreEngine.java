package com.skythees.bowEngine.core;

import com.skythees.bowEngine.core.util.Time;
import com.skythees.bowEngine.core.util.input.InputHelper;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.display.Window;
import org.lwjgl.util.Dimension;

public class CoreEngine {

    private final Game game;
    private boolean isRunning;
    private RenderingEngine renderingEngine;
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
    }

    public void start() {
        if (isRunning)
            return;

        run();
    }

    public void stop() {
        if (!isRunning)
            return;

        isRunning = false;
    }

    private void run() {
        isRunning = true;

        int frames = 0;
        float frameCounter = 0;

        game.init();

        double lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;

            double startTime = Time.getTime();
            double passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime;
            frameCounter += passedTime;

            while (unprocessedTime > frametime) {
                render = true;

                unprocessedTime -= frametime;

                if (Window.isCloseRequested())
                    stop();

                game.input((float) frametime);
                InputHelper.update();

                game.update((float) frametime);

                if (frameCounter >= 1f) {
                    System.out.println(frames);
                    frames = 0;
                    frameCounter = 0;
                }
            }
            if (render) {
                renderingEngine.render(game.getRootObject());
                Window.render();
                frames++;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        cleanUp();
    }

    private void render() {
    }

    private void cleanUp() {
        Window.dispose();
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
