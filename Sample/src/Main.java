import java.io.FileNotFoundException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.system.MemoryUtil;

public class Main
{
    public static void main(String[] args)
    {
        // Create a window
        GLFW.glfwInit();
        long window = GLFW.glfwCreateWindow(800, 600, "Title", MemoryUtil.NULL, MemoryUtil.NULL);
        GLFW.glfwMakeContextCurrent(window);
        GLContext.createFromCurrent();

        // Load OpenGL shaders and link them with a program
        String vShader = loadFile(Paths.get("GLSL/vertexShader.glsl"));
        int vertexShader = createShader(GL20.GL_VERTEX_SHADER, vShader);
        String fShader = loadFile(Paths.get("GLSL/fragmentShader.glsl"));
        int fragmentShader = createShader(GL20.GL_FRAGMENT_SHADER, fShader);
        int program = createProgram(vertexShader, fragmentShader);

        // Create the indices vbo
        byte[] indices = {0, 1, 2, 0, 2, 3};
        ByteBuffer indicesBuffer = BufferUtils.createByteBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();
        int vboIndices = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboIndices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);

        //Create the vao
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        
        // Create the position vbo
        float[] vertices = {-0.5f, 0.5f, 0f,/**/ -0.5f, -0.5f, 0f, /**/0.5f, -0.5f, 0f, /**/0.5f, 0.5f, 0f};
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vertexBuffer.put(vertices);
        vertexBuffer.flip();
        int positionVbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionVbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glBindVertexArray(0);
        
        // Run the update loop
        while (GLFW.glfwWindowShouldClose(window) == GL11.GL_FALSE)
        {
            
        }
    }

    public static int createProgram(final int... shaders)
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
            System.out.println(String.format("Linker failure: %s\n", error));
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
            System.out.println(String.format("Compile failure in %s shader:\n%s\n", shaderTypeString, error));
            System.exit(-1);
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
