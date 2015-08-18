package net.spencerhaney.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Quad extends Geometry
{
    private static final int SIZE = 4;
    private static final int STRIDE = 7;
    private static final int COLOR_OFFSET = 3;

    private int vao;

    private int vbo;
    private int cvbo;

    private FloatBuffer combinedBuffer;

    private int ivbo;
    private ByteBuffer indicesBuffer;
    private int indicesLength;

    public void init(float[] positions, float[] colors)
    {
        // Setup vertices
        setVertices(positions, colors);

        // Setup indices
        byte[] indices = {0, 2, 1, 0, 3, 2};
        indicesLength = indices.length;
        indicesBuffer = BufferUtils.createByteBuffer(indicesLength);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        // Create a vertex buffer object
        vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        {

            // Create a VBO for the vertices
            vbo = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
            {
                GL15.glBufferData(GL15.GL_ARRAY_BUFFER, combinedBuffer, GL15.GL_STREAM_DRAW);
                GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, STRIDE * SIZE, 0);
            }
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

            // Create a VBO for the colors
            cvbo = GL15.glGenBuffers();
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, cvbo);
            {
                GL15.glBufferData(GL15.GL_ARRAY_BUFFER, combinedBuffer, GL15.GL_STATIC_DRAW);
                GL20.glVertexAttribPointer(1, 4, GL11.GL_FLOAT, false, STRIDE * SIZE, COLOR_OFFSET * SIZE);
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
    public void setVertices(float[] vertices, float[] colors)
    {
        combinedBuffer = BufferUtils.createFloatBuffer(vertices.length + colors.length);
        int positionIndex = 0;
        int colorIndex = 0;
        for (int length = 0; length < vertices.length + colors.length;)
        {
            for (int i = 0; i < COLOR_OFFSET; i++)
            {
                length++;
                combinedBuffer.put(vertices[positionIndex++]);
            }
            for (int j = 0; j < STRIDE - COLOR_OFFSET; j++)
            {
                length++;
                combinedBuffer.put(colors[colorIndex++]);
            }
        }
        combinedBuffer.flip();
    }

    public void render()
    {
        GL20.glUseProgram(GLUtil.program);
        {
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
