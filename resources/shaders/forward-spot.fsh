#version 120

varying vec2 texturePos0;
varying vec3 normal0;
varying vec3 worldPos0;

struct BaseLight
{
    vec3 color;
    float intensity;
};

struct Attenuation
{
    float constant;
    float linear;
    float exponent;
};

struct PointLight
{
    BaseLight base;
    Attenuation attenuation;
    vec3 position;
	float range;
};

struct SpotLight
{
    PointLight pointLight;
    vec3 direction;
	float cutoff;
};

uniform vec3 eyePos;
uniform sampler2D diffuse;

uniform float specularIntensity;
uniform float specularExponent;

uniform SpotLight spotLight;

vec4 calcLight(BaseLight base, vec3 direction, vec3 normal)
{
    float diffuseFactor = dot(normal, -direction);

    vec4 diffuseColor = vec4(0,0,0,0);
    vec4 specularColor = vec4(0,0,0,0);

    if(diffuseFactor > 0)
    {
        diffuseColor = vec4(base.color, 1.0) * base.intensity * diffuseFactor;

        vec3 directionToEye = normalize(eyePos - worldPos0);
        vec3 reflectDirection = normalize(reflect(direction, normal));

        float specularFactor = dot(directionToEye, reflectDirection);
        specularFactor = pow(specularFactor, specularExponent);

        if(specularFactor > 0)
        {
            specularColor = vec4(base.color, 1.0) * specularIntensity * specularFactor;
        }
    }

    return diffuseColor + specularColor;
}

vec4 calcPointLight(PointLight pointLight, vec3 normal)
{
    vec3 lightDirection = worldPos0 - pointLight.position;
    float pointDistance = length(lightDirection);

    if(pointDistance > pointLight.range)
        return vec4(0,0,0,0);

    lightDirection = normalize(lightDirection);

    vec4 color = calcLight(pointLight.base, lightDirection, normal);

    float attenuation = pointLight.attenuation.constant +
        pointLight.attenuation.linear * pointDistance +
        pointLight.attenuation.exponent * pointDistance * pointDistance +
        0.000001;

    return color / attenuation;
}

vec4 calcSpotLight(SpotLight spotlight, vec3 normal)
{
    vec3 lightDirection = normalize(worldPos0 - spotlight.direction);
    float spotFactor = dot(lightDirection, spotlight.direction);

    vec4 color = vec4(0,0,0,0);

    if(spotFactor > spotlight.cutoff)
    {
        color = calcPointLight(spotlight.pointLight, normal) *
        (1.0 - (1.0 - spotFactor) / (1.0 - spotlight.cutoff));
    }

    return color;
}

void main()
{
    gl_FragColor = texture2D(diffuse, texturePos0.xy) * calcSpotLight(spotLight, normalize(normal0));
}