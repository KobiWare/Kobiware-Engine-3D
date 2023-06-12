import java.util.Vector;

public class Vertex {
    private Vector4f _pos;
    private Vector4f _color;
    public float GetX() {return _pos.GetX();} //get the X value of a vertex
    public float GetY() {return _pos.GetY();} //get the Y value of a vertex

    public Vertex(float x, float y, float z){
        _pos = new Vector4f(x, y, z, 1);
    } //make a "Vertex" of the XY value of point, Z value is the hypothetical depth

    public Vertex Transform(Matrix4f transform){
        return new Vertex(transform.Transform(_pos), _color);
    }

    public Vertex PerspectiveDivide() {
        return new Vertex(new Vector4f(_pos.GetX()/_pos.GetW(), _pos.GetY()/_pos.GetW(), _pos.GetZ()/_pos.GetW(), _pos.GetW()), _color); //divide each point by this hypothetical third dimension to give a 3D look to the image
    }

    public Vector4f GetColor() {return _color;}

    public Vertex(Vector4f pos, Vector4f color){
        _pos = pos;
        _color = color;
    }

    public float TriangleAreaTimesTwo(Vertex b, Vertex c) //technically gets the area of a square, too lazy to fix because breaks rendering code
    {//editing this to be the area of a triangle WILL break code, do not change
        //rendering does not even need area anyway
        float x1 = b.GetX() - _pos.GetX();
        float y1 = b.GetY() - _pos.GetY();

        float x2 = c.GetX() - _pos.GetX();
        float y2 = c.GetY() - _pos.GetY();

        return (x1 * y2 - x2 * y1);
    }
}
