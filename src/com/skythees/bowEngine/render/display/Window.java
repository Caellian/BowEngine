/*
 * Software developed by Skythees
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
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.render.display;

import com.skythees.bowEngine.core.math.Vector2f;
import com.sun.istack.internal.NotNull;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.Dimension;

@SuppressWarnings({"WeakerAccess", "UnusedDeclaration"})
public class Window {
    @SuppressWarnings("UnusedDeclaration")
    public static void createWindow(int width, int height, String title) {
        Display.setTitle(title);
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create();
            Keyboard.create();
            Mouse.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void render() {
        Display.update();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static void dispose() {
        Display.destroy();
        Keyboard.destroy();
        Mouse.destroy();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static boolean isCloseRequested() {
        return Display.isCloseRequested();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static String getTitle() {
        return Display.getTitle();
    }

    public static void setDimensions(@NotNull Dimension dimensions)
    {
        try {
            Display.setDisplayMode(new DisplayMode(dimensions.getWidth(), dimensions.getHeight()));
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public static Vector2f centerPosition() {
        return new Vector2f(getWidth() / 2, getHeight() / 2);
    }

    @SuppressWarnings("UnusedDeclaration")
    public static int getWidth()
    {
        return Display.getDisplayMode().getWidth();
    }

    @SuppressWarnings("UnusedDeclaration")
    public static int getHeight()
    {
        return Display.getDisplayMode().getHeight();
    }
}
