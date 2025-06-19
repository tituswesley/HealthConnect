package temp;

public class TestCar {
	
	public static void main(String[] arg) {
		MarutiCar m = new MarutiCar(); // child
		m.start();
		m.refuel();
		m.musicSystem();
		
		
		Car c = new Car();// parent class
		c.start();
		c.refuel();
		
	}

}
