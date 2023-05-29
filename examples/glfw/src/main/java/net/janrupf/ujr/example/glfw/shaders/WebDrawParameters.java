package net.janrupf.ujr.example.glfw.shaders;

import org.lwjgl.opengles.GLES30;

import java.io.IOException;
import java.io.InputStream;

public class WebDrawParameters {
    /**
     * This is a simple triangle which covers the whole screen.
     */
    private static final float[] VERTEX_DATA = {
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            -1.0f, 1.0f, 0.0f,

            -1.0f, 1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
            1.0f, -1.0f, 0.0f
    };

    private final int shaderProgram;
    private final int vertexArray;
    private final int vertexBuffer;

    private final int textureUniform;

    public WebDrawParameters() {
        // Read the vertex and fragment shader sources
        String vertexShaderSource = readShaderSource("web.vert");
        String fragmentShaderSource = readShaderSource("web.frag");

        // Compile both shaders
        int vertexShader = compileShader(vertexShaderSource, GLES30.GL_VERTEX_SHADER);
        int fragmentShader = compileShader(fragmentShaderSource, GLES30.GL_FRAGMENT_SHADER);

        // Link the shaders into a program
        int program = GLES30.glCreateProgram();
        GLES30.glAttachShader(program, vertexShader);
        GLES30.glAttachShader(program, fragmentShader);
        GLES30.glLinkProgram(program);

        // We can delete the shader objects after linking
        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);

        // Make sure the program has linked successfully
        int status = GLES30.glGetProgrami(program, GLES30.GL_LINK_STATUS);
        if (status == GLES30.GL_FALSE) {
            String infoLog = GLES30.glGetProgramInfoLog(program);
            throw new RuntimeException("Could not link program: " + infoLog);
        }

        this.shaderProgram = program;

        this.textureUniform = GLES30.glGetUniformLocation(shaderProgram, "Texture");

        // We need a vertex array object
        this.vertexArray = GLES30.glGenVertexArrays();

        // We also need a buffer which contains our vertex coordinates
        this.vertexBuffer = GLES30.glGenBuffers();

        // Bind the buffer, the vertex data is always the same for our case
        GLES30.glBindVertexArray(vertexArray);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vertexBuffer);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, VERTEX_DATA, GLES30.GL_STATIC_DRAW);
    }

    public void activateShaderProgram() {
        // Set the vertex data as the first vertex attribute
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 3 * Float.BYTES, 0);
        GLES30.glEnableVertexAttribArray(0);

        // Use the shader program
        GLES30.glUseProgram(shaderProgram);
        GLES30.glBindVertexArray(vertexArray);
    }

    public void setUniforms(
            int texture
    ) {
        GLES30.glUniform1i(textureUniform, texture);
    }

    public int getShaderProgram() {
        return shaderProgram;
    }

    private static int compileShader(String source, int type) {
        int shader = GLES30.glCreateShader(type);
        GLES30.glShaderSource(shader, source);
        GLES30.glCompileShader(shader);

        int status = GLES30.glGetShaderi(shader, GLES30.GL_COMPILE_STATUS);

        if (status == GLES30.GL_FALSE) {
            String infoLog = GLES30.glGetShaderInfoLog(shader);
            throw new RuntimeException("Could not compile shader: " + infoLog);
        }

        return shader;
    }

    private static String readShaderSource(String name) {
        try (InputStream inputStream = WebDrawParameters.class.getResourceAsStream("/shaders/" + name)) {
            if (inputStream == null) {
                throw new RuntimeException("Could not find shader source " + name);
            }

            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            int read;

            while ((read = inputStream.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, read));
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException("Could not read shader source " + name, e);
        }
    }
}
