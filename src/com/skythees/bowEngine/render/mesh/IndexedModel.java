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

import java.util.ArrayList;

/**
 * Created on 21.3.2015. at 1:15.
 */
public class IndexedModel
{
	protected ArrayList<Vector3f> positions          = new ArrayList<>();
	protected ArrayList<Vector2f> textureCoordinates = new ArrayList<>();
	protected ArrayList<Vector3f> normals            = new ArrayList<>();
	protected ArrayList<Integer>  indices            = new ArrayList<>();

	public void calcNormals()
	{
		for (int i = 0; i < indices.size(); i += 3)
		{
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);
			Vector3f v1 = positions.get(i1).sub(positions.get(i0));
			Vector3f v2 = positions.get(i2).sub(positions.get(i0));
			Vector3f normal = v1.cross(v2).normalized();
			normals.get(i0).set(normals.get(i0).add(normal));
			normals.get(i1).set(normals.get(i1).add(normal));
			normals.get(i2).set(normals.get(i2).add(normal));
		}
		for (Vector3f normal : normals)
		{
			normal.set(normal.normalized());
		}
	}

	public ArrayList<Vector3f> getPositions()
	{
		return positions;
	}

	public void setPositions(ArrayList<Vector3f> positions)
	{
		this.positions = positions;
	}

	public ArrayList<Vector2f> getTextureCoordinates()
	{
		return textureCoordinates;
	}

	public void setTextureCoordinates(ArrayList<Vector2f> textureCoordinates)
	{
		this.textureCoordinates = textureCoordinates;
	}

	public ArrayList<Vector3f> getNormals()
	{
		return normals;
	}

	public void setNormals(ArrayList<Vector3f> normals)
	{
		this.normals = normals;
	}

	public ArrayList<Integer> getIndices()
	{
		return indices;
	}

	public void setIndices(ArrayList<Integer> indices)
	{
		this.indices = indices;
	}
}
