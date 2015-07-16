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

package com.github.caellian.bowEngine.core.math;

import com.sun.istack.internal.NotNull;

import java.util.Arrays;

/**
 * @author Caellian
 */
@SuppressWarnings("unchecked")
public class Vector<N extends Number>
{
	private static final Number[] EMPTY_ELEMENT_DATA = {};

	transient Number[] elementData;

	/**
	 * The size of the Vector (the number of elements it contains).
	 */
	private int       size;
	private Vector<N> r;

	public Vector(int size)
	{
		if (size <= 0)
		{
			elementData = EMPTY_ELEMENT_DATA;
			this.size = 0;
		}
		else
		{
			elementData = new Number[size];
			this.size = size;
		}
	}

	public Vector(Number[] values)
	{
		if (values.length <= 0)
		{
			elementData = EMPTY_ELEMENT_DATA;
			this.size = 0;
		}
		else
		{
			elementData = values.clone();
			this.size = values.length;
		}
	}

	public N max()
	{
		N result = null;
		for (Number number : elementData)
		{
			if (result != null)
			{
				result = result.doubleValue() >= number.doubleValue() ? result : (N) number;
			}
			else
			{
				result = (N) number;
			}
		}
		return result;
	}

	@NotNull
	public Vector<N> normalized()
	{
		Number length = length();

		N[] resultValues = (N[]) elementData.clone();
		for (int count = 0; count < size; count++)
		{
			if (resultValues[count] != null)
			{
				resultValues[count] = (N) (new Double(elementData[count].doubleValue() / length.doubleValue()));
			}
		}
		return new Vector<>(resultValues);
	}

	public double length()
	{
		double result = 0;
		for (int count = 0; count < size; count++)
		{
			result += Math.pow((elementData[count]).doubleValue(), 2);
		}
		return result;
	}

	@SuppressWarnings({"unchecked"})
	@NotNull
	public Vector<N> rotate(@NotNull Vector<N> axis, float angle)
	{
		float sinAngle = (float) Math.sin(-angle);
		float cosAngle = (float) Math.cos(-angle);
		return this.cross(axis.mul((N) new Float(sinAngle))).add((this.mul((N) new Float(cosAngle))).add(axis.mul(this.dot(axis.mul((N) new Float(1 - cosAngle))))));
	}

	public Vector<N> add(@NotNull Vector<N> r)
	{
		if (size != r.length())
		{
			System.err.println("Invalid argument vector size size -> returning null!");
			return null;
		}

		Double[] result = (Double[]) elementData.clone();
		for (int count = 0; count < size; count++)
		{
			result[count] = result[count] + r.get(count).doubleValue();
		}
		return new Vector<>(result);
	}

	public Vector<N> cross(@NotNull Vector<N> r)
	{
		if (size != r.length())
		{
			System.err.println("Invalid argument vector size size -> returning null!");
			return null;
		}
		Number[] result;
		switch (this.size)
		{
			case 7:
				result = new Number[7];
				result[0] = this.get(1).doubleValue() * r.get(3).doubleValue() - this.get(3).doubleValue() * r.get(1).doubleValue() + this.get(2).doubleValue() * r.get(6).doubleValue() - this.get(6).doubleValue() * r.get(2).doubleValue() + this.get(4).doubleValue() * r.get(5).doubleValue() - this.get(5).doubleValue() * r.get(4).doubleValue();
				result[1] = this.get(2).doubleValue() * r.get(4).doubleValue() - this.get(4).doubleValue() * r.get(2).doubleValue() + this.get(3).doubleValue() * r.get(0).doubleValue() - this.get(0).doubleValue() * r.get(3).doubleValue() + this.get(5).doubleValue() * r.get(6).doubleValue() - this.get(6).doubleValue() * r.get(5).doubleValue();
				result[2] = this.get(3).doubleValue() * r.get(5).doubleValue() - this.get(5).doubleValue() * r.get(3).doubleValue() + this.get(4).doubleValue() * r.get(1).doubleValue() - this.get(1).doubleValue() * r.get(4).doubleValue() + this.get(6).doubleValue() * r.get(0).doubleValue() - this.get(0).doubleValue() * r.get(6).doubleValue();
				result[3] = this.get(4).doubleValue() * r.get(6).doubleValue() - this.get(6).doubleValue() * r.get(4).doubleValue() + this.get(5).doubleValue() * r.get(2).doubleValue() - this.get(2).doubleValue() * r.get(5).doubleValue() + this.get(0).doubleValue() * r.get(1).doubleValue() - this.get(1).doubleValue() * r.get(0).doubleValue();
				result[4] = this.get(5).doubleValue() * r.get(0).doubleValue() - this.get(0).doubleValue() * r.get(5).doubleValue() + this.get(6).doubleValue() * r.get(3).doubleValue() - this.get(3).doubleValue() * r.get(6).doubleValue() + this.get(1).doubleValue() * r.get(2).doubleValue() - this.get(2).doubleValue() * r.get(1).doubleValue();
				result[5] = this.get(6).doubleValue() * r.get(1).doubleValue() - this.get(1).doubleValue() * r.get(6).doubleValue() + this.get(0).doubleValue() * r.get(4).doubleValue() - this.get(4).doubleValue() * r.get(0).doubleValue() + this.get(2).doubleValue() * r.get(3).doubleValue() - this.get(3).doubleValue() * r.get(2).doubleValue();
				result[6] = this.get(0).doubleValue() * r.get(2).doubleValue() - this.get(2).doubleValue() * r.get(0).doubleValue() + this.get(1).doubleValue() * r.get(5).doubleValue() - this.get(5).doubleValue() * r.get(1).doubleValue() + this.get(3).doubleValue() * r.get(4).doubleValue() - this.get(4).doubleValue() * r.get(3).doubleValue();
				return new Vector<>(result);
			case 3:
				result = new Number[3];
				result[0] = this.get(1).doubleValue() * r.get(2).doubleValue() - this.get(2).doubleValue() * r.get(1).doubleValue();
				result[1] = this.get(2).doubleValue() * r.get(0).doubleValue() - this.get(0).doubleValue() * r.get(2).doubleValue();
				result[2] = this.get(0).doubleValue() * r.get(1).doubleValue() - this.get(1).doubleValue() * r.get(0).doubleValue();
				return new Vector<>(result);
			default:
				System.err.println("Cross product does not exist in " + size + "-dimensional Euclidean space -> returning null!");
				return null;
		}
	}

