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

package com.github.caellian.bowEngine.core.math;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class Quaternion
{
	private float x;
	private float y;
	private float z;
	private float w;

	public Quaternion(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public Quaternion(@NotNull Vector3f axis, float angle)
	{
		float sinHalfAngle = (float) Math.sin(angle / 2);
		float cosHalfAngle = (float) Math.cos(angle / 2);

		this.x = axis.getX() * sinHalfAngle;
		this.y = axis.getY() * sinHalfAngle;
		this.z = axis.getZ() * sinHalfAngle;
		this.w = cosHalfAngle;
	}

	public Quaternion(Matrix4f rotation)
	{
		float trace = rotation.get(0, 0) + rotation.get(1, 1) + rotation.get(2, 2);

		if (trace > 0)
		{
			float s = 0.5f / (float) Math.sqrt(trace + 1.0f);
			w = 0.25f / s;
			x = (rotation.get(1, 2) - rotation.get(2, 1)) * s;
			y = (rotation.get(2, 0) - rotation.get(0, 2)) * s;
			z = (rotation.get(0, 1) - rotation.get(1, 0)) * s;
		}
		else
		{
			if (rotation.get(0, 0) > rotation.get(1, 1) && rotation.get(0, 0) > rotation.get(2, 2))
			{
				float s = 2.0f * (float) Math.sqrt(1.0f + rotation.get(0, 0) - rotation.get(1, 1) - rotation.get(2, 2));
				w = (rotation.get(1, 2) - rotation.get(2, 1)) / s;
				x = 0.25f * s;
				y = (rotation.get(1, 0) + rotation.get(0, 1)) / s;
				z = (rotation.get(2, 0) + rotation.get(0, 2)) / s;
			}
			else if (rotation.get(1, 1) > rotation.get(2, 2))
			{
				float s = 2.0f * (float) Math.sqrt(1.0f + rotation.get(1, 1) - rotation.get(0, 0) - rotation.get(2, 2));
				w = (rotation.get(2, 0) - rotation.get(0, 2)) / s;
				x = (rotation.get(1, 0) + rotation.get(0, 1)) / s;
				y = 0.25f * s;
				z = (rotation.get(2, 1) + rotation.get(1, 2)) / s;
			}
			else
			{
				float s = 2.0f * (float) Math.sqrt(1.0f + rotation.get(2, 2) - rotation.get(0, 0) - rotation.get(1, 1));
				w = (rotation.get(0, 1) - rotation.get(1, 0)) / s;
				x = (rotation.get(2, 0) + rotation.get(0, 2)) / s;
				y = (rotation.get(1, 2) + rotation.get(2, 1)) / s;
				z = 0.25f * s;
			}
		}

		float length = (float) Math.sqrt(x * x + y * y + z * z + w * w);
		x /= length;
		y /= length;
		z /= length;
		w /= length;
	}

	@NotNull
	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public Quaternion normalized()
	{
		float length = length();

		x /= length;
		y /= length;
		z /= length;
		w /= length;

		return new Quaternion(x / length, y / length, z / length, w / length);
	}

	@SuppressWarnings("WeakerAccess")
	public float length()
	{
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	@NotNull
	public Quaternion conjugated()
	{
		return new Quaternion(-x, -y, -z, w);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Quaternion set(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;

		return this;
	}

	@NotNull
	public Quaternion set(@NotNull Quaternion r)
	{
		this.x = r.getX();
		this.y = r.getY();
		this.z = r.getZ();
		this.w = r.getW();

		return this;
	}

	public float getX()
	{
		return x;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setX(float x)
	{
		this.x = x;
	}

	public float getY()
	{
		return y;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setY(float y)
	{
		this.y = y;
	}

	public float getZ()
	{
		return z;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setZ(float z)
	{
		this.z = z;
	}

	@SuppressWarnings("WeakerAccess")
	public float getW()
	{
		return w;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setW(float w)
	{
		this.w = w;
	}

	@NotNull
	public Quaternion mul(@NotNull Quaternion r)
	{
		float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(x_, y_, z_, w_);
	}

	@NotNull
	public Quaternion mul(@NotNull Vector3f r)
	{
		float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
		float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
		float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
		float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

		return new Quaternion(x_, y_, z_, w_);
	}

	@NotNull
	public Quaternion sub(@NotNull Quaternion r)
	{
		return new Quaternion(x - r.getX(), y - r.getY(), z - r.getZ(), w - r.getW());
	}

	@NotNull
	public Quaternion add(@NotNull Quaternion r)
	{
		return new Quaternion(x + r.getX(), y + r.getY(), z + r.getZ(), w + r.getW());
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public float dot(@NotNull Quaternion r)
	{
		return x * r.getX() + y * r.getY() + z * r.getZ() + w * r.getW();
	}

	public Quaternion mul(float r)
	{
		return new Quaternion(x * r, y * r, z * r, w * r);
	}

	public Quaternion nlerp(Quaternion destination, float lerpFactor, boolean shortest)
	{
		Quaternion correctedDestination = destination;

		if (shortest && this.dot(destination) < 0)
		{
			correctedDestination = new Quaternion(-destination.getX(), -destination.getY(), -destination.getZ(), -destination.getW());
		}

		return correctedDestination.sub(this).mul(lerpFactor).add(this).normalized();
	}

	public Quaternion slerp(Quaternion dest, float lerpFactor, boolean shortest)
	{
		final float EPSILON = 1e3f;

		float cos = this.dot(dest);
		Quaternion correctedDest = dest;

		if (shortest && cos < 0)
		{
			cos = -cos;
			correctedDest = new Quaternion(-dest.getX(), -dest.getY(), -dest.getZ(), -dest.getW());
		}

		if (Math.abs(cos) >= 1 - EPSILON)
		{
			return nlerp(correctedDest, lerpFactor, false);
		}

		float sin = (float) Math.sqrt(1.0f - cos * cos);
		float angle = (float) Math.atan2(sin, cos);
		float invSin = 1.0f / sin;

		float srcFactor = (float) Math.sin((1.0f - lerpFactor) * angle) * invSin;
		float destFactor = (float) Math.sin((lerpFactor) * angle) * invSin;

		return this.mul(srcFactor).add(correctedDest.mul(destFactor));
	}

	@NotNull
	public Matrix4f toRotationMatrix()
	{
		Vector3f forward = new Vector3f(2.0f * (x * z - w * y), 2.0f * (y * z + w * x), 1.0f - 2.0f * (x * x + y * y));
		Vector3f up = new Vector3f(2.0f * (x * y + w * z), 1.0f - 2.0f * (x * x + z * z), 2.0f * (y * z - w * x));
		Vector3f right = new Vector3f(1.0f - 2.0f * (y * y + z * z), 2.0f * (x * y - w * z), 2.0f * (x * z + w * y));

		return new Matrix4f().initRotation(forward, up, right);
	}

	@NotNull
	public Vector3f getForward()
	{
		return new Vector3f(0, 0, 1).rotate(this);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector3f getBack()
	{
		return new Vector3f(0, 0, -1).rotate(this);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector3f getUp()
	{
		return new Vector3f(0, 1, 0).rotate(this);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector3f getDown()
	{
		return new Vector3f(0, -1, 0).rotate(this);
	}

	@NotNull
	public Vector3f getRight()
	{
		return new Vector3f(1, 0, 0).rotate(this);
	}

	@NotNull
	public Vector3f getLeft()
	{
		return new Vector3f(-1, 0, 0).rotate(this);
	}

	public boolean equals(@Nullable Quaternion compared)
	{
		return this == compared || compared != null && Float.compare(compared.w, w) == 0 && Float.compare(compared.x, x) == 0 && Float.compare(compared.y, y) == 0 && Float.compare(compared.z, z) == 0;
	}

	@Override
	public int hashCode()
	{
		int result = (x != +0.0f ? Float.floatToIntBits(x) : 0);
		result = 31 * result + (y != +0.0f ? Float.floatToIntBits(y) : 0);
		result = 31 * result + (z != +0.0f ? Float.floatToIntBits(z) : 0);
		result = 31 * result + (w != +0.0f ? Float.floatToIntBits(w) : 0);
		return result;
	}
}
