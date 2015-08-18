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

package com.caellian.bowEngine.core.math;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class Vector2f
{
	private float x;
	private float y;

	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	@SuppressWarnings("unused")
	public float max()
	{
		return Math.max(x, y);
	}

	@SuppressWarnings("unused")
	public float dot(@NotNull Vector2f r)
	{
		return x * r.getX() + y * r.getY();
	}

	public float getY()
	{
		return y;
	}

	@SuppressWarnings("unused")
	public void setY(float y) {
		this.y = y;
	}

	public float getX()
	{
		return x;
	}

	@SuppressWarnings("unused")
	public void setX(float x)
	{
		this.x = x;
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f normalized()
	{
		float length = length();

		return new Vector2f(x / length, y / length);
	}

	@SuppressWarnings("WeakerAccess")
	public float length()
	{
		return (float) Math.sqrt(x * x + y * y);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f rotate(float angle)
	{
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);

		return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
	}

	@SuppressWarnings("unused")
	public float cross(@NotNull Vector2f r)
	{
		return x * r.getY() - y * r.getX();
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f lerp(@NotNull Vector2f dest, @NotNull Vector2f lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	@SuppressWarnings("WeakerAccess")
	@NotNull
	public Vector2f add(@NotNull Vector2f r)
	{
		return new Vector2f(x + r.getX(), y + r.getY());
	}

	@SuppressWarnings("WeakerAccess")
	@NotNull
	public Vector2f mul(@NotNull Vector2f r)
	{
		return new Vector2f(x * r.getX(), y * r.getY());
	}

	@NotNull
	public Vector2f sub(@NotNull Vector2f r)
	{
		return new Vector2f(x - r.getX(), y - r.getY());
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f set(float x, float y)
	{
		this.x = x;
		this.y = y;

		return this;
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f set(@NotNull Vector2f r)
	{
		this.x = r.getX();
		this.y = r.getY();

		return this;
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f add(float r)
	{
		return new Vector2f(x + r, y + r);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f sub(float r)
	{
		return new Vector2f(x - r, y - r);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f mul(float r)
	{
		return new Vector2f(x * r, y * r);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f div(@NotNull Vector2f r)
	{
		return new Vector2f(x / r.getX(), y / r.getY());
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f div(float r)
	{
		return new Vector2f(x / r, y / r);
	}

	@SuppressWarnings("unused")
	@NotNull
	public Vector2f abs()
	{
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	@SuppressWarnings("unused")
	public boolean equals(@Nullable Vector2f compared)
	{
		return this == compared || compared != null && Float.compare(compared.x, x) == 0 && Float.compare(compared.y, y) == 0;
	}

	@NotNull
	public String toString()
	{
		return "(" + x + " " + y + ")";
	}
}
