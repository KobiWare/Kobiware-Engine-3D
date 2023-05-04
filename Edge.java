public class Edge {
    private float _x;
    private float _xStep;
    private int _yStart;
    private int _yEnd;

    public float GetX() {return _x;}
    public int GetYStart() {return _yStart;}
    public int GetYEnd() {return _yEnd;}

    public Edge(Vertex minYVert, Vertex maxYVert){
        _yStart = (int)Math.ceil(minYVert.GetY());
        _yEnd = (int)Math.ceil(maxYVert.GetY());

        float yDist = maxYVert.GetY() - minYVert.GetY();
        float xDist = maxYVert.GetX() - minYVert.GetX();

        _xStep = (float)xDist/(float)yDist;
        float yPrestep = _yStart - minYVert.GetY();
        _x = minYVert.GetX() + yPrestep * _xStep;
    }

    public void Step() {
        _x += _xStep;
    }
}
