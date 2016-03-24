package tester;

import org.lwjgl.util.vector.Vector3f;

import entity.Camera;
import entity.Entity;
import entity.Light;
import entity.Player;
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
		
		ModelTexture texture = new ModelTexture(loader.loadTexture("blend_map.png"));
		TexturedModel texturedModel = new TexturedModel(batmanModel, texture);

		texture.setShineDamper(10);
		texture.setReflectivity(1);

		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1, 1, 1));
		
		Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap.png");
		
		MasterRenderer renderer = new MasterRenderer();
		
		Player player = new Player(texturedModel, new Vector3f(500,0, -500), 0, 180, 0, 1);
		
		Camera camera = new Camera(player);
		
		while(!Display.isCloseRequested()){
			camera.move();
			player.move();
			
			renderer.processEntity(player);
			renderer.processTerrain(terrain);
			renderer.render(light, camera);
			
			Display.updateDisplay();
		}
		
		renderer.cleanUp();
		loader.cleanUp();
		Display.closeDisplay();
	}
}
