package com.skythees.bowEngine.render;

import com.skythees.bowEngine.core.components.GameObject;
import com.skythees.bowEngine.core.math.Vector3f;
import com.skythees.bowEngine.render.components.Camera;
import com.skythees.bowEngine.render.components.light.BaseLight;
import com.skythees.bowEngine.shaders.ForwardAmbient;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

/**
 * Created on 15.3.2015. at 2:47.
 */
public class RenderingEngine
{
	private final ArrayList<BaseLight> lights;
	private       Camera               mainCamera;
	@NotNull
	private Vector3f ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
	private BaseLight activeLight;

	public RenderingEngine()
	{
		lights = new ArrayList<>();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_DEPTH_CLAMP);

		glEnable(GL_TEXTURE_2D);
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public static void setTextures(boolean enabled)
	{
		if (enabled)
		{
			glEnable(GL_TEXTURE_2D);
		}
		else
		{
			glDisable(GL_TEXTURE_2D);
		}
	}

	@SuppressWarnings("unused")
	private static void unbindTextures()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	@SuppressWarnings("unused")
	private static void setClearColor(@NotNull Vector3f color)
	{
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
	}

	@SuppressWarnings("unused")
	public static String openGLVer()
	{
		return glGetString(GL_VERSION);
	}

	@NotNull
	public Vector3f getAmbientLight()
	{
		return ambientLight;
	}

	@SuppressWarnings("unused")
	public void setAmbientLight(@NotNull Vector3f ambientLight)
	{
		this.ambientLight = ambientLight;
	}

	public void addLight(BaseLight light)
	{
		lights.add(light);
	}

	public void render(@NotNull GameObject object)
	{
		clearScreen();

		lights.clear();
		object.addToRenderingEngine(this);

		Shader forwardAmbient = ForwardAmbient.getInstance();

		object.render(forwardAmbient, this);
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		for (BaseLight light : lights)
		{
			activeLight = light;
			object.render(light.getShader(), this);
		}

		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	@SuppressWarnings({"UnusedDeclaration", "unused"})
	public static void clearScreen()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public BaseLight getActiveLight()
	{
		return this.activeLight;
	}

	public Camera getMainCamera()
	{
		return mainCamera;
	}

	public void addCamera(Camera camera)
	{
		this.mainCamera = camera;
	}
}
