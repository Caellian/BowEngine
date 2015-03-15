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
        long frameCounter = 0;

        game.init();

        long lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;

            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) Time.SECOND;
            frameCounter += passedTime;

            while (unprocessedTime > frametime) {
                render = true;

                unprocessedTime -= frametime;

                if (Window.isCloseRequested())
                    stop();

                Time.setDelta(frametime);

                game.input();
                renderingEngine.tmpInput();
                InputHelper.update();

                game.update();

                if (frameCounter >= Time.SECOND) {
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
