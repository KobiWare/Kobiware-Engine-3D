public class Vertex {
    private Vector4f _pos;
    public float GetX() {return _pos.GetX();}
    public float GetY() {return _pos.GetY();}

    public Vertex(float x, float y, float z){
        _pos = new Vector4f(x, y, z, 1);
    }

    public Vertex Transform(Matrix4f transform){
        return new Vertex(transform.Transform(_pos));
    }

    public Vertex PerspectiveDivide() {
        return new Vertex(new Vector4f(_pos.GetX()/_pos.GetW(), _pos.GetY()/_pos.GetW(), _pos.GetZ()/_pos.GetW(), _pos.GetW()));
    }

    public Vertex(Vector4f pos){
        _pos = pos;
    }

    public float TriangleAreaTimesTwo(Vertex b, Vertex c)
    {
        float x1 = b.GetX() - _pos.GetX();
        float y1 = b.GetY() - _pos.GetY();

        float x2 = c.GetX() - _pos.GetX();
        float y2 = c.GetY() - _pos.GetY();

        return (x1 * y2 - x2 * y1);
    }
}
