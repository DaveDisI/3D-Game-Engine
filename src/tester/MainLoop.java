package tester;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
import gui.GUIRenderer;
import gui.GUITexture;
import models.RawModel;
import models.TexturedModel;
import obj_loader.ModelData;
import obj_loader.OBJFileLoader;
import render_engine.Display;
import render_engine.Loader;
import render_engine.MasterRenderer;
import render_engine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainLoop {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static void main(String[] args) {
		Display.createDisplay(WIDTH, HEIGHT, "GAME!!!");
		Loader loader = new Loader();

		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grass.png"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("stone.png"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("flowers.png"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("brick.png"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blend_map.png"));

		ModelData batmanData = OBJFileLoader.loadOBJ("batman");
		RawModel batmanModel = loader.loadToVAO(batmanData.getVertices(), batmanData.getTextureCoords(), batmanData.getNormals(), batmanData.getIndices());
		
		ModelTexture fernTextureAt = new ModelTexture(loader.loadTexture("leaf.png"));
		fernTextureAt.setNumberOfRows(2);
		fernTextureAt.setTransparancy(true);
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("blend_map.png"));
		TexturedModel texturedModel = new TexturedModel(batmanModel, texture);

		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(fernData.getVertices(), fernData.getTextureCoords(), fernData.getNormals(), fernData.getIndices());
		TexturedModel fernTModel = new TexturedModel(fernModel, fernTextureAt);
		
		texture.setShineDamper(10);
		texture.setReflectivity(1);

		List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(3000, 2000, 2000), new Vector3f(0.8f, 0.8f, 0.8f)));
		lights.add(new Light(new Vector3f(490, 10, -500), new Vector3f(1, 0, 0), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(510, 10, -500), new Vector3f(0, 0, 1), new Vector3f(1, 0.01f, 0.002f)));
		lights.add(new Light(new Vector3f(500, 10, -510), new Vector3f(0, 1, 0), new Vector3f(1, 0.01f, 0.002f)));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap.png");
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		Player player = new Player(texturedModel, new Vector3f(500, 0, -500), 0, 180, 0, 1);
		Entity fern = new Entity(fernTModel, 0, new Vector3f(500, terrain.getHeightOfTerrain(500, -500), -500), 0, 0, 0, 1);
		
		Camera camera = new Camera(player);
		
		List<GUITexture> guis = new ArrayList<GUITexture>();
		GUITexture gui = new GUITexture(loader.loadTexture("building.jpg"), new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		
		GUIRenderer guiRenderer = new GUIRenderer(loader);
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move(terrain);
			
			renderer.processEntity(player);
			renderer.processEntity(fern);
			renderer.processTerrain(terrain);
			renderer.render(lights, camera);
			//guiRenderer.render(guis);
			Display.updateDisplay();
		}
		
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		Display.closeDisplay();
	}
}
