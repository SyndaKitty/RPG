#version 330

in vec3 pass_Color;
out vec4 color;

void main(){
	color = vec4(pass_Color, 1.0f);
}