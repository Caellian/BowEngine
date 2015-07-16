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

package com.github.caellian.bowEngine.physics;

import com.github.caellian.bowEngine.core.components.GameObject;
import com.github.caellian.bowEngine.core.math.Vector3f;
import com.github.caellian.bowEngine.physics.attributes.Gravity;

/**
 * @author Caellian
 */
public class PhysicsEngine
{
	protected double gravitationalConstant     = 6.674 * Math.pow(10, -11);
	/**
	 * Gravitational acceleration conventional standard value is exactly 9.80665 m/s^2,
	 * but 9.8 is used here to increase performance.
	 * It is not recommended to use numbers with high amount of decimals because gravity gets calculated every game tick.
	 */
	protected float  gravitationalAcceleration = 9.80665f;

	public void preUpdate(GameObject rootObject)
	{
		rootObject.forEach(object->{
			GameObject gameObject = (GameObject) object;
			gameObject.getTransform().physicsUpdate();
		});
	}

	public void postUpdate(GameObject rootObject)
	{
		rootObject.forEach(object->{
			if (object.getClass().isAnnotationPresent(Gravity.class))
			{
				applyGravity((GameObject) object);
			}
		});
	}

	private void applyGravity(GameObject object)
	{
		Gravity gravity = object.getClass().getAnnotation(Gravity.class);
		float weight = gravity.weight();
		float force = gravity.weight() * gravitationalAcceleration;

		object.getTransform().accelerateMovement(new Vector3f(0, -0.1f, 0));
	}

	public float getGravitationalAcceleration()
	{
		return this.gravitationalAcceleration;
	}

	public void setGravitationalAcceleration(float gravitationalAcceleration)
	{
		this.gravitationalAcceleration = gravitationalAcceleration;
	}
}
