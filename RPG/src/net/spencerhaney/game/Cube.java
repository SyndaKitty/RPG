package net.spencerhaney.game;

import net.spencerhaney.engine.GameObject;
import net.spencerhaney.opengl.Face;
import net.spencerhaney.opengl.Vector3f;
import net.spencerhaney.opengl.Vertex;

public class Cube extends GameObject
{
    private Face face;

    private Vertex[] vertices;

    @Override
    public void init()
    {
        face = new Face();
        vertices = new Vertex[]
                {
                  new Vertex(-.5f, -.5f, 0, 1, (float)Math.random(), (float)Math.random(), (float)Math.random(), 1, 0, 1),
                  new Vertex(.5f, -.5f, 0, 1, (float)Math.random(), (float)Math.random(),(float)Math.random(), 1, 1, 1),
                  new Vertex(.5f, .5f, 0, 1, (float)Math.random(), (float)Math.random(), (float)Math.random(), 1, 1, 0),
                  new Vertex(-.5f, .5f, 0, 1, (float)Math.random(), (float)Math.random(), (float)Math.random(), 1, 0, 0),
                };
        face.init(Vector3f.TOWARD, vertices);
    }

    @Override
    public void update()
    {
        
    }

    @Override
    public void render()
    {
        face.render();
    }
}
