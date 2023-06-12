public class RenderContext extends Bitmap{
    public RenderContext(int width, int height)
    {
        super(width, height);
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
        ScanTriangle(minYVert, midYVert, maxYVert, minYVert.TriangleAreaTimesTwo(maxYVert, midYVert) >= 0);
    }

    private void ScanTriangle(Vertex minYVert, Vertex midYVert, Vertex maxYVert, boolean handedness) {//convert triangle = get handedness and lines
        Gradients gradients = new Gradients(minYVert, midYVert, maxYVert);
        Edge topToBottom = new Edge(gradients, minYVert, maxYVert, 0);
        Edge topToMiddle = new Edge(gradients, minYVert, midYVert, 0);
        Edge middleToBottom = new Edge(gradients, midYVert, maxYVert, 1);

        Edge left = topToBottom;
        Edge right = topToMiddle;
        if(handedness){
            Edge temp = left;
            left = right;
            right = temp;
        }

        int yStart = topToMiddle.GetYStart();
        int yEnd = topToMiddle.GetYEnd();
        for(int j = yStart; j < yEnd; j++){
            DrawScanLine(left, right, j);
            left.Step();
            right.Step();
        }

        left = topToBottom;
        right = middleToBottom;
        if(handedness){
            Edge temp = left;
            left = right;
            right = temp;
        }

        yStart = middleToBottom.GetYStart();
        yEnd = middleToBottom.GetYEnd();
        for(int j = yStart; j < yEnd; j++){
            DrawScanLine(left, right, j);
            left.Step();
            right.Step();
        }
    }

    private void DrawScanLine(Edge left, Edge right, int j){
        int xMin = (int)Math.ceil(left.GetX());
        int xMax = (int)Math.ceil(right.GetX());
        Vector4f minColor = left.GetColor();
        Vector4f maxColor = right.GetColor();

        float lerpAmt = 0.0f;
        float lerpStep = 1.0f/(float)(xMax-xMin);
        for (int i = xMin; i < xMax; i++){
            Vector4f color = minColor.Lerp(maxColor, lerpAmt);

            byte r = (byte)(color.GetX() * 255.0f + 0.5f);
            byte g = (byte)(color.GetY() * 255.0f + 0.5f);
            byte b = (byte)(color.GetZ() * 255.0f + 0.5f);

            DrawPixel(i, j, (byte)0xFF, b, g, r);
            lerpAmt += lerpStep;
        }
    }
}
