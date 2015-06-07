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

package com.skythees.bowEngine.render.components.light;

import com.skythees.bowEngine.core.math.Vector3f;
import com.skythees.bowEngine.shaders.ForwardPoint;
import com.sun.istack.internal.NotNull;

/**
 * Created on 02.03.15.
 */
public class PointLight extends BaseLight
{
	private static final int COLOR_DEPTH = 256;

	private Vector3f attenuation;
	private float    range;

	public PointLight(Vector3f color, float intensity, @NotNull Vector3f attenuation)
	{
		super(color, intensity);
		this.attenuation = attenuation;

		float a = attenuation.getZ();
		float b = attenuation.getY();
		float c = attenuation.getX() - COLOR_DEPTH * getIntensity() * getColor().max();
		this.range = (float) (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);

		setShader(ForwardPoint.getInstance());
	}

	public float getRange()
	{
		return range;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setRange(float range)
	{
		this.range = range;
	}

	public float getConstant()
	{
		return this.attenuation.getX();
	}

	@SuppressWarnings("unused")
	public void setConstant(float constant)
	{
		this.attenuation.setX(constant);
	}

	public float getLinear()
	{
		return this.attenuation.getY();
	}

	@SuppressWarnings("unused")
	public void setLinear(float linear)
	{
		this.attenuation.setY(linear);
	}

	public float getExponent()
	{
		return this.attenuation.getZ();
	}

	@SuppressWarnings("unused")
	public void setExponent(float exponent)
	{
		this.attenuation.setZ(exponent);
	}
}
