package net.spencerhaney.engine;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public abstract class GameObject
{
    private float x;
    private float y;
    private float z;
    private int vao;
    private int vbo;
    private Game g;

    public GameObject()
    {
        float vertices[] = {-0.5f, -0.5f, 0.0f, 0.5f, -0.5f, 0.0f, 0.0f, 0.5f, 0.0f};

        // Position at 0
        FloatBuffer dataBuffer = BufferUtils.createFloatBuffer(vertices.length);

        // Put all the data in the buffer, position at the end of the data
        dataBuffer.put(vertices);
        // Set the limit at the position to the end of the data
        dataBuffer.flip();

        // Create a buffer with a pointer "vbo"
        vbo = GL15.glGenBuffers();

        // Generate a VAO
        vao = GL30.glGenVertexArrays();
        
        //Start specifying the configuration of the vao
        GL30.glBindVertexArray(vao);
        {
            // Hey were talking about the "vbo" buffer now
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

            // Put our data in the binded buffer
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, dataBuffer, GL15.GL_STATIC_DRAW);

            // Reading instructions. Start at 0, each vertex has 3 floats, not normalized, 0 stride = OpenGL determines,
            // 0 offset
            GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
            
            //Activate vertex attribute using va location of 0
            GL20.glEnableVertexAttribArray(0);
        }
        //Were done configuring
        GL30.glBindVertexArray(0);
        
        System.out.println("Rendering gameObject");
        System.out.println("Vao: " + vao);
        System.out.println("Vbo: " + vbo);
        GL20.glUseProgram(GLUtil.program);
        GL30.glBindVertexArray(vao);
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
        GL30.glBindVertexArray(0);
        GL20.glUseProgram(0);
    }

    public abstract void update();

    public void render()
    {
        
    }

    public void setGame(Game g)
    {
        this.g = g;
    }

    public Game getGame()
    {
        return g;
    }

}
