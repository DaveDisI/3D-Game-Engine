package gui;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;
 
public class GUIShader extends ShaderProgram{
     
    private static final String VERTEX_FILE = "src/gui/gui_vertex_shader.vs";
    private static final String FRAGMENT_FILE = "src/gui/gui_fragment_shader.fs";
     
    private int transformationMatrixLocation;
 
    public GUIShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }
     
    public void loadTransformation(Matrix4f matrix){
        super.loadMatrix(transformationMatrixLocation, matrix);
    }
 
    @Override
    protected void getAllUniformLocations() {
    	transformationMatrixLocation = super.getUniformLocation("transformationMatrix");
    }
 
    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }
     
     
     
 
}