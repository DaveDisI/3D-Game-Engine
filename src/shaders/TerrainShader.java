package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Light;
import game_utils.MathUtils;

public class TerrainShader extends ShaderProgram{
	
	private static final int MAX_LIGHTS = 4;
	
	private static final String VERTEX_FILE = "src/shaders/terrain_vertex_shader.vs";
	private static final String FRAGMENT_FILE = "src/shaders/terrain_fragment_shader.fs";
	
	private int transformationMatLocation;
	private int projectionMatLocation;
	private int viewMatLocation;
	private int lightPosLocation[];
	private int lightColorLocation[];
	private int attenuationLocation[];
	private int shineDamperLocation;
	private int reflectivityLocation;
	private int skyColorLocation;
	private int backgroundTextureLocation;
	private int rTextureLocation;
	private int gTextureLocation;
	private int bTextureLocation;
	private int blendMapLocation;
	
	
	
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
		shineDamperLocation = super.getUniformLocation("shineDamper");
		reflectivityLocation = super.getUniformLocation("reflectivity");
		skyColorLocation = super.getUniformLocation("skyColor");
		backgroundTextureLocation = super.getUniformLocation("backgroundTexture");
		rTextureLocation = super.getUniformLocation("rTexture");
		gTextureLocation = super.getUniformLocation("gTexture");
		bTextureLocation = super.getUniformLocation("bTexture");
		blendMapLocation = super.getUniformLocation("blendMap");
		
		lightPosLocation = new int[MAX_LIGHTS];
		lightColorLocation = new int[MAX_LIGHTS];
		attenuationLocation = new int[MAX_LIGHTS];
		
		for(int i = 0; i < MAX_LIGHTS; i++){
			lightPosLocation[i] = super.getUniformLocation("lightPosition[" + i + "]");
			lightColorLocation[i] = super.getUniformLocation("lightColor[" + i + "]");
			attenuationLocation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
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
	
	public void loadLights(List<Light> lights){
		for(int i = 0; i < MAX_LIGHTS; i++){
			if(i < lights.size()){
				super.loadVector(lightPosLocation[i], lights.get(i).getPosition());
				super.loadVector(lightColorLocation[i], lights.get(i).getColor());
				super.loadVector(attenuationLocation[i], lights.get(i).getAttenuation());
			}else{
				
			}
			super.loadVector(lightPosLocation[i], new Vector3f(0, 0, 0));
			super.loadVector(lightColorLocation[i],  new Vector3f(0, 0, 0));
			super.loadVector(attenuationLocation[i], lights.get(i).getAttenuation());
		}
	}
	
	public void loadViewMatrix(Camera camera){
		Matrix4f viewMatrix = MathUtils.createViewMatrix(camera);
		super.loadMatrix(viewMatLocation, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection){
		super.loadMatrix(projectionMatLocation, projection);
	}
}
