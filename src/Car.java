
public class Car {
	
	public enum LaneChangeDir {NONE,LEFT,RIGHT}
	
	public static final int MAX_SPEED = 5;
	public static final double DECEL_PROB = 0.25;
	public static final double CHANGE_PROB = 0.5;
	public static final int CAR_WIDTH = 15;
	
	public int velocity;
	public boolean autonomous;
	public LaneChangeDir laneIntents;
	public int distance;
	public int time;
	
	public Car(int initVelocity, boolean autonomous){
		velocity = initVelocity;
		this.autonomous = autonomous;
		laneIntents = LaneChangeDir.NONE;
		distance = 0;
		time = 0;
	}
	
//	public void changeLane(LaneChangeDir dir){
//		laneIntents = dir;
//	}
//	
//	public void laneChanged(){
//		laneIntents = LaneChangeDir.NONE;
//	}
	
	public void updateVelocity(int followingDistance){
		velocity += 1;
		velocity = Math.min(Math.min(velocity, followingDistance), MAX_SPEED);
		if(velocity >= 2 && Math.random() < DECEL_PROB) velocity--;
		
		distance+=CAR_WIDTH;
		time+=1;
	}

}
