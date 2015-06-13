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

package com.skythees.bowEngine.render.gui;

import com.skythees.bowEngine.render.Texture;

import java.awt.*;

/**
 * Created by caellian on 10.06.15., at 01:25.
 */
public class GUI
{
	/**
	 * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
	 */
	public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
	{

	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't used anywhere in vanilla code.
	 */
	public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight)
	{

	}

	/**
	 * Draw a 1 pixel wide horizontal line. Args: x1, x2, y, color
	 */
	protected void drawHorizontalLine(int startX, int endX, int y, int color)
	{
		if (endX < startX)
		{
			int i1 = startX;
			startX = endX;
			endX = i1;
		}

		drawRect(startX, y, endX + 1, y + 1, color);
	}

	/**
	 * Draws a solid color rectangle with the specified coordinates and color (ARGB format). Args: x1, y1, x2, y2, color
	 */
	public static void drawRect(int left, int top, int right, int bottom, int color)
	{

	}

	/**
	 * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
	 */
	protected void drawVerticalLine(int x, int startY, int endY, int color)
	{
		if (endY < startY)
		{
			int i1 = startY;
			startY = endY;
			endY = i1;
		}

		drawRect(x, startY + 1, x + 1, endY, color);
	}

	/**
	 * Draws a rectangle with a vertical gradient between the specified colors (ARGB format). Args : x1, y1, x2, y2,
	 * topColor, bottomColor
	 */
	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor)
	{

	}

	/**
	 * Renders the specified text to the screen, center-aligned. Args : renderer, string, x, y, color
	 */
	public void drawCenteredString(Font fontRendererIn, String text, int x, int y, int color)
	{

	}

	/**
	 * Renders the specified text to the screen. Args : renderer, string, x, y, color
	 */
	public void drawString(Font fontRendererIn, String text, int x, int y, int color)
	{

	}

	/**
	 * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
	 */
	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height)
	{

	}

	/**
	 * Draws a textured rectangle using the texture currently bound to the TextureManager
	 */
	public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV)
	{

	}

	/**
	 * Draws a texture rectangle using the texture currently bound to the TextureManager
	 */
	public void drawTexturedModalRect(int xCoord, int yCoord, Texture textureSprite, int p_175175_4_, int p_175175_5_)
	{

	}
}
