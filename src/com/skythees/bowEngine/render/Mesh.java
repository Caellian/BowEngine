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

package com.skythees.bowEngine.render;

import com.skythees.bowEngine.core.math.Vector3f;
import com.skythees.bowEngine.core.util.helpers.DataUtil;
import com.skythees.bowEngine.render.mesh.IndexedModel;
import com.skythees.bowEngine.render.mesh.OBJModel;
import com.skythees.bowEngine.render.resourceManagement.MeshResource;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh
{
	@NotNull
	private static final HashMap<String, MeshResource> resourceHashMap = new HashMap<>();
	private final String       fileName;
	private       MeshResource resource;

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Mesh(@NotNull String fileName)
	{
		this.fileName = fileName;
		resource = resourceHashMap.get(fileName);

		if (null != resource)
		{
			resource.increaseReference();
		}
		else
		{
			loadMesh(fileName);
			resourceHashMap.put(fileName, resource);
		}
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	private void loadMesh(@NotNull String mesh)
	{
		String[] nameArray = mesh.split("\\.");
		String ext = nameArray[nameArray.length - 1];

		if (!(Objects.equals(ext, "obj")))
		{
			System.err.println("Error: Mesh data file format not supported:" + ext);
			new Exception().printStackTrace();
			System.exit(1);
		}

		OBJModel objModel = new OBJModel(mesh);
		IndexedModel model = objModel.toIndexedModel();
		model.calcNormals();

		ArrayList<Vertex> vertices = new ArrayList<>();

		for (int i = 0; i < model.getPositions().size(); i++)
		{
			vertices.add(new Vertex(model.getPositions().get(i), model.getTextureCoordinates().get(i), model.getNormals().get(i)));
		}

		Vertex[] vertexData = vertices.toArray(new Vertex[vertices.size()]);
		Integer[] indexData = model.getIndices().toArray(new Integer[model.getIndices().size()]);

		addVertices(vertexData, DataUtil.toIntPrimitive(indexData), false);
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	private void addVertices(@NotNull Vertex[] vertices, @NotNull int[] indices, boolean calcNormals)
	{
		if (calcNormals)
		{
			calcNormals(vertices, indices);
		}

		resource = new MeshResource(indices.length);

		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glBufferData(GL_ARRAY_BUFFER, DataUtil.createFlippedBuffer(vertices), GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, DataUtil.createFlippedBuffer(indices), GL_STATIC_DRAW);
	}

	private void calcNormals(@NotNull Vertex[] vertices, @NotNull int[] indices)
	{
		for (int count = 0; count < indices.length; count += 3)
		{
			int i0 = indices[count];
			int i1 = indices[count + 1];
			int i2 = indices[count + 2];

			Vector3f v1 = vertices[i1].getPos().sub(vertices[i0].getPos());
			Vector3f v2 = vertices[i2].getPos().sub(vertices[i0].getPos());

			Vector3f normal = v1.cross(v2).normalized();

			vertices[i0].setNormal(vertices[i0].getNormal().add(normal));
			vertices[i1].setNormal(vertices[i1].getNormal().add(normal));
			vertices[i2].setNormal(vertices[i2].getNormal().add(normal));
		}

		for (Vertex vertex : vertices)
		{
			vertex.setNormal(vertex.getNormal().normalized());
		}
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Mesh(@NotNull Vertex[] vertices, @NotNull int[] indices)
	{
		this(vertices, indices, false);
	}

	@SuppressWarnings({"SameParameterValue", "WeakerAccess"})
	public Mesh(@NotNull Vertex[] vertices, @NotNull int[] indices, boolean calcNormals)
	{
		fileName = "";
		addVertices(vertices, indices, calcNormals);
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void draw()
	{
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glBindBuffer(GL_ARRAY_BUFFER, resource.getVbo());
		glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0);
		glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12);
		glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, resource.getIbo());
		glDrawElements(GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		if (resource.decreaseReference() && !fileName.isEmpty())
		{
			resourceHashMap.remove(fileName);
		}
	}
}
