package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Light;
import game_utils.MathUtils;

public class StaticShader extends ShaderProgram{
	
	private int transformationMatLocation;
	private int projectionMatLocation;
	private int viewMatLocation;
	private int lightPosLocation;
	private int lightColorLocation;
	private int shineDamperLocation;
	private int reflectivityLocation;
	private int useFakeLightingLocation;
	private int skyColorLocation;
	
	private static final String VERTEX_FILE = "src/shaders/vertex_shader.vs";
	private static final String FRAGMENT_FILE = "src/shaders/fragment_shader.fs";
	
	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void bindAttributes(){
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
		super.bindAttribute(2, "normal");
	}
	
	@Override
	protected void getAllUniformLocations(){
		transformationMatLocation = super.getUniformLocation("transformationMatrix");
		projectionMatLocation = super.getUniformLocation("projectionMatrix");
		viewMatLocation = super.getUniformLocation("viewMatrix");
		lightPosLocation = super.getUniformLocation("lightPosition");
		lightColorLocation = super.getUniformLocation("lightColor");
		shineDamperLocation = super.getUniformLocation("shineDamper");
		reflectivityLocation = super.getUniformLocation("reflectivity");
		useFakeLightingLocation = super.getUniformLocation("useFakeLighting");
		skyColorLocation = super.getUniformLocation("skyColor");
	}
	
	public void loadSkyColor(float r, float g, float b){
		super.loadVector(skyColorLocation, new Vector3f(r, g, b));
	}
	
	public void loadFakeLightingVariable(boolean useFake){
		super.loadBoolean(useFakeLightingLocation, useFake);
	}
	
	public void loadShineVariables(float damper, float reflectivity){
		super.loadFloat(shineDamperLocation, damper);
		super.loadFloat(reflectivityLocation, reflectivity);
	}
	
	public void loadTransformationMatrix(Matrix4f matrix){
		super.loadMatrix(transformationMatLocation, matrix);
	}
	
	public void loadLight(Light light){
		super.loadVector(lightPosLocation, light.getPosition());
		super.loadVector(lightColorLocation, light.getColor());
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = MathUtils.createViewMatrix(camera);
		super.loadMatrix(viewMatLocation, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(projectionMatLocation, projection);
	}
}
