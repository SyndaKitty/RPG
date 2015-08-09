package net.spencerhaney.game;

import net.spencerhaney.engine.GameObject;
import net.spencerhaney.engine.Time;
import net.spencerhaney.opengl.Triangle;

public class Cube extends GameObject
{
    Triangle t;
    private float time;
    
    @Override
    public void init()
    {
        t = new Triangle();
        t.init(1.0f, 0.0f, 1.0f, -.5f, .86f, 0.0f, -.5f, -.86f, 0.5f);
    }
    
    @Override
    public void update()
    {
        time += Time.getDelta();
        float o = (float)(Math.PI * 2) / 3.0f;
        float p = 2 * o;
        t.setVertices(
                (float)Math.cos(time    ), (float)Math.sin(time    ), 0.0f,
                (float)Math.cos(time + o), (float)Math.sin(time + o), 0.0f,
                (float)Math.cos(time + p), (float)Math.sin(time + p), 1.0f
        );
    }

    @Override
    public void render()
    {
        t.render();
    }
}
