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

package com.caellian.bowEngine.physics;

import com.caellian.bowEngine.core.components.GameObject;

import java.util.ArrayList;

/**
 * @author Caellian
 */
public class PhysicsEngine
{
	/**
	 * This is the gravitational constant for physics in our universe.
	 * Changing this can cause interesting behaviour that players aren't used to.
	 */
	protected double gravitationalConstant     = 6.674 * Math.pow(10, -11);

	/**
	 * Gravitational acceleration conventional standard value is exactly 9.80665 m/s^2,
	 * but 9.8 is used here to increase performance.
	 * It is not recommended to use numbers with high amount of decimals because gravity gets calculated every game tick.
	 */
	protected float gravitationalAcceleration = 9.8f;

	/**
	 * List of game objects affected by gravity.
	 */
	ArrayList<GameObject> gravityAffected = new ArrayList<>(0);

	public void update(GameObject rootObject) {
		rootObject.forEach(object->{
			GameObject gameObject = (GameObject) object;
			gameObject.getTransform().physicsUpdate();
		});
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
