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

package com.caellian.bowEngine.render;

import com.caellian.bowEngine.core.Transform;
import com.caellian.bowEngine.core.components.GameObject;
import com.caellian.bowEngine.core.lib.Reference;
import com.caellian.bowEngine.core.math.Vector3f;
import com.caellian.bowEngine.core.util.helpers.IUniformHandler;
import com.caellian.bowEngine.render.components.Camera;
import com.caellian.bowEngine.render.components.light.BaseLight;
import com.caellian.bowEngine.render.display.Window;
import com.caellian.bowEngine.render.resources.MapStorage;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

/**
 * Created on 15.3.2015. at 2:47.
 */
public class RenderingEngine extends MapStorage
{
	private final ArrayList<BaseLight>       lights         = new ArrayList<>(0);
	private       ArrayList<IScreenDrawable> screenObject2D = new ArrayList<>(0);
	private Vector3f  clearColor;
	private BaseLight activeLight;

	private Shader forwardAmbient;
	private Camera currentCamera;

	private ArrayList<IUniformHandler> IUniformHandlers;

	public RenderingEngine(IUniformHandler... IUniformHandler)
	{
		this.IUniformHandlers = new ArrayList<>(0);
		this.IUniformHandlers.addAll(Arrays.asList(IUniformHandler));

		integerHashMap.put("diffuse", Reference.SamplerData.DIFFUSE);
		vector3fHashMap.put("ambient", new Vector3f(0.1f, 0.1f, 0.1f));

		forwardAmbient = new Shader("./resources/shaders/forward-ambient");

		clearColor = new Vector3f(0, 0, 0);
		glClearColor(clearColor.getX(), clearColor.getY(), clearColor.getZ(), 0.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
	}

	public static String openGLVersion()
	{
		return glGetString(GL_VERSION);
	}

	protected void initEngine()
	{

	}

	public void addLight(BaseLight light)
	{
		lights.add(light);
	}

	public void render(@NotNull GameObject object)
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		object.renderAll(forwardAmbient, this);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		for (BaseLight light : lights)
		{
			activeLight = light;
			object.renderAll(light.getShader(), this);
		}

		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	public void renderGUI()
	{
		//Switching to GUI rendering mode
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		gluOrtho2D(0.0f, Window.getWidth(), Window.getHeight(), 0.0f);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		glTranslatef(0.375f, 0.375f, 0.0f);

		glDisable(GL_DEPTH_TEST);

		//Drawing all stored objects
		screenObject2D.forEach(IScreenDrawable::draw);

		//Switching back to world display mode
		glViewport(0, 0, Window.getWidth(), Window.getHeight());
		glMatrixMode(GL_PROJECTION);

		glLoadIdentity();
		gluPerspective(45, (float) Window.getWidth() / Window.getHeight(), 0.1f, 5000.0f);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);
	}

	public BaseLight getActiveLight()
	{
		return this.activeLight;
	}

	public Camera getCurrentCamera()
	{
		return currentCamera;
	}

	public void setCurrentCamera(Camera camera)
	{
		this.currentCamera = camera;
	}

	public Vector3f getClearColor()
	{
		return this.clearColor;
	}

	public void setClearColor(Vector3f color)
	{
		clearColor = color;
		glClearColor(clearColor.getX(), clearColor.getY(), clearColor.getZ(), 0.0f);
	}

	public int getSamplerSlot(String samplerName)
	{
		return integerHashMap.get(samplerName);
	}

	public void updateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType)
	{
		for (IUniformHandler IUniformHandler : IUniformHandlers)
		{
			if (IUniformHandler.updateUniformStruct(transform, material, shader, uniformName, uniformType))
			{
				return;
			}
		}
		throw new IllegalArgumentException(uniformType + " is not a supported type in Rendering Engine or game!");
	}

	public void updateUndefinedUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType)
	{
		for (IUniformHandler IUniformHandler : IUniformHandlers)
		{
			if (IUniformHandler.updateUndefinedUniformStruct(transform, material, shader, uniformName, uniformType))
			{
				return;
			}
		}
		throw new IllegalArgumentException(uniformName + " is not supported by Bow Engine or game!");
	}

	public void set2DScreenObjects(ArrayList<IScreenDrawable> screenDrawables)
	{
		this.screenObject2D = screenDrawables;
	}

	public void add2DScreenObject(IScreenDrawable screenDrawable)
	{
		this.screenObject2D.add(screenDrawable);
	}

	public void remove2DScreenObject(IScreenDrawable screenDrawable)
	{
		this.screenObject2D.remove(screenDrawable);
	}
}
