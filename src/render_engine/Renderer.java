package render_engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entity.Entity;
import game_utils.MathUtils;
import models.RawModel;
import models.TexturedModel;
import shaders.StaticShader;

public class Renderer {
	
	public static final float FOV = 70.0f;
	public static final float NEAR_PLANE = 0.1f;
	public static final float FAR_PLANE = 1000.0f;
	
	private Matrix4f projectionMatrix;
	
	public void prepare(){
		GL11.glClearColor(0.0f, 0.5f, 1.0f, 1.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void render(Entity entity, StaticShader shader){
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(entity.getPosition(), entity.getRotX(),entity.getRotY(), entity.getRotZ(), entity.getScale());
		
		shader.loadTransformationMatrix(transformationMatrix);
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjectionMatrix(){
		//float apectRation = (float)
	}
	
}
