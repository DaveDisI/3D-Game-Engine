package render_engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import entity.Camera;
import entity.Entity;
import entity.Light;
import models.TexturedModel;
import planet.Planet;
import planet.PlanetRenderer;
import planet.PlanetShader;
import shaders.StaticShader;
import shaders.TerrainShader;
import skybox.SkyboxRenderer;
import terrains.Terrain;

public class MasterRenderer {
	
	public static final float FOV = 70.0f;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000.0f;
	
	private static final float RED = 0.1f;
	private static final float GREEN = 0.45f;
	private static final float BLUE = 0.8f;
	
	private Matrix4f projectionMatrix;
	
	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;
	
	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();
	
	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Planet>> planets = new HashMap<TexturedModel, List<Planet>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	
	private SkyboxRenderer skyboxRenderer;
	
	PlanetRenderer planetRenderer;
	PlanetShader planetShader = new PlanetShader();
	
	public MasterRenderer(Loader loader){
		enableCulling();
		createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(loader, projectionMatrix);
		planetRenderer = new PlanetRenderer(planetShader, projectionMatrix);
	}
	
	public static void enableCulling(){
		//GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	
	public static void disableCulling(){
		GL11.glDisable(GL11.GL_CULL_FACE);
	}
	
	public void render(List<Light> lights, Camera camera){
		prepare();

		shader.start();
		shader.loadSkyColor(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities);
		shader.stop();
		
		terrainShader.start();
		terrainShader.loadSkyColor(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(terrains);
		terrainShader.stop();
		
		skyboxRenderer.render(camera, RED, GREEN, BLUE);
		
		planetShader.start();
		planetShader.loadSkyColor(RED, GREEN, BLUE);
		planetShader.loadLights(lights);
		planetShader.loadViewMatrix(camera);
		planetRenderer.render(planets);
		planetShader.stop();
		
		terrains.clear();
		entities.clear();
		planets.clear();
	}
	
	public void processTerrain(Terrain terrain){
		terrains.add(terrain);
	}
	
	public void processEntity(Entity entity){
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if(batch != null){
			batch.add(entity);
		}else{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}
	
	public void processPlanet(Planet entity){
		TexturedModel entityModel = entity.getModel();
		List<Planet> batch = planets.get(entityModel);
		if(batch != null){
			batch.add(entity);
		}else{
			List<Planet> newBatch = new ArrayList<Planet>();
			newBatch.add(entity);
			planets.put(entityModel, newBatch);
		}
	}
	
	public void cleanUp(){
		shader.cleanUp();
		terrainShader.cleanUp();
	}
	
	public Matrix4f getProjectionMatrix(){
		return projectionMatrix;
	}
	
	public void prepare(){
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(RED, GREEN, BLUE, 1.0f);
	}
	
	private void createProjectionMatrix(){
		float aspectRatio = (float)Display.getWidth() / Display.getHeight();
		float yScale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
		float xScale = yScale / aspectRatio;
		float frustumLength = FAR_PLANE - NEAR_PLANE;
		
		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = xScale;
		projectionMatrix.m11 = yScale;
		projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustumLength);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustumLength);
		projectionMatrix.m33 = 0;
	}
}
