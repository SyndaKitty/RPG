package net.spencerhaney.opengl;

public class Vector3f
{
    private float x;
    private float y;
    private float z;

    public Vector3f()
    {

    }

    public Vector3f(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void setX(float x)
    {
        this.x = x;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public void setZ(float z)
    {
        this.z = z;
    }

    public float getX()
    {
        return x;
    }
    
    public float getY()
    {
        return y;
    }
    
    public float getZ()
    {
        return z;
    }
}
