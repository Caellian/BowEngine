#version 330

const int MAX_LIGHTS = 10;

in vec2 texturePos0;
in vec3 normal0;
in vec3 worldPos0;

out vec4 fragColor;

struct BaseLight
{
    vec3 color;
    float intensity;
};

struct DirectionalLight
{
    BaseLight base;
    vec3 direction;
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

uniform vec3 baseColor;
uniform vec3 eyePos;
uniform vec3 ambientLight;
uniform sampler2D sampler;

uniform float specularIntensity;
uniform float specularExponent;

uniform DirectionalLight directionalLight;
uniform PointLight pointLights[MAX_LIGHTS];
uniform SpotLight spotLights[MAX_LIGHTS];

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

vec4 calcDirectionalLight(DirectionalLight directionalLight, vec3 normal)
{
    return calcLight(directionalLight.base, -directionalLight.direction, normal);
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
    vec4 totalLight = vec4(ambientLight,1);
    vec4 color = vec4(baseColor, 1);
    vec4 textureColor = texture(sampler, texturePos0.xy);
    
    if(textureColor != vec4(0,0,0,0))
        color *= textureColor;
    
    vec3 normal = normalize(normal0);
    
    totalLight += calcDirectionalLight(directionalLight, normal);

    for(int i = 0; i < MAX_LIGHTS; i++)
	    if(pointLights[i].base.intensity > 0)
            totalLight += calcPointLight(pointLights[i], normal);

    for(int i = 0; i < MAX_LIGHTS; i++)
	    if(spotLights[i].pointLight.base.intensity > 0)
            totalLight += calcSpotLight(spotLights[i], normal);
    
    fragColor = color * totalLight;
}
