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

package com.github.caellian.bowEngine.render.components.light;

import com.github.caellian.bowEngine.core.math.Vector3f;

/**
 * @author Caellian
 */
public class Attenuation extends Vector3f
{
	public Attenuation(float constant, float linear, float exponent)
	{
		super(constant, linear, exponent);
	}

	public float getConstant()
	{
		return super.getX();
	}

	public float getLinear()
	{
		return super.getY();
	}

	public float getExponent()
	{
		return super.getZ();
	}
}
