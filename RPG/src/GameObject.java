
public class GameObject {
	private double x;
	private double y;
	private Game g;
	
	public GameObject(Game g){
		this.g = g;
		x = 0;
		y = 0;
	}
	
	public void update(){
		x += 1 * Time.getDelta();
		y += 1 * Time.getDelta();
	}
	
	public void render(){
		System.out.println(x + " " + y);
	}
	
}
