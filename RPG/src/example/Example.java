package example;
import org.lwjgl.Sys;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWvidmode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.ByteBuffer;

//The following code was retrieved from www.LWJGL.org/guide
public class Example
{

    // Strongly reference callback instances
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;

    // Window handle
    private long window;

    public void run()
    {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
        try
        {
            init();
            loop();

            // Release window and window callback
            glfwDestroyWindow(window);
            keyCallback.release();
        }
        finally
        {
            // Terminate GLFW and release GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }

    private void init()
    {
        // Setup error callback. Print to System.err
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));

        // Initialize GLFW
        if (glfwInit() != GL11.GL_TRUE)
        {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure our window
        glfwDefaultWindowHints(); // optional, defaulted
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // window stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // resizable window

        int WIDTH = 300;
        int HEIGHT = 300;

        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
        {
            throw new RuntimeException("Failed to create the GLFW Window");
        }

        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback()
        {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods)
            {

            }
        });

        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        
        // Center our window
        glfwSetWindowPos(window, (GLFWvidmode.width(vidmode) - WIDTH) / 2, (GLFWvidmode.height(vidmode) - HEIGHT) / 2);

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop()
    {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();

        // Set the clear color
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        // Run rendering loop until closed
        while (glfwWindowShouldClose(window) == GL_FALSE)
        {
            //Clear the framebuffer
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            //Swap the color buffers
            glfwSwapBuffers(window);
            
            //Poll window for events (eg: key callback)
            glfwPollEvents();
        }
    }
    
    public static void main(String [] args){
        new Example().run();
    }
}
