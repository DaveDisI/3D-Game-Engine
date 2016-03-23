package shaders;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Light;
import game_utils.MathUtils;

public class TerrainShader extends ShaderProgram{
	private int transformationMatLocation;
	private int projectionMatLocation;
	private int viewMatLocation;
	private int lightPosLocation;
	private int lightColorLocation;
	private int shineDamperLocation;
	private int reflectivityLocation;
	private int skyColorLocation;
	private int backgroundTextureLocation;
	private int rTextureLocation;
	private int gTextureLocation;
	private int bTextureLocation;
	private int blendMapLocation;
	
	private static final String VERTEX_FILE = "src/shaders/terrain_vertex_shader.vs";
	private static final String FRAGMENT_FILE = "src/shaders/terrain_fragment_shader.fs";
	
	public TerrainShader() {
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
		skyColorLocation = super.getUniformLocation("skyColor");
		backgroundTextureLocation = super.getUniformLocation("backgroundTexture");
		rTextureLocation = super.getUniformLocation("rTexture");
		gTextureLocation = super.getUniformLocation("gTexture");
		bTextureLocation = super.getUniformLocation("bTexture");
		blendMapLocation = super.getUniformLocation("blendMap");
	}
	
	public void connectTextureUnits(){
		super.loadInt(backgroundTextureLocation, 0);
		super.loadInt(rTextureLocation, 1);
		super.loadInt(gTextureLocation, 2);
		super.loadInt(bTextureLocation, 3);
		super.loadInt(blendMapLocation, 4);
	}
	
	public void loadSkyColor(float r, float g, float b){
		super.loadVector(skyColorLocation, new Vector3f(r, g, b));
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
