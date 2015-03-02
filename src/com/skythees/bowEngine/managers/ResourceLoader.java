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

package com.skythees.bowEngine.managers;

import com.skythees.bowEngine.math.vector.Vector3f;
import com.skythees.bowEngine.render.Mesh;
import com.skythees.bowEngine.render.Texture;
import com.skythees.bowEngine.render.Vertex;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Objects;

public class ResourceLoader {
    public static String loadShader(String shader) {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader = null;

        try {
            File shaderFile = new File("./resources/shaders/" + shader);
//            System.out.println(shaderFile.getAbsolutePath());
            shaderReader = new BufferedReader(new FileReader(shaderFile));
            String line;

            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n");
            }

            shaderReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return shaderSource.toString();
    }

    public static Mesh loadMesh(String mesh) {
        String[] nameArray = mesh.split("\\.");
        String ext = nameArray[nameArray.length - 1];

        if (!(Objects.equals(ext, "obj"))) {
            System.err.println("Error: Mesh data file format not supported:" + ext);
            new Exception().printStackTrace();
            System.exit(1);
        }

        ArrayList<Vertex> vertices = new ArrayList<>();
        ArrayList<Integer> indices = new ArrayList<>();


        StringBuilder shaderSource = new StringBuilder();
        BufferedReader meshReader = null;

        try {
            File meshFile = new File("./resources/models/" + mesh);
//            System.out.println(meshFile.getAbsolutePath());
            meshReader = new BufferedReader(new FileReader(meshFile));
            String line;

            while ((line = meshReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = DataUtil.removeEmptyStrings(tokens);

                if (tokens.length == 0 || tokens[0].equals("#")) {
                    continue;
                }
                if (tokens[0].equals("v")) {
                    vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                            Float.valueOf(tokens[2]),
                            Float.valueOf(tokens[3]))));
                } else if (tokens[0].equals("f")) {
                    indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
                    indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1);
                    if (tokens.length > 4) {
                        indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1);
                        indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1);
                    }
                }
            }

            meshReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Mesh resultMesh = new Mesh();
        Vertex[] vertexData = vertices.toArray(new Vertex[vertices.size()]);
        Integer[] indexData = indices.toArray(new Integer[indices.size()]);
        resultMesh.addVertices(vertexData, DataUtil.toIntArray(indexData));

        return resultMesh;
    }

    public static Texture loadTexture(String texture) {
        String[] nameArray = texture.split("\\.");
        String ext = nameArray[nameArray.length - 1];

        try {
            File textureFile = new File("./resources/textures/" + texture);
            int id = TextureLoader.getTexture(ext, new FileInputStream(textureFile)).getTextureID();
            return new Texture(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
