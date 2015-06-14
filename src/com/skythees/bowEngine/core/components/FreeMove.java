/*
 * BowEngine, modular and easy to use game engine
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
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.skythees.bowEngine.core.components;

import com.skythees.bowEngine.core.math.Vector3f;
import com.skythees.bowEngine.core.util.input.InputHelper;
import com.sun.istack.internal.NotNull;

/**
 * Created by caellian on 14.06.15., at 01:59.
 */
public class FreeMove extends GameComponent
{
	private float speed;
	private int   forwardKey;
	private int   backwardKey;
	private int   rightKey;
	private int   leftKey;

	public FreeMove(float speed, int forwardKey, int backwardKey, int rightKey, int leftKey)
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backwardKey = backwardKey;
		this.rightKey = rightKey;
		this.leftKey = leftKey;
	}

	@Override
	public void input(float delta)
	{
		float moveAmount = speed * delta;

		if (InputHelper.getKey(forwardKey))
		{
			move(getTransform().getRot().getForward(), moveAmount);
		}
		if (InputHelper.getKey(backwardKey))
		{
			move(getTransform().getRot().getForward(), -moveAmount);
		}
		if (InputHelper.getKey(leftKey))
		{
			move(getTransform().getRot().getLeft(), moveAmount);
		}
		if (InputHelper.getKey(rightKey))
		{
			move(getTransform().getRot().getRight(), moveAmount);
		}
	}

	@SuppressWarnings("WeakerAccess")
	public void move(@NotNull Vector3f direction, float amount)
	{
		getTransform().setPos(getTransform().getPos().add(direction.mul(amount)));
	}
}
