package game;

public class Vector3 {
    class Vector3
    {
        float *v[3];
        public:
        float x, y, z;
        Vector3(float a, float b, float c) { x = a; y = b; z = c; v[0] = &x; v[1] = &y; v[2] = &z; }
        Vector3() : Vector3(0, 0, 0) {}
        float& operator[](int i) { return *v[i]; }
        friend Vector3 operator+(Vector3& a, Vector3& b) { return Vector3(a.x + b.x, a.y + b.y, a.z + b.z); }
        friend Vector3 operator*(Vector3& a, float b) { return Vector3(a.x*b, a.y*b, a.z*b); }
        friend Vector3 operator*(float b, Vector3& a) { return Vector3(a.x*b, a.y*b, a.z*b); }
        friend Vector3 operator/(Vector3& a, float b) { return a*(1/b); }
        Vector3 operator+() { return *this; }
        Vector3 operator-() { return *this * -1; }
        friend Vector3 operator-(Vector3& a, Vector3& b) { return a + -b; }

        static Vector3 Cross(Vector3& a, Vector3& b) { return Vector3(a.y * b.z - b.y * a.z, a.x * b.z - b.x * a.z, a.x * b.y - b.x * a.y); }
        static float Dot(Vector3& a, Vector3& b) { return a.x*b.x + a.y*b.y + a.z*b.z; }
        float Magnitude() { return sqrt(x*x + y*y + z*z); }
        Vector3 Normalize() { float m = this->Magnitude();  return *this *(1/m); }
        friend ostream& operator<< (ostream& out, Vector3& v)
        {
            out << v.x << " " << v.y << " " << v.z << endl;
            return out;
        }
        friend istream& operator>> (istream& in, Vector3& v)
        {
            in >> v.x >> v.y >> v.z;
            return in;
        }
    };
}
