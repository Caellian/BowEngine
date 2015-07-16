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

package com.github.caellian.bowEngine.core.components;

import com.github.caellian.bowEngine.core.math.Vector2f;
import com.github.caellian.bowEngine.core.math.Vector3f;
import com.github.caellian.bowEngine.core.util.input.InputHelper;
import com.github.caellian.bowEngine.render.display.Window;

/**
 * @author Caellian
 */
public class FreeLook extends GameComponent
{
	private static final Vector3f Y_AXIS      = new Vector3f(0, 1, 0);
	private              boolean  mouseLocked = false;

	private float sensitivity;
	private int   escapeKey;

	public FreeLook(float sensitivity, int escapeKey)
	{
		this.sensitivity = sensitivity;
		this.escapeKey = escapeKey;
	}

	@Override
	public void input(float delta)
	{
		if (InputHelper.getKey(escapeKey))
		{
			InputHelper.setCursor(true);
			mouseLocked = false;
		}
		if (InputHelper.getMouseDown(0))
		{
			InputHelper.setMousePosition(Window.centerPosition());
			InputHelper.setCursor(false);
			mouseLocked = true;
		}

		if (mouseLocked)
		{
			Vector2f deltaPos = InputHelper.getMousePosition().sub(Window.centerPosition());

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if (rotY)
			{
				getTransform().rotate(Y_AXIS, (float) Math.toRadians(deltaPos.getX() * sensitivity));
			}
			if (rotX)
			{
				getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(-deltaPos.getY() * sensitivity));
			}
			if (rotY || rotX)
			{
				InputHelper.setMousePosition(Window.centerPosition());
			}
		}
	}
}
