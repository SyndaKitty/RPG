package net.spencerhaney.opengl;

import java.util.Arrays;

public class Vertex
{
    // Vertex data
    private float[] xyzw = new float[] {0f, 0f, 0f, 1f};
    private float[] rgba = new float[] {1f, 1f, 1f, 1f};

    /**
     * The number of elements/floats the Vertex has
     */
    public static final int ELEMENTS = 8;

    /**
     * The size of each element/float the Vertex has
     */
    public static final int SIZE = 4;

    /**
     * The number of bytes each Vertex contains
     */
    public static final int STRIDE = ELEMENTS * SIZE;

    /**
     * The number of bytes that offset the color
     */
    public static final int COLOR_OFFEST = 4 * SIZE;

    public Vertex(float x, float y, float z, float r, float g, float b, float a)
    {
        setXYZ(x, y, z);
        setRGBA(r, g, b, a);
    }

    public Vertex(float x, float y, float z, float w, float r, float g, float b, float a)
    {
        setXYZW(x, y, z, w);
        setRGBA(r, g, b, a);
    }

    public Vertex(float x, float y, float z, float r, float g, float b)
    {
        setXYZ(x, y, z);
        setRGB(r, g, b);
    }

    public Vertex()
    {
        // Do nothing
    }

    // Setters
    public void setXYZ(float x, float y, float z)
    {
        this.setXYZW(x, y, z, 1f);
    }

    public void setRGB(float r, float g, float b)
    {
        this.setRGBA(r, g, b, 1f);
    }

    public void setXYZW(float x, float y, float z, float w)
    {
        this.xyzw = new float[] {x, y, z, w};
    }

    public void setRGBA(float r, float g, float b, float a)
    {
        this.rgba = new float[] {r, g, b, 1f};
    }

    // Getters
    public float[] getXYZW()
    {
        return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
    }

    public float[] getRGBA()
    {
        return new float[] {this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
    }

    public float[] getElements()
    {
        float[] elements = new float[ELEMENTS];
        elements[0] = xyzw[0];
        elements[1] = xyzw[1];
        elements[2] = xyzw[2];
        elements[3] = xyzw[3];
        elements[4] = rgba[0];
        elements[5] = rgba[1];
        elements[6] = rgba[2];
        elements[7] = rgba[3];
        return elements;
    }

    public String toString()
    {
        return Arrays.toString(xyzw) + Arrays.toString(rgba);
    }
}
