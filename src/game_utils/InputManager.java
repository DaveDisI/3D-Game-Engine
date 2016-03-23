package game_utils;

import static org.lwjgl.glfw.GLFW.GLFW_TRUE;

import org.lwjgl.glfw.GLFW;

public class InputManager {
	public static boolean escape_key = false;
	public static boolean space_key = false;
	public static boolean a_key = false;
	public static boolean d_key = false;
	public static boolean f_key = false;
	public static boolean r_key = false;
	public static boolean s_key = false;
	public static boolean w_key = false;
	
	public static void update(long window) {
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_ESCAPE) == 1){
			escape_key = true;
		}else{
			escape_key = false;
		}
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_A) == 1){
			a_key = true;
		}else{
			a_key = false;
		}
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_D) == 1){
			d_key = true;
		}else{
			d_key = false;
		}
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_F) == 1){
			f_key = true;
		}else{
			f_key = false;
		}
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_R) == 1){
			r_key = true;
		}else{
			r_key = false;
		}
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_S) == 1){
			s_key = true;
		}else{
			s_key = false;
		}
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_W) == 1){
			w_key = true;
		}else{
			w_key = false;
		}
		if(GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == 1){
			space_key = true;
		}else{
			space_key = false;
		}
	}
}
