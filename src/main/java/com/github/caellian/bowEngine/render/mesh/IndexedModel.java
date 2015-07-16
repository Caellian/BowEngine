/*
 * BowEngine, modular and easy to use game engine
 * Copyright (C) 2015 Caellian
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

package com.github.caellian.bowEngine.render.mesh;

import com.github.caellian.bowEngine.core.math.Vector2f;
import com.github.caellian.bowEngine.core.math.Vector3f;

import java.util.ArrayList;

/**
 * Created on 21.3.2015. at 1:15.
 */
public class IndexedModel
{
	protected ArrayList<Vector3f> positions          = new ArrayList<>();
	protected ArrayList<Vector2f> textureCoordinates = new ArrayList<>();
	protected ArrayList<Vector3f> normals            = new ArrayList<>();
	protected ArrayList<Vector3f> tangents           = new ArrayList<>();
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

			normals.set(i0, normals.get(i0).add(normal));
			normals.set(i1, normals.get(i1).add(normal));
			normals.set(i2, normals.get(i2).add(normal));
		}
		for (Vector3f normal : normals)
		{
			normal.set(normal.normalized());
		}
	}

	@SuppressWarnings("unused")
	public void calcTangents()
	{
		for (int i = 0; i < indices.size(); i += 3)
		{
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);

			Vector3f edge1 = positions.get(i1).sub(positions.get(i0));
			Vector3f edge2 = positions.get(i2).sub(positions.get(i0));

			float deltaU1 = textureCoordinates.get(i1).getX() - textureCoordinates.get(i0).getX();
			float deltaV1 = textureCoordinates.get(i1).getY() - textureCoordinates.get(i0).getY();
			float deltaU2 = textureCoordinates.get(i2).getX() - textureCoordinates.get(i0).getX();
			float deltaV2 = textureCoordinates.get(i2).getY() - textureCoordinates.get(i0).getY();

			float dividend = (deltaU1 * deltaV2 - deltaU2 * deltaV1);
			float f = dividend == 0 ? 0.0f : 1.0f / dividend;

			Vector3f tangent = new Vector3f(0, 0, 0);
			tangent.setX(f * (deltaV2 * edge1.getX() - deltaV1 * edge2.getX()));
			tangent.setY(f * (deltaV2 * edge1.getY() - deltaV1 * edge2.getY()));
			tangent.setZ(f * (deltaV2 * edge1.getZ() - deltaV1 * edge2.getZ()));

			tangents.set(i0, tangents.get(i0).add(tangent));
			tangents.set(i1, tangents.get(i1).add(tangent));
			tangents.set(i2, tangents.get(i2).add(tangent));
		}

		for (Vector3f tangent : tangents)
		{
			tangent.set(tangent.normalized());
		}
	}

	@SuppressWarnings("WeakerAccess")
	public ArrayList<Vector3f> getTangents()
	{
		return tangents;
	}

	@SuppressWarnings("unused")
	public void setTangents(ArrayList<Vector3f> tangents)
	{
		this.tangents = tangents;
	}

	public ArrayList<Vector3f> getPositions()
	{
		return positions;
	}

	@SuppressWarnings("unused")
	public void setPositions(ArrayList<Vector3f> positions)
	{
		this.positions = positions;
	}

	public ArrayList<Vector2f> getTextureCoordinates()
	{
		return textureCoordinates;
	}

	@SuppressWarnings("unused")
	public void setTextureCoordinates(ArrayList<Vector2f> textureCoordinates)
	{
		this.textureCoordinates = textureCoordinates;
	}

	public ArrayList<Vector3f> getNormals()
	{
		return normals;
	}

	@SuppressWarnings("unused")
	public void setNormals(ArrayList<Vector3f> normals)
	{
		this.normals = normals;
	}

	public ArrayList<Integer> getIndices()
	{
		return indices;
	}

	@SuppressWarnings("unused")
	public void setIndices(ArrayList<Integer> indices)
	{
		this.indices = indices;
	}
}
