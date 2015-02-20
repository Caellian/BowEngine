package com.skythees.bowEngine.managers;

import com.skythees.bowEngine.math.vector.Matrix4f;
import com.skythees.bowEngine.render.Vertex;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Objects;

public class DataUtil {
    public static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size);
    }

    public static IntBuffer createIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size);
    }

    public static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer buffer = createIntBuffer(values.length);
        buffer.put(values);
        buffer.flip();
        return buffer;
    }

    public static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

        for (Vertex vertex : vertices) {
            buffer.put(vertex.getPos().getX());
            buffer.put(vertex.getPos().getY());
            buffer.put(vertex.getPos().getZ());
            buffer.put(vertex.getTexturePos().getX());
            buffer.put(vertex.getTexturePos().getY());

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

    public static String[] removeEmptyStrings(String[] tokens) {
        ArrayList<String> tokenList = new ArrayList<>();

        for (final String token : tokens) {
            if (!Objects.equals(token, "")) {
                tokenList.add(token);
            }
        }
        return tokenList.toArray(new String[tokenList.size()]);
    }

    public static int[] toIntArray(Integer[] indexData) {
        int[] result = new int[indexData.length];
        int counter = 0;
        for (final Integer integer : indexData) {
            result[counter++] = integer;
        }
        return result;
    }
}
