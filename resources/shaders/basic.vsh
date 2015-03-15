#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texturePos;

out vec2 texturePos0;

uniform mat4 transform;

void main()
{
    gl_Position = transform * vec4(position, 1.0);
	texturePos0 = texturePos;
}