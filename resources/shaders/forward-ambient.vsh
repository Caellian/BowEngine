#version 120

attribute vec3 position;
attribute vec2 texturePos;

varying vec2 texturePos0;

uniform mat4 MVP;

void main()
{
    gl_Position = MVP * vec4(position, 1.0);
	texturePos0 = texturePos;
}