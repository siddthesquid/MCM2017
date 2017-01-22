
public class HighwayGridObject {

	public boolean openSpace;
	public boolean hasCar;
	public Car car;
	
	public HighwayGridObject(boolean openSpace){
		this.openSpace = openSpace;
		this.hasCar = false;
		car = null;
	}
	
	public void addCar(Car c){
		hasCar = true;
		car = c;
	}
	
	public Car deleteCar(){
		hasCar = false;
		Car retCar = car;
		car = null;
		return retCar;
	}
	
}
