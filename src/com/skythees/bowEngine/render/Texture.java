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

import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.FileInputStream;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;

public class Texture {
    private final int id;

    public Texture(String fileName) {
        this(loadTexture(fileName));
    }

    @SuppressWarnings("WeakerAccess")
    public Texture(int id) {
        this.id = id;
    }

    @SuppressWarnings("UnusedDeclaration")
    public static int loadTexture(String texture) {
        String[] nameArray = texture.split("\\.");
        String ext = nameArray[nameArray.length - 1];

        try {
            File textureFile = new File("./resources/textures/" + texture);
            return TextureLoader.getTexture(ext, new FileInputStream(textureFile)).getTextureID();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return 0;
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    @SuppressWarnings("UnusedDeclaration")
    public int getID() {
        return id;
    }
}
