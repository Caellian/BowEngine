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

package com.skythees.bowEngine.render.mesh;

import com.skythees.bowEngine.core.math.Vector2f;
import com.skythees.bowEngine.core.math.Vector3f;
import com.skythees.bowEngine.core.util.helpers.DataUtil;
import com.sun.istack.internal.NotNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created on 21.3.2015. at 1:19.
 */
public class OBJModel extends IndexedModel
{
	@NotNull
	protected ArrayList<OBJIndex> indices              = new ArrayList<>();
	private   boolean             hasTextureCoorinates = false;
	private   boolean             hasNormals           = false;

	public OBJModel(@NotNull String fileName)
	{
		BufferedReader meshReader;
		try
		{
			File meshFile = new File(fileName);
			meshReader = new BufferedReader(new FileReader(meshFile));
			String line;

			while ((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = DataUtil.removeEmptyStrings(tokens);

				if (tokens.length == 0 || tokens[0].equals("#"))
				{
					continue;
				}

				switch (tokens[0])
				{
					case "v":
						positions.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
						break;
					case "vt":
						textureCoordinates.add(new Vector2f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2])));
						break;
					case "vn":
						normals.add(new Vector3f(Float.valueOf(tokens[1]), Float.valueOf(tokens[2]), Float.valueOf(tokens[3])));
						break;
					case "f":
						for (int counter = 0; counter < tokens.length - 3; counter++)
						{
							indices.add(parseOBJIndex(tokens[1]));
							indices.add(parseOBJIndex(tokens[2 + counter]));
							indices.add(parseOBJIndex(tokens[3 + counter]));
						}
				}
			}

			meshReader.close();
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	@NotNull
	private OBJIndex parseOBJIndex(@NotNull String token)
	{
		String[] values = token.split("/");

		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(values[0]) - 1;

		if (values.length > 1)
		{
			hasTextureCoorinates = true;
			result.textureCoordinateIndex = Integer.parseInt(values[1]) - 1;

			if (values.length > 2)
			{
				hasNormals = true;
				result.normalIndex = Integer.parseInt(values[2]) - 1;
			}
		}

		return result;
	}

	@NotNull
	public IndexedModel toIndexedModel()
	{
		IndexedModel result = new IndexedModel();
		IndexedModel normalModel = new IndexedModel();
		HashMap<OBJIndex, Integer> resultIndexMap = new HashMap<>();
		HashMap<Integer, Integer> normalIndexMap = new HashMap<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();

		for (OBJIndex currentIndex : indices)
		{
			Vector3f currentPosition = positions.get(currentIndex.vertexIndex);
			Vector2f currentTextureCoordinate;
			Vector3f currentNormal;

			if (hasTextureCoorinates)
			{
				currentTextureCoordinate = textureCoordinates.get(currentIndex.textureCoordinateIndex);
			}
			else
			{
				currentTextureCoordinate = new Vector2f(0, 0);
			}

			if (hasNormals)
			{
				currentNormal = normals.get(currentIndex.textureCoordinateIndex);
			}
			else
			{
				currentNormal = new Vector3f(0, 0, 0);
			}

			Integer modelVertexIndex = resultIndexMap.get(currentIndex);

			if (modelVertexIndex == null)
			{
				modelVertexIndex = result.getPositions().size();
				resultIndexMap.put(currentIndex, modelVertexIndex);

				result.getPositions().add(currentPosition);
				result.getTextureCoordinates().add(currentTextureCoordinate);
				if (hasNormals)
				{
					result.getNormals().add(currentNormal);
				}
			}

			Integer normalModelIndex = normalIndexMap.get(currentIndex.vertexIndex);

			if (normalModelIndex == null)
			{
				normalModelIndex = normalModel.getPositions().size();
				normalIndexMap.put(currentIndex.getVertexIndex(), normalModelIndex);

				normalModel.getPositions().add(currentPosition);
				normalModel.getTextureCoordinates().add(currentTextureCoordinate);
				normalModel.getNormals().add(currentNormal);
				normalModel.getTangents().add(new Vector3f(0, 0, 0));
			}

			result.getIndices().add(modelVertexIndex);
			normalModel.getIndices().add(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}
		if (!hasNormals)
		{
			normalModel.calcNormals();

			for (int normal = 0; normal < result.getPositions().size(); normal++)
			{
				result.getNormals().add(normalModel.getNormals().get(indexMap.get(normal)));
			}
		}

		return result;
	}

	public class OBJIndex
	{
		public int vertexIndex;
		public int textureCoordinateIndex;
		public int normalIndex;

		public int getVertexIndex()
		{
			return vertexIndex;
		}

		public void setVertexIndex(int vertexIndex)
		{
			this.vertexIndex = vertexIndex;
		}

		public int getTextureCoordinateIndex()
		{
			return textureCoordinateIndex;
		}

		public void setTextureCoordinateIndex(int textureCoordinateIndex)
		{
			this.textureCoordinateIndex = textureCoordinateIndex;
		}

		public int getNormalIndex()
		{
			return normalIndex;
		}

		public void setNormalIndex(int normalIndex)
		{
			this.normalIndex = normalIndex;
		}

		@Override
		public int hashCode()
		{
			int result = vertexIndex;
			result = 31 * result + textureCoordinateIndex;
			result = 31 * result + normalIndex;
			return result;
		}

		@Override
		public boolean equals(Object o)
		{
			if (this == o)
			{
				return true;
			}
			if (!(o instanceof OBJIndex))
			{
				return false;
			}

			OBJIndex objIndex = (OBJIndex) o;

			return normalIndex == objIndex.normalIndex && textureCoordinateIndex == objIndex.textureCoordinateIndex && vertexIndex == objIndex.vertexIndex;
		}
	}
}
