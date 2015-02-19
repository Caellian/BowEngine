package com.skythees.bowEngine.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ResourceLoader
{
	public static String loadShader(String fileName)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try
		{
            File shaderFile = new File("./resources/shaders/" + fileName);
//            System.out.println(shaderFile.getAbsolutePath());
            shaderReader = new BufferedReader(new FileReader(shaderFile));
            String line;
			
			while((line = shaderReader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			
			shaderReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return shaderSource.toString();
	}
}
