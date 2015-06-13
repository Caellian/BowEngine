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
import com.skythees.bowEngine.render.Shader;
import com.sun.istack.internal.NotNull;

/**
 * Created on 03.03.15.
 */
public class SpotLight extends PointLight
{
	private float cutoff;

	@SuppressWarnings("unused")
	public SpotLight(Vector3f color, float intensity, Vector3f attenuation, float cutoff)
	{
		super(color, intensity, attenuation); //TODO: Calculate
		this.cutoff = cutoff;

		setShader(new Shader("./resources/shaders/forward-spot"));
	}

	@NotNull
	public Vector3f getDirection()
	{
		return getTransform().getTransformedRotation().getForward();
	}

	public float getCutoff()
	{
		return cutoff;
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public void setCutoff(float cutoff)
	{
		this.cutoff = cutoff;
	}
}
