package com.skythees.bowEngine.render.resources;

import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;

/**
 * Created by Caellian on 05.06.15., at 15:59, at 16:01.
 */
public class TextureResource
{
	private final int id;
	private       int referenceCounter;

	public TextureResource()
	{
		this.id = glGenTextures();
		referenceCounter = 1;
	}

	public void increaseReference()
	{
		referenceCounter++;
	}

	public boolean decreaseReference()
	{
		referenceCounter--;
		return referenceCounter == 0;
	}

	public int getId()
	{
		return id;
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		glDeleteBuffers(id);
	}
}
