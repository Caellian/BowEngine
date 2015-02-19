package com.skythees.bowEngine.managers;

import com.skythees.bowEngine.math.vector.Matrix4f;
import com.skythees.bowEngine.render.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

public class DataUtil {
    public static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices) {
            buffer.put(vertex.getPos().getX());
            buffer.put(vertex.getPos().getY());
            buffer.put(vertex.getPos().getZ());
        }

        buffer.flip();

        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Matrix4f matrix) {
        FloatBuffer buffer = createFloatBuffer(4 * 4);

        for (int i : new int[]{0, 1, 2, 3}) {
            for (int j : new int[]{0, 1, 2, 3}) {
                buffer.put(matrix.get(i, j));
            }
        }

        buffer.flip();

        return buffer;
    }
}
