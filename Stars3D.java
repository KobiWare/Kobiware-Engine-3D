public class Stars3D
{//this doesnt even make stars anymore, just a random triangle pretty much
    private final float _spread;
    private final float _speed;
    private final float _starX[];
    private final float _starY[];
    private final float _starZ[];
    public Stars3D(int numStars, float spread, float speed)
    {//struct storing the values of what to make
        _spread = spread;
        _speed = speed;

        _starX = new float[numStars]; //set _starX, _starY, _starZ length same as numStars
        _starY = new float[numStars];
        _starZ = new float[numStars];

        for(int i = 0; i < _starX.length; i++)
        {
            InitStar(i); //initialize the amount of stars that are in the X array, which corresponds back to numStars
        }
    }
    private void InitStar(int i)
    {
        _starX[i] = 2 * ((float)Math.random() - 0.5f) * _spread; //initialize the starX value as a random point subtracted by 0.5 and multiply by spread to give random nature
        _starY[i] = 2 * ((float)Math.random() - 0.5f) * _spread;
        _starZ[i] = ((float)Math.random() + 0.00001f) * _spread; //initialize the starZ value as a random point and add a infinitesimally small number to give a slight "depth" to it
    }
    public void UpdateAndRender(RenderContext target, float delta)//update the view point and then render it
    {
        final float tanHalfFOV = (float)Math.tan(Math.toRadians(90.0/2.0)); //get the tangent of the FOV/2 in radians
        target.Clear((byte)0x00); //set the target point (area star is rendering at) as white

        float halfWidth  = target.GetWidth()/2.0f; //get screen width/2
        float halfHeight = target.GetHeight()/2.0f;//get screen height/2
        int triangleBuilderCounter = 0;

        int x1 = 0;
        int y1 = 0;
        int x2 = 0;
        int y2 = 0;
        for(int i = 0; i < _starX.length; i++) //loop that repeats until at length of the _starX array
        {
            _starZ[i] -= delta * _speed; //_starZ index of i subtract by change multiplied by speed

            if(_starZ[i] <= 0) //if the depth is less than 0, initialize the star
            {
                InitStar(i);
            }

            int x = (int)((_starX[i]/(_starZ[i] * tanHalfFOV)) * halfWidth + halfWidth); //x point is equal to the _starX index divided by _starZ index multiplied by the tangent of half fov, multiplied by half width and adding half height
            int y = (int)((_starY[i]/(_starZ[i] * tanHalfFOV)) * halfHeight + halfHeight);
            //dividing by the _starZ index gives the star image the 3D effect to it, very important to the rendering

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