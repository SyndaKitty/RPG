package net.spencerhaney.game;

import net.spencerhaney.engine.GameObject;
import net.spencerhaney.engine.Time;
import net.spencerhaney.opengl.Quad;
import net.spencerhaney.opengl.Vertex;

public class Cube extends GameObject
{
    private Quad quad;

    private Vertex[] vertices;
    private float time = 0;

    @Override
    public void init()
    {
        quad = new Quad();
        vertices = new Vertex[] {
            //@formatter:off
            new Vertex(-0.5f, -0.5f,  0.0f, 1f, 0f, 0f, 1f),
            new Vertex(-0.5f,  0.5f,  0.0f, 0f, 1f, 0f, 1f),
            new Vertex( 0.5f,  0.5f,  0.0f, 0f, 0f, 1f, 1f),
            new Vertex( 0.5f, -0.5f,  0.0f, 1f, 1f, 1f, 1f)
            //@formatter:on
        };
        quad.init(vertices);
    }

    @Override
    public void update()
    {
        time += Time.getDelta();
        time = (float)(time % (Math.PI * 2)); // Normalize time
        float q = (float)(Math.PI / 2);
        float x0 = (float)Math.cos(time) * 0.5f;
        float y0 = (float)Math.sin(time) * 0.5f;
        
        float x1 = (float)Math.cos(time + q) * 0.5f;
        float y1 = (float)Math.sin(time + q) * 0.5f;
        
        float x2 = (float)Math.cos(time + 2 * q) * 0.5f;
        float y2 = (float)Math.sin(time + 2 * q) * 0.5f;
        
        float x3 = (float)Math.cos(time - q) * 0.5f;
        float y3 = (float)Math.sin(time - q) * 0.5f;

        vertices = new Vertex[] {
                //@formatter:off
                new Vertex(x0, y0, 0.0f, x0 + 0.5f, 0f, 0f, 1f),
                new Vertex(x1, y1, 0.0f, 0f, x1 + 0.5f, 0f, 1f),
                new Vertex(x2, y2, 0.0f, 0f, 0f, x2 + 0.5f, 1f),
                new Vertex(x3, y3, 0.0f, x3 + 0.5f, x3 + 0.5f, x3 + 0.5f, 1f),
                //@formatter:on
        };
        quad.update(vertices);
    }

    @Override
    public void render()
    {
        quad.render();
    }
}
