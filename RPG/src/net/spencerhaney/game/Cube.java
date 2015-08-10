package net.spencerhaney.game;

import net.spencerhaney.engine.GameObject;
import net.spencerhaney.opengl.Quad;

public class Cube extends GameObject
{
    private Quad q;
    
    @Override
    public void init()
    {
        q = new Quad();
        q.init(-0.5f, -0.5f,  0.0f,  1.0f, 
               -0.5f,  0.5f,  0.0f,  1.0f, 
                0.5f,  0.5f,  0.0f,  1.0f, 
                0.5f, -0.5f,  0.0f,  1.0f);
    }
    
    @Override
    public void update()
    {
        
    }

    @Override
    public void render()
    {
       q.render();
    }
}
