import java.util.Arrays;
public class Bitmap
{
    private final int  _width;
    private final int  _height;
    private final byte components[];
    public int GetWidth() { return _width; }
    public int GetHeight() { return _height; }
    public Bitmap(int width, int height)
    {
        _width      = width;
        _height     = height;
        components = new byte[_width * _height * 4];
    }
    public void Clear(byte shade)
    {
        Arrays.fill(components, shade);
    }
    public void DrawPixel(int x, int y, byte a, byte b, byte g, byte r)
    {
        int index = (x + y * _width) * 4;
        components[index    ] = a;
        components[index + 1] = b;
        components[index + 2] = g;
        components[index + 3] = r;
    }
    public void CopyToByteArray(byte[] dest)
    {
        for(int i = 0; i < _width * _height; i++)
        {
            dest[i * 3    ] = components[i * 4 + 1];
            dest[i * 3 + 1] = components[i * 4 + 2];
            dest[i * 3 + 2] = components[i * 4 + 3];
        }
    }
}
