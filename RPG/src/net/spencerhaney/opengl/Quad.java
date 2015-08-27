package net.spencerhaney.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Quad
{
    private int vao;
    private int vbo;

    private FloatBuffer combinedBuffer;

    private int ivbo;
    private ByteBuffer indicesBuffer;
    private int indicesLength;

    private Vertex[] vertices;
    
    private int texture;
    
    /**
     * Create the necessary OpenGL components to render this Geometry
     * 
     * @param vertices
     *            The vertices in clockwise order
     */
    public void init(int texture, Vertex... vertices)
    {
        this.texture = texture;
        
        this.vertices = vertices;

        // Setup vertices
        setVertices(vertices);

        // Setup indices
        byte[] indices = {0, 1, 2, 0, 3, 2};
        indicesLength = indices.length;
        indicesBuffer = BufferUtils.createByteBuffer(indicesLength);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        
        // Create a vertex buffer object
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        {
            
            // Create a VBO for the positions
            vbo = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
            {
                GL15.glBufferData(GL15.GL_ARRAY_BUFFER, combinedBuffer, GL15.GL_STATIC_DRAW);
                GL20.glVertexAttribPointer(0, 4, GL11.GL_FLOAT, false, Vertex.STRIDE, 0);
                GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, Vertex.STRIDE, Vertex.COLOR_OFFEST);
                GL20.glVertexAttribPointer(2, 2, GL11.GL_FLOAT, false, Vertex.STRIDE, Vertex.TEXTURE_OFFSET);
            }
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

            // Done with the VAO
        }
        GL30.glBindVertexArray(0);

        // Create a VBO for the indices
        ivbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ivbo);
        {
            GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        }
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    /**
     * Set the vertices of this rectangle.
     * 
     * @param vertices
     *            The vertices of the rectangle in clockwise order
     */
    public void setVertices(Vertex... vertices)
    {
        for(Vertex v : vertices)
        {
            System.out.println(v.toString());
        }
        
        combinedBuffer = BufferUtils.createFloatBuffer(vertices.length * Vertex.STRIDE);

        for (Vertex v : vertices)
        {
            combinedBuffer.put(v.getElements());
        }

        combinedBuffer.flip();
    }

    public void update()
    {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        ByteBuffer vertexByteBuffer = BufferUtils.createByteBuffer(Vertex.STRIDE);
        for (int i = 0; i < vertices.length; i++)
        {
            FloatBuffer vertexFloatBuffer = vertexByteBuffer.asFloatBuffer();
            vertexFloatBuffer.rewind();
            vertexFloatBuffer.put(vertices[i].getElements());
            vertexFloatBuffer.flip();
            GL15.glBufferSubData(GL15.GL_ARRAY_BUFFER, i * Vertex.STRIDE, vertexByteBuffer);
        }
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void update(Vertex... vertices)
    {
        this.vertices = vertices;
        update();
    }

    public void render()
    {
        GL20.glUseProgram(GLUtil.program);
        {
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
            GL30.glBindVertexArray(vao);
            {
                GL20.glEnableVertexAttribArray(0);
                GL20.glEnableVertexAttribArray(1);

                GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ivbo);
                {
                    // Draw
                    GL11.glDrawElements(GL11.GL_TRIANGLES, indicesLength, GL11.GL_UNSIGNED_BYTE, 0);

                }
                GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

                GL20.glDisableVertexAttribArray(0);
                GL20.glDisableVertexAttribArray(1);
            }
            GL30.glBindVertexArray(0);
        }
        GL20.glUseProgram(0);
    }
}