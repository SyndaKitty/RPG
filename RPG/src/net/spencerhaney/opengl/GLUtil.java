package net.spencerhaney.opengl;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import net.spencerhaney.engine.ErrorCodes;

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
        String vShader = loadFile(Paths.get("GLSL/vertexShader.glsl"));
        vertexShader = createShader(GL20.GL_VERTEX_SHADER, vShader);

        String fShader = loadFile(Paths.get("GLSL/fragmentShader.glsl"));
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

    public static void wireframeMode(boolean on)
    {
        if (on)
        {
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
        }else
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
