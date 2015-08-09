#version 330 core
layout (location = 0) in vec3 position;
varying vec3 fNormal; 
varying vec3 fPosition;

void main()
{
	gl_Position = vec4(position.xyz, 1.0);
	fPosition = position.xyz;
}