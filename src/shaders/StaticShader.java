package shaders;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends ShaderProgram{
	
	private int transformationMatLocation;
	
	private static final String VERTEX_FILE = "src/shaders/vertex_shader.vs";
	private static final String FRAGMENT_FILE = "src/shaders/fragment_shader.fs";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes(){
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	@Override
	protected void getAllUniformLocations(){
		transformationMatLocation = super.getUniformLocation("transformationMatrix");
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(transformationMatLocation, matrix);
	}
}
