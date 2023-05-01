public class Stars3D
{
    private final float _spread;
    private final float _speed;
    private final float _starX[];
    private final float _starY[];
    private final float _starZ[];
    public Stars3D(int numStars, float spread, float speed)
    {
        _spread = spread;
        _speed = speed;

        _starX = new float[numStars];
        _starY = new float[numStars];
        _starZ = new float[numStars];

        for(int i = 0; i < _starX.length; i++)
        {
            InitStar(i);
        }
    }
    private void InitStar(int i)
    {
        _starX[i] = 2 * ((float)Math.random() - 0.5f) * _spread;
        _starY[i] = 2 * ((float)Math.random() - 0.5f) * _spread;
        _starZ[i] = ((float)Math.random() + 0.00001f) * _spread;
    }
    public void UpdateAndRender(RenderContext target, float delta)
    {
        final float tanHalfFOV = (float)Math.tan(Math.toRadians(90.0/2.0));
        target.Clear((byte)0x00);

        float halfWidth  = target.GetWidth()/2.0f;
        float halfHeight = target.GetHeight()/2.0f;
        int triangleBuilderCounter = 0;

        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        for(int i = 0; i < _starX.length; i++)
        {
            _starZ[i] -= delta * _speed;

            if(_starZ[i] <= 0)
            {
                InitStar(i);
            }

            int x = (int)((_starX[i]/(_starZ[i] * tanHalfFOV)) * halfWidth + halfWidth);
            int y = (int)((_starY[i]/(_starZ[i] * tanHalfFOV)) * halfHeight + halfHeight);

            if(x < 0 || x >= target.GetWidth() ||
                    (y < 0 || y >= target.GetHeight()))
            {
                InitStar(i);
                continue;
            }
            triangleBuilderCounter++;
            if(triangleBuilderCounter == 1)
            {
                x1 = x;
                y1 = y;
            }
            else if(triangleBuilderCounter == 2)
            {
                x2 = x;
                y2 = y;
            }
            else if(triangleBuilderCounter == 3)
            {
                triangleBuilderCounter = 0;
                //Vertex v1 = new Vertex(x1, y1);
                //Vertex v2 = new Vertex(x2, y2);
                //Vertex v3 = new Vertex(x, y);

                //target.FillTriangle(v1, v2, v3);
            }
        }
    }
}