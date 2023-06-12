public class Gradients
{
    private Vector4f[] _color;
    private Vector4f _colorXStep;
    private Vector4f _colorYStep;

    public Vector4f GetColor(int loc) { return _color[loc]; }
    public Vector4f GetColorXStep() { return _colorXStep; }
    public Vector4f GetColorYStep() { return _colorYStep; }

    public Gradients(Vertex minYVert, Vertex midYVert, Vertex maxYVert)
    {
        _color = new Vector4f[3];

        _color[0] = minYVert.GetColor();
        _color[1] = midYVert.GetColor();
        _color[2] = maxYVert.GetColor();

        float oneOverdX = 1.0f /
                (((midYVert.GetX() - maxYVert.GetX()) *
                        (minYVert.GetY() - maxYVert.GetY())) -
                        ((minYVert.GetX() - maxYVert.GetX()) *
                                (midYVert.GetY() - maxYVert.GetY())));

        float oneOverdY = -oneOverdX;

        _colorXStep =
                (((_color[1].Sub(_color[2])).Mul(
                        (minYVert.GetY() - maxYVert.GetY()))).Sub(
                        ((_color[0].Sub(_color[2])).Mul(
                                (midYVert.GetY() - maxYVert.GetY()))))).Mul(oneOverdX);

        _colorYStep =
                (((_color[1].Sub(_color[2])).Mul(
                        (minYVert.GetX() - maxYVert.GetX()))).Sub(
                        ((_color[0].Sub(_color[2])).Mul(
                                (midYVert.GetX() - maxYVert.GetX()))))).Mul(oneOverdY);
    }
}