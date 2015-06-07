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

package com.skythees.bowEngine.render.components;

import com.skythees.bowEngine.core.components.GameComponent;
import com.skythees.bowEngine.core.math.Matrix4f;
import com.skythees.bowEngine.core.math.Vector2f;
import com.skythees.bowEngine.core.math.Vector3f;
import com.skythees.bowEngine.core.util.input.InputHelper;
import com.skythees.bowEngine.render.RenderingEngine;
import com.skythees.bowEngine.render.display.Window;
import com.sun.istack.internal.NotNull;
import org.lwjgl.input.Keyboard;

public class Camera extends GameComponent
{
	private static final Vector3f yAxis = new Vector3f(0, 1, 0);
	private final Matrix4f projection;
	private boolean mouseLocked = false;

	@SuppressWarnings("unused")
	public Camera(float fov, float aspectRatio, float zNear, float zFar)
	{
		this.projection = new Matrix4f().initPerspective(fov, aspectRatio, zNear, zFar);
	}

	@NotNull
	public Matrix4f getViewProjection()
	{
		Matrix4f cameraRotation = getTransform().getTransformedRotation().conjugated().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPosition().mul(-1);
		Matrix4f cameraTranslation = new Matrix4f().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}

	@Override
	public void input(float delta)
	{
		float sensitivity = 0.5f;
		float movAmt = 10 * delta;

		if (InputHelper.getKey(Keyboard.KEY_ESCAPE))
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

		if (InputHelper.getKey(Keyboard.KEY_W))
		{
			move(getTransform().getRot().getForward(), movAmt);
		}
		if (InputHelper.getKey(Keyboard.KEY_S))
		{
			move(getTransform().getRot().getForward(), -movAmt);
		}
		if (InputHelper.getKey(Keyboard.KEY_A))
		{
			move(getTransform().getRot().getLeft(), movAmt);
		}
		if (InputHelper.getKey(Keyboard.KEY_D))
		{
			move(getTransform().getRot().getRight(), movAmt);
		}

		if (mouseLocked)
		{
			Vector2f deltaPos = InputHelper.getMousePosition().sub(Window.centerPosition());

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if (rotY)
			{
				getTransform().rotate(yAxis, (float) Math.toRadians(deltaPos.getX() * sensitivity));
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

	@Override
	public void addToRenderingEngine(@NotNull RenderingEngine renderingEngine)
	{
		renderingEngine.addCamera(this);
	}

	@SuppressWarnings("WeakerAccess")
	public void move(@NotNull Vector3f dir, float amount)
	{
		getTransform().setPos(getTransform().getPos().add(dir.mul(amount)));
	}
}
