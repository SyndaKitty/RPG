package net.spencerhaney.engine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public class GLUtil
{
    private GLUtil()
    {
        // Do nothing
    }

    public static int vertexShader;
    public static int fragmentShader;
    public static int program;    
    
    public static void init()
    {
        // Write our vertex shader to return the vec3 position plus a 1f for the w component
        String vShader = "#version 330 core\n" + "layout (location = 0) in vec3 position;\n" + "void main()\n"
                + "{\n" + "    gl_Position = vec4(position.x, position.y, position.z, 1.0);\n" + "}";

        vertexShader = createShader(GL20.GL_VERTEX_SHADER, vShader);
        
        // Write our fragment shader to return opaque red-orange
        String fShader = "#version 330\n" + "out vec4 color;\n" + "void main(){\n"
                + "    color = vec4(1.0f, 0.5f, 0.0f, 1f);\n" + "}";

        fragmentShader = createShader(GL20.GL_FRAGMENT_SHADER, fShader);
        
        program = createProgram(vertexShader, fragmentShader);
    }
    
    public static int createProgram(int... shaders)
    {
        int program = GL20.glCreateProgram();
        for (int s : shaders)
        {
            GL20.glAttachShader(program, s);
        }
        GL20.glLinkProgram(program);
        int status = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
        if (status == GL11.GL_FALSE)
        {
            String error = GL20.glGetProgramInfoLog(program);
            System.err.printf("Linker failure: %s\n", error);
        }
//        for (int s : shaders)
//        {
//            GL20.glDeleteShader(s);
//        }
        return program;
    }

    public static int createProgram(int[] shaderTypes, String[] shaders)
    {
        int[] shaderIds = new int[shaders.length];
        for (int i = 0; i < shaderIds.length; i++)
        {
            shaderIds[i] = createShader(shaderTypes[i], shaders[i]);
        }
        return createProgram(shaderIds);
    }

    public static int createShader(int shadertype, String shaderString)
    {
        int shader = GL20.glCreateShader(shadertype);
        GL20.glShaderSource(shader, shaderString);
        GL20.glCompileShader(shader);
        int status = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE)
        {
            String error = GL20.glGetShaderInfoLog(shader);

            String ShaderTypeString = null;
            switch (shadertype)
            {
                case GL20.GL_VERTEX_SHADER:
                    ShaderTypeString = "vertex";
                    break;
                case GL32.GL_GEOMETRY_SHADER:
                    ShaderTypeString = "geometry";
                    break;
                case GL20.GL_FRAGMENT_SHADER:
                    ShaderTypeString = "fragment";
                    break;
            }
            System.err.printf("Compile failure in %s shader:\n%s\n", ShaderTypeString, error);
            System.exit(ErrorCodes.SHADER_COMPILATION);
        }
        return shader;
    }
}
