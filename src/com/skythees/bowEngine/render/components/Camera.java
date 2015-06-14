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

package com.skythees.bowEngine.render.components;

import com.skythees.bowEngine.core.CoreEngine;
import com.skythees.bowEngine.core.components.GameComponent;
import com.skythees.bowEngine.core.math.Matrix4f;
import com.skythees.bowEngine.core.math.Vector3f;
import com.sun.istack.internal.NotNull;

public class Camera extends GameComponent
{
	private final Matrix4f projection;

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
	public void addToEngine(CoreEngine engine)
	{
		engine.getRenderingEngine().addCamera(this);
	}

}
