package com.skythees.bowEngine.render.resources;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glCreateProgram;

/**
 * Created by caellian on 13.06.15., at 13:20.
 */
public class ShaderResource
{
	private final int program;
	private       int referenceCounter;
	private HashMap<String, Integer> uniforms     = new HashMap<>();
	private ArrayList<String>        uniformNames = new ArrayList<>();
	private ArrayList<String>        uniformTypes = new ArrayList<>();

	public ShaderResource()
	{
		this.program = glCreateProgram();
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

	public int getProgram()
	{
		return program;
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		glDeleteBuffers(program);
	}

	public HashMap<String, Integer> getUniforms()
	{
		return uniforms;
	}

	public ArrayList<String> getUniformNames()
	{
		return uniformNames;
	}

	public ArrayList<String> getUniformTypes()
	{
		return uniformTypes;
	}
}
