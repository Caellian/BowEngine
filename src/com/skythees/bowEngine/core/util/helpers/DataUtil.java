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

package com.skythees.bowEngine.core.util.helpers;

import com.skythees.bowEngine.core.Vertex;
import com.skythees.bowEngine.core.math.Matrix4f;

import java.nio.*;
import java.util.ArrayList;
import java.util.Objects;

public class DataUtil {
//    Copied over some BufferUtils methods to make the engine run faster.

    /**
     * Construct a direct native-ordered bytebuffer with the specified size.
     *
     * @param size The size, in bytes
     * @return a ByteBuffer
     */
    public static ByteBuffer createByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

    /**
     * Construct a direct native-order shortbuffer with the specified number
     * of elements.
     *
     * @param size The size, in shorts
     * @return a ShortBuffer
     */
    public static ShortBuffer createShortBuffer(int size) {
        return createByteBuffer(size << 1).asShortBuffer();
    }

    /**
     * Construct a direct native-order charbuffer with the specified number
     * of elements.
     *
     * @param size The size, in chars
     * @return an CharBuffer
     */
    public static CharBuffer createCharBuffer(int size) {
        return createByteBuffer(size << 1).asCharBuffer();
    }

    /**
     * Construct a direct native-order intbuffer with the specified number
     * of elements.
     *
     * @param size The size, in ints
     * @return an IntBuffer
     */
    public static IntBuffer createIntBuffer(int size) {
        return createByteBuffer(size << 2).asIntBuffer();
    }

    /**
     * Construct a direct native-order longbuffer with the specified number
     * of elements.
     *
     * @param size The size, in longs
     * @return an LongBuffer
     */
    public static LongBuffer createLongBuffer(int size) {
        return createByteBuffer(size << 3).asLongBuffer();
    }

    /**
     * Construct a direct native-order floatbuffer with the specified number
     * of elements.
     *
     * @param size The size, in floats
     * @return a FloatBuffer
     */
    public static FloatBuffer createFloatBuffer(int size) {
        return createByteBuffer(size << 2).asFloatBuffer();
    }

    /**
     * Construct a direct native-order doublebuffer with the specified number
     * of elements.
     *
     * @param size The size, in floats
     * @return a FloatBuffer
     */
    public static DoubleBuffer createDoubleBuffer(int size) {
        return createByteBuffer(size << 3).asDoubleBuffer();
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
            buffer.put(vertex.getNormal().getX());
            buffer.put(vertex.getNormal().getY());
            buffer.put(vertex.getNormal().getZ());
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
