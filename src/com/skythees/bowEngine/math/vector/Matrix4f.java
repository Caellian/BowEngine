package com.skythees.bowEngine.math.vector;

public class Matrix4f
{
	private float[][] matrix;
	
	public Matrix4f()
	{
		matrix = new float[4][4];
	}

	public Matrix4f initIdentity()
	{
        for (int x : new int[]{0, 1, 2, 3}) {
            for (int y : new int[]{0, 1, 2, 3}) {
                matrix[x][y] = y == x ? 1 : 0;
            }
        }
        return this;
	}
	
	public Matrix4f mul(Matrix4f r)
	{
		Matrix4f res = new Matrix4f();
		
		for(int i = 0; i < 4; i++)
		{
			for(int j = 0; j < 4; j++)
			{
				res.set(i, j, matrix[i][0] * r.get(0, j) +
							  matrix[i][1] * r.get(1, j) +
							  matrix[i][2] * r.get(2, j) +
							  matrix[i][3] * r.get(3, j));
			}
		}
		
		return res;
	}
	
	public float[][] getMatrix()
	{
		return matrix;
	}
	
	public float get(int x, int y)
	{
		return matrix[x][y];
	}

	public void setMatrix(float[][] matrix)
	{
		this.matrix = matrix;
	}
	
	public void set(int x, int y, float value)
	{
		matrix[x][y] = value;
	}
}
