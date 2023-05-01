import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.BufferStrategy;
import java.awt.image.DataBufferByte;
import javax.swing.*;

public class Display extends Canvas {
    private final JFrame frame;
    private final BufferedImage displayImage;
    private final RenderContext frameBuffer;
    private final byte[] displayComponents;
    public final BufferStrategy bufferStrategy;
    private final Graphics graphics;
    public RenderContext GetFrameBuffer() { return frameBuffer; }

    public Display(int width, int height, String title){
        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size); //set min and max size in code so that way window is not accidentally resized ever

        frameBuffer = new RenderContext(width, height);
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        displayComponents = ((DataBufferByte)displayImage.getRaster().getDataBuffer()).getData(); //why in the actual heck is it so difficult to access the bytes of the image

        frameBuffer.Clear((byte)0x80); //set screen to grey

        frame = new JFrame();
        frame.add(this);
        frame.pack(); //make not resizable
        frame.setResizable(false); //make it not resizable because of 3d graphic stuff
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); //set frame in center of screen
        frame.setTitle(title);
        frame.setVisible(true);

        createBufferStrategy(1);
        bufferStrategy = getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();
    }

    public void SwapBuffers() {
        frameBuffer.CopyToByteArray(displayComponents); //copy the bit map into display components
        graphics.drawImage(displayImage, 0, 0, frameBuffer.GetWidth(), frameBuffer.GetHeight(), null);
        bufferStrategy.show();
    }
}
