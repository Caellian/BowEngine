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

package com.skythees.bowEngine.core;

import com.skythees.bowEngine.core.math.Matrix4f;
import com.skythees.bowEngine.core.math.Quaternion;
import com.skythees.bowEngine.core.math.Vector3f;
import com.sun.istack.internal.NotNull;

public class Transform
{
	private Matrix4f   parentMatrix;
	private Transform  parent;
	private Vector3f   pos;
	private Quaternion rot;
	private Vector3f   scale;

	private Vector3f   oldPos;
	private Quaternion oldRot;
	private Vector3f   oldScale;

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Transform()
	{
		pos = new Vector3f(0, 0, 0);
		rot = new Quaternion(0, 0, 0, 1);
		scale = new Vector3f(1, 1, 1);

		parentMatrix = new Matrix4f().initIdentity();
	}

	public void update()
	{
		if (oldPos != null)
		{
			oldPos.set(pos);
			oldRot.set(rot);
			oldScale.set(scale);
		}
		else
		{
			oldPos = new Vector3f(0, 0, 0).set(pos).add(1f);
			oldRot = new Quaternion(0, 0, 0, 0).set(rot);
			oldScale = new Vector3f(0, 0, 0).set(scale).add(1f);
		}
	}

	@SuppressWarnings("WeakerAccess")
	public boolean hasChanged()
	{

		return parent != null && parent.hasChanged() || pos.equals(oldPos) || rot.equals(oldRot) || scale.equals(oldScale);
	}

	@NotNull
	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(pos.getX(), pos.getY(), pos.getZ());
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale.getX(), scale.getY(), scale.getZ());

		return getParentMatrix().mul(translationMatrix.mul(rotationMatrix.mul(scaleMatrix)));
	}

	private Matrix4f getParentMatrix()
	{
		if (parent != null && parent.hasChanged())
		{
			parentMatrix = parent.getTransformation();
		}
		return parentMatrix;
	}

	public void setParent(Transform parent)
	{
		this.parent = parent;
	}

	@NotNull
	public Vector3f getTransformedPosition()
	{
		return getParentMatrix().transform(pos);
	}

	@NotNull
	public Quaternion getTransformedRotation()
	{
		Quaternion parentRotation = new Quaternion(0, 0, 0, 1);

		if (parent != null)
		{
			parentRotation = parent.getTransformedRotation();
		}

		return parentRotation.mul(rot);
	}

	public void rotate(@NotNull Vector3f axis, float angle)
	{
		rot = new Quaternion(axis, angle).mul(rot).normalized();
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Vector3f getPos()
	{
		return pos;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setPos(Vector3f pos)
	{
		this.pos = pos;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Quaternion getRot()
	{
		return rot;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setRot(Quaternion rot)
	{
		this.rot = rot;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Vector3f getScale()
	{
		return scale;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setScale(Vector3f scale)
	{
		this.scale = scale;
	}
}
