package net.spencerhaney.opengl;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import net.spencerhaney.engine.ErrorCodes;
import net.spencerhaney.engine.Logging;

public class GLUtil
{
    public static int vertexShader;
    public static int fragmentShader;
    public static int program;

    private GLUtil()
    {
        // Do nothing
    }
    
    public static void init()
    {
        Logging.fine("OpenGL version: " + GL11.glGetString(GL11.GL_VERSION));
        
        String vShader = loadFile(Paths.get("GLSL/vertexShader.glsl"));
        vertexShader = createShader(GL20.GL_VERTEX_SHADER, vShader);

        String fShader = loadFile(Paths.get("GLSL/fragmentShader.glsl"));
        fragmentShader = createShader(GL20.GL_FRAGMENT_SHADER, fShader);

        program = createProgram(vertexShader, fragmentShader);
    }

    public static int createProgram(final int... shaders)
    {
        int program = GL20.glCreateProgram();
        for (int s : shaders)
        {
            GL20.glAttachShader(program, s);
        }

        // Position information will be attribute 0
        GL20.glBindAttribLocation(GLUtil.program, 0, "in_Position");
        // Color information will be attribute 1
        GL20.glBindAttribLocation(GLUtil.program, 1, "in_Color");
        //Normal information will be attibute 2 TODO look into linking up
        GL20.glBindAttribLocation(GLUtil.program, 2, "in_Normal");
        
        GL20.glLinkProgram(program);
        int status = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);
        if (status == GL11.GL_FALSE)
        {
            String error = GL20.glGetProgramInfoLog(program);
            System.err.printf("Linker failure: %s\n", error);
        }
        return program;
    }

    public static int createProgram(final int[] shaderTypes, final String[] shaders)
    {
        int[] shaderIds = new int[shaders.length];
        for (int i = 0; i < shaderIds.length; i++)
        {
            shaderIds[i] = createShader(shaderTypes[i], shaders[i]);
        }
        return createProgram(shaderIds);
    }

    public static int createShader(final int shadertype, final String shaderString)
    {
        int shader = GL20.glCreateShader(shadertype);
        GL20.glShaderSource(shader, shaderString);
        GL20.glCompileShader(shader);
        int status = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);
        if (status == GL11.GL_FALSE)
        {
            String error = GL20.glGetShaderInfoLog(shader);

            String shaderTypeString = null;
            switch (shadertype)
            {
                case GL20.GL_VERTEX_SHADER:
                    shaderTypeString = "vertex";
                    break;
                case GL32.GL_GEOMETRY_SHADER:
                    shaderTypeString = "geometry";
                    break;
                case GL20.GL_FRAGMENT_SHADER:
                    shaderTypeString = "fragment";
                    break;
            }
            System.err.printf("Compile failure in %s shader:\n%s\n", shaderTypeString, error);
            System.exit(ErrorCodes.SHADER_COMPILATION);
        }
        return shader;
    }

    public static void setWireframeMode(final boolean on)
    {
        if (on)
        {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        }
        else
        {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        }
    }
    
    public static String loadFile(final Path p)
    {
        Scanner in = null;
        try
        {
            in = new Scanner(p.toFile());
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        while (in.hasNextLine())
        {
            buffer.append(in.nextLine() + "\n");
        }
        return buffer.toString();
    }
}