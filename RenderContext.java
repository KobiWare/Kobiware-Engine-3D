public class RenderContext extends Bitmap{
    private final int scanBuffer[];
    public RenderContext(int width, int height)
    {
        super(width, height);
        scanBuffer = new int[height * 2];
    }

    public void DrawScanBuffer(int yCord, int xMin, int xMax){
        scanBuffer[yCord*2] = xMin;
        scanBuffer[yCord*2+1] = xMax;
    }

    public void FillShape(int yMin, int yMax){//fill the connected vertex inside area
        for(int j = yMin; j < yMax; j++){
            int xMin = scanBuffer[j*2];
            int xMax = scanBuffer[j*2+1];//always minimum x plus 1 in order to make it just a singular pixel

            for(int i = xMin; i < xMax; i++){
                DrawPixel(i, j, (byte)0xFF, (byte)0xFF, (byte)0xFF, (byte)0xFF); //draw the pixel with all white on the inside
            }
        }
    }

    public void FillTriangle(Vertex v1, Vertex v2, Vertex v3){//fill a triangle on the inside with solid pixels
        Matrix4f screenSpaceTransform = new Matrix4f().InitScreenSpaceTransform(GetWidth()/2, GetHeight()/2);
        Vertex minYVert = v1.Transform(screenSpaceTransform).PerspectiveDivide();
        Vertex midYVert = v2.Transform(screenSpaceTransform).PerspectiveDivide();
        Vertex maxYVert = v3.Transform(screenSpaceTransform).PerspectiveDivide();

        if(maxYVert.GetY() < midYVert.GetY())//following if statements are just to switch around the vertices to be ordered correctly
        {
            Vertex temp = maxYVert;
            maxYVert = midYVert;
            midYVert = temp;
        }
        if(midYVert.GetY() < minYVert.GetY())
        {
            Vertex temp = midYVert;
            midYVert = minYVert;
            minYVert = temp;
        }
        if(maxYVert.GetY() < midYVert.GetY())
        {
            Vertex temp = maxYVert;
            maxYVert = midYVert;
            midYVert = temp;
        }
        float area = minYVert.TriangleAreaTimesTwo(maxYVert, midYVert); //area is actually of a square but i dont care it doesnt change anything seeing as it doesnt need the triangle area
        int handedness = area >= 0 ? 1 : 0;

        ScanConvertTriangle(minYVert, midYVert, maxYVert, handedness);
        FillShape((int)minYVert.GetY(), (int)maxYVert.GetY()); //fill the shape in all pixels
    }

    public void ScanConvertTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, int handedness) {//convert triangle = get handedness and lines
        ScanConvertLine(minYVert, maxYVert, 0 + handedness);
        ScanConvertLine(minYVert, midYVert, 1 - handedness);
        ScanConvertLine(midYVert, maxYVert, 1 - handedness);
    }

    private void ScanConvertLine(Vertex minYVert, Vertex maxYVert, int whichSide){//get line distance
        int yStart = (int)minYVert.GetY();
        int yEnd = (int)maxYVert.GetY();
        int xStart = (int)minYVert.GetX();
        int xEnd = (int)maxYVert.GetX();

        int yDist = yEnd - yStart;
        int xDist = xEnd - xStart;

        if(yDist <= 0){
            return;
        }

        float xStep = (float)xDist/(float)yDist;
        float curX = (float)xStart;

        for(int j = yStart; j < yEnd; j++){
            scanBuffer[j * 2 + whichSide] = (int)curX;
            curX += xStep;
        }
    }
}
