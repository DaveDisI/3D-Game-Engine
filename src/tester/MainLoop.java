package tester;

import org.lwjgl.util.vector.Vector3f;

import entity.Entity;
import models.RawModel;
import models.TexturedModel;
import render_engine.Display;
import render_engine.Loader;
import render_engine.Renderer;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainLoop {
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	public static void main(String[] args) {
		Display.createDisplay(WIDTH, HEIGHT, "GAME!!!");
		
		Loader loader = new Loader();
		Renderer renderer = new Renderer();
		
		StaticShader shader = new StaticShader();
		
		float[] vertices = {
				-0.5f, 0.5f, 0.0f,
				-0.5f, -0.5f, 0.0f,
				 0.5f, -0.5f, 0.0f,
				 0.5f, 0.5f, 0.0f
		};
		
		int[] indices = {
				0, 1, 3, 
				3, 1, 2
		};
		
		float[] textureCoords = {
				0, 1,
				0, 0, 
				1, 0,
				1, 1
		};
		
		RawModel model = loader.loadToVAO(vertices, textureCoords, indices);
		ModelTexture texture = new ModelTexture(loader.loadTexture("building.jpg"));
		TexturedModel texturedModel = new TexturedModel(model, texture);
		
		Entity entity = new Entity(texturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		
		while(!Display.isCloseRequested()){
			entity.increasePosition(0, 0, 0);
			renderer.prepare();
			shader.start();
			renderer.render(entity, shader);
			shader.stop();
			//game logic
			//render
			Display.updateDisplay();
		}
		
		shader.cleanUp();
		loader.cleanUp();
		Display.closeDisplay();
	}
}
