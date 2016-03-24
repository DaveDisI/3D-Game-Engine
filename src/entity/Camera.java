package entity;

import org.lwjgl.util.vector.Vector3f;

import game_utils.InputManager;

public class Camera {

	private float distanceFromPlayer = 10;
	private float angleAroundPlayer = 0;
	
	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 15.0f;
	private float yaw;
	private float roll;
	
	private float speed = 0.3f;
	
	Player player;
	
	public Camera(Player player){
		this.player = player;
	}

	public void move(){
		calculateZoom();
		calculatePitch();
		calculateAngleAroundPlayer();
		
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPostion(horizontalDistance, verticalDistance);
		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}
	
	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calculateCameraPostion(float hDistance, float vDistance){
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (hDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (hDistance * Math.cos(Math.toRadians(theta)));
		
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;	
		position.y = player.getPosition().y + vDistance + 3;
	}
	
	private float calculateHorizontalDistance(){
		return (float)(distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}
	
	private float calculateVerticalDistance(){
		return (float)(distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
	
	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setRoll(float roll) {
		this.roll = roll;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	private void calculateZoom(){
		float zoomLevel = (float) (InputManager.getMouseScroll());
		distanceFromPlayer -= zoomLevel;
	}
	
	private void calculatePitch(){
		if(InputManager.rightMouseButton){
			float pitchChange = (float) (InputManager.getMouseDeltaY() * 0.1f);
			pitch -= pitchChange;
		}
	}
	
	private void calculateAngleAroundPlayer(){
		if(InputManager.leftMouseButton){
			float angleChange = InputManager.getMouseDeltaX() * 0.3f;
			angleAroundPlayer -= angleChange;
		}
	}
}
