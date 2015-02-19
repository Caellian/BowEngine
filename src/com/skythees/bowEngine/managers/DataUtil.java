package com.skythees.bowEngine.managers;

import com.skythees.bowEngine.render.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class DataUtil
{
	public static FloatBuffer createFloatBuffer(int size)
	{
		return BufferUtils.createFloatBuffer(size);
	}
	
	public static FloatBuffer createFlippedBuffer(Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices) {
            buffer.put(vertex.getPos().getX());
            buffer.put(vertex.getPos().getY());
            buffer.put(vertex.getPos().getZ());
        }
		
		buffer.flip();
		
		return buffer;
	}
}
