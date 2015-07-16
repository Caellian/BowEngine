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

package com.github.caellian.bowEngine.core.util.helpers;

import com.github.caellian.bowEngine.core.math.Matrix4f;
import com.github.caellian.bowEngine.render.Vertex;
import com.sun.istack.internal.NotNull;

import java.nio.*;
import java.util.ArrayList;
import java.util.Objects;

public class DataUtil
{

	/**
	 * Construct a direct native-order <code>ShortBuffer<code/> from the given array of elements.
	 *
	 * @param values
	 * 		Values used to create a flipped buffer.
	 *
	 * @return a <code>ShortBuffer<code/>
	 */
	@SuppressWarnings("unused")
	@NotNull
	public static ShortBuffer createFlippedBuffer(@NotNull short... values)
	{
		ShortBuffer buffer = createShortBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	/**
	 * Construct a direct native-order <code>ShortBuffer<code/> with the specified number
	 * of elements.
	 *
	 * @param size
	 * 		The size, in shorts
	 *
	 * @return a <code>ShortBuffer<code/>
	 */
	@SuppressWarnings("WeakerAccess")
	public static ShortBuffer createShortBuffer(int size)
	{
		return createByteBuffer(size << 1).asShortBuffer();
	}

	/**
	 * Construct a direct native-ordered <code>ByteBuffer<code/> with the specified size.
	 *
	 * @param size
	 * 		The size, in bytes
	 *
	 * @return a <code>ByteBuffer<code/>
	 */
	public static ByteBuffer createByteBuffer(int size)
	{
		return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
	}

	/**
	 * Construct a direct native-order <code>ByteBuffer<code/> from the given array of elements.
	 *
	 * @param values
	 * 		Values used to create a flipped buffer.
	 *
	 * @return a <code>ByteBuffer<code/>
	 */
	@SuppressWarnings("unused")
	@NotNull
	public static ByteBuffer createFlippedBuffer(@NotNull byte... values)
	{
		ByteBuffer buffer = createByteBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	/**
	 * Construct a direct native-order <code>LongBuffer<code/> from the given array of elements.
	 *
	 * @param values
	 * 		Values used to create a flipped buffer.
	 *
	 * @return a <code>LongBuffer<code/>
	 */
	@SuppressWarnings("unused")
	@NotNull
	public static LongBuffer createFlippedBuffer(@NotNull long... values)
	{
		LongBuffer buffer = createLongBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	/**
	 * Construct a direct native-order <code>LongBuffer<code/> with the specified number
	 * of elements.
	 *
	 * @param size
	 * 		The size, in longs
	 *
	 * @return an <code>LongBuffer<code/>
	 */
	@SuppressWarnings("WeakerAccess")
	public static LongBuffer createLongBuffer(int size)
	{
		return createByteBuffer(size << 3).asLongBuffer();
	}

	/**
	 * Construct a direct native-order <code>DoubleBuffer<code/> from the given array of elements.
	 *
	 * @param values
	 * 		Values used to create a flipped buffer.
	 *
	 * @return a <code>DoubleBuffer<code/>
	 */
	@SuppressWarnings("unused")
	@NotNull
	public static DoubleBuffer createFlippedBuffer(@NotNull double... values)
	{
		DoubleBuffer buffer = createDoubleBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	/**
	 * Construct a direct native-order <code>DoubleBuffer<code/> with the specified number
	 * of elements.
	 *
	 * @param size
	 * 		The size, in floats
	 *
	 * @return a <code>DoubleBuffer<code/>
	 */
	@SuppressWarnings("WeakerAccess")
	public static DoubleBuffer createDoubleBuffer(int size)
	{
		return createByteBuffer(size << 3).asDoubleBuffer();
	}

	/**
	 * Construct a direct native-order <code>IntBuffer<code/> from the given array of elements.
	 *
	 * @param values
	 * 		Values used to create a flipped buffer.
	 *
	 * @return a <code>IntBuffer<code/>
	 */
	@NotNull
	public static IntBuffer createFlippedBuffer(@NotNull int... values)
	{
		IntBuffer buffer = createIntBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	/**
	 * Construct a direct native-order <code>IntBuffer<code/> with the specified number
	 * of elements.
	 *
	 * @param size
	 * 		The size, in ints
	 *
	 * @return an <code>IntBuffer<code/>
	 */
	@SuppressWarnings("WeakerAccess")
	public static IntBuffer createIntBuffer(int size)
	{
		return createByteBuffer(size << 2).asIntBuffer();
	}

	/**
	 * Construct a direct native-order <code>CharBuffer<code/> from the given array of elements.
	 *
	 * @param values
	 * 		Values used to create a flipped buffer.
	 *
	 * @return a <code>CharBuffer<code/>
	 */
	@NotNull
	public static CharBuffer createFlippedBuffer(@NotNull char... values)
	{
		CharBuffer buffer = createCharBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	/**
	 * Construct a direct native-order <code>CharBuffer<code/> with the specified number
	 * of elements.
	 *
	 * @param size
	 * 		The size, in chars
	 *
	 * @return an <code>CharBuffer<code/>
	 */
	@SuppressWarnings("WeakerAccess")
	public static CharBuffer createCharBuffer(int size)
	{
		return createByteBuffer(size << 1).asCharBuffer();
	}

	/**
	 * Construct a direct native-order <code>FloatBuffer<code/> from the given array of vertices.
	 *
	 * @param vertices
	 * 		Vertices used to create a flipped buffer.
	 *
	 * @return a <code>FloatBuffer<code/>
	 */
	@NotNull
	public static FloatBuffer createFlippedBuffer(@NotNull Vertex[] vertices)
	{
		FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE);

		for (Vertex vertex : vertices)
		{
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

	/**
	 * Construct a direct native-order <code>FloatBuffer<code/> with the specified number
	 * of elements.
	 *
	 * @param size
	 * 		The size, in floats
	 *
	 * @return a <code>FloatBuffer<code/>
	 */
	@SuppressWarnings("WeakerAccess")
	public static FloatBuffer createFloatBuffer(int size)
	{
		return createByteBuffer(size << 2).asFloatBuffer();
	}

	/**
	 * Construct a direct native-order <code>FloatBuffer<code/> from the given array of elements.
	 *
	 * @param values
	 * 		Values used to create a flipped buffer.
	 *
	 * @return a <code>FloatBuffer<code/>
	 */
	@SuppressWarnings("unused")
	@NotNull
	public static FloatBuffer createFlippedBuffer(@NotNull float... values)
	{
		FloatBuffer buffer = createFloatBuffer(values.length);
		buffer.put(values);
		buffer.flip();
		return buffer;
	}

	/**
	 * Construct a direct native-order <code>FloatBuffer<code/> from the given <code>Matrix4f<code/>.
	 *
	 * @param matrix
	 * 		Matrix used to create a flipped buffer.
	 *
	 * @return a <code>FloatBuffer<code/>
	 */
	@NotNull
	public static FloatBuffer createFlippedBuffer(@NotNull Matrix4f matrix)
	{
		FloatBuffer buffer = createFloatBuffer(4 * 4);

		for (int i : new int[]{0, 1, 2, 3})
		{
			for (int j : new int[]{0, 1, 2, 3})
			{
				buffer.put(matrix.get(i, j));
			}
		}

		buffer.flip();

		return buffer;
	}

	/**
	 * Filters out empty strings from given string array.
	 *
	 * @param tokens
	 * 		Array of strings of which some are empty.
	 *
	 * @return string array
	 */
	public static String[] removeEmptyStrings(@NotNull String[] tokens)
	{
		ArrayList<String> tokenList = new ArrayList<>();

		for (final String token : tokens)
		{
			if (!Objects.equals(token, ""))
			{
				tokenList.add(token);
			}
		}
		return tokenList.toArray(new String[tokenList.size()]);
	}

	/**
	 * Returns primitive versions of given <code>Integer<code/>s
	 *
	 * @param integerObjects
	 * 		Integers to be turned into ints.
	 *
	 * @return int array
	 */
	@NotNull
	public static int[] toIntPrimitive(@NotNull Integer[] integerObjects)
	{
		int[] result = new int[integerObjects.length];
		int counter = 0;
		for (final Integer integer : integerObjects)
		{
			result[counter++] = integer;
		}
		return result;
	}
}
