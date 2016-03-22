#version 400 core

in vec3 position;
in vec2 textureCoords;

out vec2 texturePassCoords;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;

void main(){
	gl_Position = projectionMatrix * transformationMatrix * vec4(position, 1.0);
	texturePassCoords = textureCoords;
}