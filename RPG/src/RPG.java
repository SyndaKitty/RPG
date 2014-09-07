import java.util.ArrayList;


public class RPG extends Game{

	private ArrayList<GameObject> objects;
	
	public RPG(){
		objects = new ArrayList<GameObject>();
		objects.add(new GameObject(this));
	}
	
	@Override
	public void getInput() {
		
	}

	@Override
	public void update() {
		for(GameObject o : objects){
			o.update();
		}
	}

	@Override
	public void render() {
		for(GameObject o : objects){
			o.render();
		}
	}

}
