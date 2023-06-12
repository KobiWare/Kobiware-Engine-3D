public class Edge {
    private float _x;
    private float _xStep;
    private int _yStart;
    private int _yEnd;
    private Vector4f _color;
    private Vector4f _colorStep;

    public Vector4f GetColor() {return _color;}
    public float GetX() {return _x;}
    public int GetYStart() {return _yStart;}
    public int GetYEnd() {return _yEnd;}

    public Edge(Gradients gradients, Vertex minYVert, Vertex maxYVert, int minYVertIndex){
        _yStart = (int)Math.ceil(minYVert.GetY());
        _yEnd = (int)Math.ceil(maxYVert.GetY());

        float yDist = maxYVert.GetY() - minYVert.GetY();
        float xDist = maxYVert.GetX() - minYVert.GetX();

        _xStep = (float)xDist/(float)yDist;
        float yPrestep = _yStart - minYVert.GetY();
        _x = minYVert.GetX() + yPrestep * _xStep;
        float xPrestep = _x - minYVert.GetX();

        _color = gradients.GetColor(minYVertIndex).Add(gradients.GetColorYStep().Mul(yPrestep)).Add(gradients.GetColorXStep().Mul(xPrestep));
        _colorStep = gradients.GetColorYStep().Add(gradients.GetColorXStep().Mul(_xStep));
    }

    public void Step() {
        _x += _xStep;
        _color = _color.Add(_colorStep);
    }
}
