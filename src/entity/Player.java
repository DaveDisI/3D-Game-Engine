package entity;

import org.lwjgl.util.vector.Vector3f;

import game_utils.InputManager;
import models.TexturedModel;
import render_engine.Display;

public class Player extends Entity {

	private static final float RUN_SPEED = 20f;
	private static final float TURN_SPEED = 160f;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;
	
	private static final float TERRAIN_HEIGHT = 0;
		
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	
	private boolean isInAir = false;
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(){
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * Display.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * Display.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		upwardsSpeed += GRAVITY * Display.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * Display.getFrameTimeSeconds(), 0);
		
		if(super.getPosition().y < TERRAIN_HEIGHT){
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = TERRAIN_HEIGHT;
		}
	}
	
	private void jump(){
		if(!isInAir){
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
		
	}
	
	private void checkInputs(){
		if(InputManager.w_key){
			this.currentSpeed = RUN_SPEED;
		}else if(InputManager.s_key){
			this.currentSpeed = -RUN_SPEED;
		}else{
			this.currentSpeed = 0;
		}
		if(InputManager.a_key){
			this.currentTurnSpeed = TURN_SPEED;
		}else if(InputManager.d_key){
			this.currentTurnSpeed = -TURN_SPEED;
		}else{
			this.currentTurnSpeed = 0;
		}
		if(InputManager.space_key){
			jump();
		}
		
	}
}
