
public class Car {
	
	public enum LaneChange {STILL, LEFT, RIGHT}
	
	public static final int MAX_SPEED = 5;
	public static final double DECEL_PROB = 0.25;
	public static final double CHANGE_PROB = 0.5;
	
	public int velocity;
	public boolean autonomous;
	public LaneChange direction;
	
	
	public Car(int initVelocity, boolean autonomous){
		velocity = initVelocity;
		this.autonomous = autonomous;
		direction = LaneChange.STILL;
	}
	
	public void changeLane(){
		
	}
	
	public void updateVelocity(int followingDistance){
		velocity += 1;
		velocity = Math.min(Math.min(velocity, followingDistance), MAX_SPEED);
		if(velocity >= 2 && Math.random() < DECEL_PROB) velocity--;
	}

}
