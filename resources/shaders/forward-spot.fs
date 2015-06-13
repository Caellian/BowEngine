#version 120
#include "./resources/shaders/lighting.glh"

varying vec2 texCoord0;
varying vec3 normal0;
varying vec3 worldPos0;

uniform sampler2D R_diffuse;
uniform SpotLight R_spotLight;

void main()
{
    gl_FragColor = texture2D(R_diffuse, texCoord0.xy) * CalcSpotLight(R_spotLight, normalize(normal0), worldPos0);
}
