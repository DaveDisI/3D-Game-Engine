package game_utils;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWScrollCallback;

public class InputManager {
	public static boolean escape_key = false;
	public static boolean space_key = false;
	public static boolean a_key = false;
	public static boolean d_key = false;
	public static boolean f_key = false;
	public static boolean r_key = false;
	public static boolean s_key = false;
	public static boolean w_key = false;

	public static boolean leftMouseButton = false;
	public static boolean rightMouseButton = false;
	public static boolean middleMouseButton = false;

	public static double mouseScroll = 0;

	public static double mouseX = 0;
	public static double mouseY = 0;
	
	private static int lastMouseX = 0;
	private static int lastMouseY = 0;
	
	public static void init(long window) {
		GLFW.glfwSetCursorPosCallback(window, new MousePositionCallback());
		GLFW.glfwSetScrollCallback(window, new MouseScrollCallback());
		GLFW.glfwSetKeyCallback(window, new KeyCallback());
		GLFW.glfwSetMouseButtonCallback(window, new MouseButtonCallback());
	}

	public static int getMouseDeltaX() {
		int dx = (int)(mouseX - lastMouseX);
		lastMouseX = (int)mouseX;
		return dx;
	}

	public static int getMouseDeltaY() {
		int dy = (int)(mouseY - lastMouseY);
		lastMouseY = (int)mouseY;
		return dy;
	}

	public static double getMouseScroll(){
		double s = mouseScroll;
		mouseScroll = 0;
		return s;
	}
	
	private static class KeyCallback extends GLFWKeyCallback {

		@Override
		public void invoke(long window, int key, int scancode, int action, int mods) {
			if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS) {
				escape_key = true;
			} else if (key == GLFW.GLFW_KEY_W && action == GLFW.GLFW_RELEASE) {
				escape_key = false;
			}
			if (key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_PRESS) {
				space_key = true;
			} else if (key == GLFW.GLFW_KEY_SPACE && action == GLFW.GLFW_RELEASE) {
				space_key = false;
			}
			if (key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_PRESS) {
				a_key = true;
			} else if (key == GLFW.GLFW_KEY_A && action == GLFW.GLFW_RELEASE) {
				a_key = false;
			}
			if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_PRESS) {
				d_key = true;
			} else if (key == GLFW.GLFW_KEY_D && action == GLFW.GLFW_RELEASE) {
				d_key = false;
			}
			if (key == GLFW.GLFW_KEY_F && action == GLFW.GLFW_PRESS) {
				f_key = true;
			} else if (key == GLFW.GLFW_KEY_F && action == GLFW.GLFW_RELEASE) {
				f_key = false;
			}
			if (key == GLFW.GLFW_KEY_R && action == GLFW.GLFW_PRESS) {
				r_key = true;
			} else if (key == GLFW.GLFW_KEY_R && action == GLFW.GLFW_RELEASE) {
				r_key = false;
			}
			if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_PRESS) {
				s_key = true;
			} else if (key == GLFW.GLFW_KEY_S && action == GLFW.GLFW_RELEASE) {
				s_key = false;
			}
			if (key == GLFW.GLFW_KEY_W && action == GLFW.GLFW_PRESS) {
				w_key = true;
			} else if (key == GLFW.GLFW_KEY_W && action == GLFW.GLFW_RELEASE) {
				w_key = false;
			}
		}
	}

	private static class MouseButtonCallback extends GLFWMouseButtonCallback {
		@Override
		public void invoke(long window, int button, int action, int mods) {
			if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_PRESS) {
				leftMouseButton = true;
			} else if (button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_RELEASE) {
				leftMouseButton = false;
			}
			if (button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS) {
				rightMouseButton = true;
			} else if (button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_RELEASE) {
				rightMouseButton = false;
			}
			if (button == GLFW.GLFW_MOUSE_BUTTON_3 && action == GLFW.GLFW_PRESS) {
				middleMouseButton = true;
			} else if (button == GLFW.GLFW_MOUSE_BUTTON_3 && action == GLFW.GLFW_RELEASE) {
				middleMouseButton = false;
			}
		}
	}

	private static class MousePositionCallback extends GLFWCursorPosCallback {
		@Override
		public void invoke(long window, double xpos, double ypos) {
			mouseX = xpos;
			mouseY = ypos;
		}
	}

	private static class MouseScrollCallback extends GLFWScrollCallback {
		@Override
		public void invoke(long window, double xoffset, double yoffset) {
			mouseScroll += yoffset;
		}
	}
}
