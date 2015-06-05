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

package com.skythees.bowEngine.render;

import com.skythees.bowEngine.core.util.helpers.DataUtil;
import com.skythees.bowEngine.render.resourceManagement.TextureResource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class Texture
{
	private final static int                              BYTES_PER_PIXEL = 4;
	private static       HashMap<String, TextureResource> resourceHashMap = new HashMap<>();
	private final String          fileName;
	private       TextureResource resource;

	public Texture(String fileName)
	{
		this.fileName = fileName;
		resource = resourceHashMap.get(fileName);

		if (resource != null)
		{
			resource.increaseReference();
		}
		else
		{
			resource = new TextureResource(loadTexture(fileName));
			resourceHashMap.put(fileName, resource);
		}
	}

	@SuppressWarnings("UnusedDeclaration")
	public static int loadTexture(String texture)
	{
		String[] nameArray = texture.split("\\.");
		String ext = nameArray[nameArray.length - 1];

		try
		{
			File textureFile = new File("" + texture);
			BufferedImage image = ImageIO.read(textureFile);
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());

			ByteBuffer imageBuffer = DataUtil.createByteBuffer(image.getHeight() * image.getWidth() * BYTES_PER_PIXEL);
			boolean hasAlpha = image.getColorModel().hasAlpha();

			for (int x = 0; x < image.getHeight(); x++)
			{
				for (int y = 0; y < image.getWidth(); y++)
				{
					int pixel = pixels[y * image.getWidth() + x];

					imageBuffer.put((byte) ((pixel >> 16) & 0xFF));
					imageBuffer.put((byte) ((pixel >> 8) & 0xFF));
					imageBuffer.put((byte) ((pixel) & 0xFF));
					if (hasAlpha)
					{
						imageBuffer.put((byte) ((pixel >> 24) & 0xFF));
					}
					else
					{
						imageBuffer.put((byte) 0xFF);
					}
				}
			}
			imageBuffer.flip();

			int id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);

			return id;
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}

		return 0;
	}

	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, resource.getId());
	}

	public TextureResource getResource()
	{
		return resource;
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		if (resource.decreaseReference())
		{
			resourceHashMap.remove(fileName);
		}
	}
}
