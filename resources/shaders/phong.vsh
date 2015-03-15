#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texturePos;
layout (location = 2) in vec3 normal;

out vec2 texturePos0;
out vec3 normal0;
out vec3 worldPos0;

uniform mat4 transform;
uniform mat4 transformProjected;

void main()
{
	gl_Position = transformProjected * vec4(position, 1.0);
	texturePos0 = texturePos;
	normal0 = (transform * vec4(normal, 0.0)).xyz;
	worldPos0 = (transform * vec4(position, 1.0)).xyz;
}
