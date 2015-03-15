#version 120

attribute vec3 position;
attribute vec2 texturePos;
attribute vec3 normal;

varying vec2 texturePos0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform mat4 model;
uniform mat4 MVP;

void main()
{
	gl_Position = MVP * vec4(position, 1.0);
	texturePos0 = texturePos;
	normal0 = (model * vec4(normal, 0.0)).xyz;
	worldPos0 = (model * vec4(position, 1.0)).xyz;
}
