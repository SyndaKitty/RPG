#version 330
out vec4 color;

varying vec3 fPosition;

void main(){
	color = vec4(fPosition, 1.0);
}