
public class Time {
	
	private static final double MILLIS_IN_SECOND = 1000;
	
	private static long lastTime;
	private static long currentTime;
	
	private Time(){}
	
	public static void init(){
		currentTime = lastTime = System.currentTimeMillis();
	}
	
	public static void update(){
		lastTime = currentTime;
		currentTime = System.currentTimeMillis();
	}
	
	public static double getDelta(){
		return (currentTime - lastTime) / MILLIS_IN_SECOND;
	}
}