	@NotNull
	public Vector<N> mul(@NotNull N r)
	{
		Double[] result = (Double[]) elementData.clone();
		for (int count = 0; count < size; count++)
		{
			result[count] = result[count] * r.doubleValue();
		}
		return new Vector<>(result);
	}

	@SuppressWarnings({"unchecked"})
	public N dot(@NotNull Vector<N> r)
	{
		N result = (N) new Double(elementData[0].doubleValue() * r.get(0).doubleValue());
		for (int count = 1; count < size; count++)
		{
			result = (N) new Double(result.doubleValue() * r.get(count).doubleValue());
		}
		return result;
	}

	public N get(int index)
	{
		return (N) elementData[index];
	}

	/**
	 * This method can only be used with 2-dimensional vectors. If used with vectors extending above 2-dimensional
	 * space will always return null
	 *
	 * @param r
	 * 		another vector
	 *
	 * @return (scalar) cross product of two 2-dimensional vectors or {@code null} if {@code r.length() != 2}.
	 */

	public N toScalar(Vector<N> r)
	{
		return this.size != 2 ? null : (N) new Double(this.get(0).doubleValue() * r.get(1).doubleValue() - this.get(1).doubleValue() * r.get(0).doubleValue());
	}

	public N set(int index, @NotNull N newValue)
	{
		elementData[index] = newValue;
		return this.get(index);
	}

	// TODO: Vector<N> rotation
	//	@NotNull
	//	public Vector<N> rotate(@NotNull Quaternion rotation)
	//	{
	//		Quaternion conjugate = rotation.conjugated();
	//		Quaternion w = rotation.mul(this).mul(conjugate);
	//		return new Vector<N>(w.getX(), w.getY(), w.getZ());
	//	}

	@NotNull
	public Vector<N> lerp(@NotNull Vector<N> dest, @NotNull Vector<N> lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	public Vector<N> mul(@NotNull Vector<N> r)
	{
		if (size != r.length())
		{
			System.err.println("Invalid argument vector size size -> returning null!");
			return null;
		}
		Double[] result = (Double[]) elementData.clone();
		for (int count = 0; count < size; count++)
		{
			result[count] = result[count] * r.get(count).doubleValue();
		}
		return new Vector<>(result);
	}

	@NotNull
	public Vector<N> sub(@NotNull Vector<N> r)
	{
		Double[] result = new Double[this.size];
		for (int count = 0; count < this.size; count++)
		{
			result[count] = elementData[count].doubleValue() - r.get(count).doubleValue();
		}
		return new Vector<>((N[]) result);
	}

	@NotNull
	public Vector<N> set(Number[] values)
	{
		elementData = values;
		return this;
	}

	@NotNull
	public Vector<N> set(@NotNull Vector<N> r)
	{
		for (int count = 0; count < this.size; count++)
		{
			elementData[count] = r.get(count);
		}
		return this;
	}
	
	@NotNull

	public Vector<N> add(N r)
	{
		Double[] result = new Double[this.size];
		for (int count = 0; count < this.size; count++)
		{
			result[count] = elementData[count].doubleValue() + r.doubleValue();
		}
		return new Vector<>((N[]) result);
	}

	@NotNull
	public Vector<N> sub(N r)
	{
		Double[] result = new Double[this.size];
		for (int count = 0; count < this.size; count++)
		{
			result[count] = elementData[count].doubleValue() - r.doubleValue();
		}
		return new Vector<>((N[]) result);
	}

	@NotNull
	public Vector<N> div(@NotNull Vector<N> r)
	{
		if (size != r.length())
		{
			System.err.println("Invalid argument vector size size -> returning null!");
			return null;
		}

		Double[] result = new Double[this.size];
		for (int count = 0; count < this.size; count++)
		{
			result[count] = elementData[count].doubleValue() / r.get(count).doubleValue();
		}
		return new Vector<>((N[]) result);
	}

	@NotNull
	public Vector<N> div(N r)
	{
		Double[] result = new Double[this.size];
		for (int count = 0; count < this.size; count++)
		{
			result[count] = elementData[count].doubleValue() / r.doubleValue();
		}
		return new Vector<>((N[]) result);
	}

	@NotNull
	public Vector<N> abs()
	{
		Double[] result = new Double[this.size];
		for (int count = 0; count < this.size; count++)
		{
			result[count] = Math.abs(elementData[count].doubleValue());
		}
		return new Vector<>((N[]) result);
	}

	@Override
	public int hashCode()
	{
		return Arrays.hashCode(elementData);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof Vector))
		{
			return false;
		}

		Vector<?> vector = (Vector<?>) o;

		return Arrays.equals(elementData, vector.elementData);

	}

	@NotNull
	@Override
	public String toString()
	{
		String result = "(";
		for (Number number : elementData)
		{
			result += number + ", ";
		}
		return result.substring(0, result.length() - 2) + ")";
	}
}
