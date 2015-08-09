package net.spencerhaney.opengl;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Rectangle extends Geometry
{
    private FloatBuffer buffer;
    private int vao;

    public void init(float ... vertices)
    {
        buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();

        int vbo = GL15.glGenBuffers();
        vao = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vao);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STREAM_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
    }

    public void setVertices(float ... vertices)
    {
        buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
    }

    public void render()
    {
        GL20.glUseProgram(GLUtil.program);
        GL30.glBindVertexArray(vao);
        GL20.glEnableVertexAttribArray(0);
        
        //Update data
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STREAM_DRAW);
        
        //Draw
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        
        //Cleanup
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
    }
}
