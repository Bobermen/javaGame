package game;

public class Matrix3x3 {
    private:
    float m[16];
    public:
    Matrix4x4()
    { //еденичная 3 на 3
        m[0] = m[5] = m[10] = m[15] = 1.0;
        m[1] = m[2] = m[3] = m[4] = 0.0;
        m[6] = m[7] = m[8] = m[9] = 0.0;
        m[11] = m[12] = m[13] = m[14] = 0.0;
    }
    float& operator[](int i)
    {
        return m[i];
    }
    friend ostream& operator<< (ostream& out, Matrix4x4 m)
    {
        for (int i = 0; i < 4; i++)
        {
            out << m[0+i] << " " << m[4+i] << " " << m[8+i] << " " << m[12+i] << endl;
        }
        return out;
    }
    friend Matrix4x4 operator* (Matrix4x4& m1, Matrix4x4& m2)
    {
        //перемножение
        Matrix4x4 result;
        // Fisrt Column
        result[0] = m1[0] * m2[0] + m1[4] * m2[1] + m1[8] * m2[2] + m1[12] * m2[3];
        result[1] = m1[1] * m2[0] + m1[5] * m2[1] + m1[9] * m2[2] + m1[13] * m2[3];
        result[2] = m1[2] * m2[0] + m1[6] * m2[1] + m1[10] * m2[2] + m1[14] * m2[3];
        result[3] = m1[3] * m2[0] + m1[7] * m2[1] + m1[11] * m2[2] + m1[15] * m2[3];

        // Second Column
        result[4] = m1[0] * m2[4] + m1[4] * m2[5] + m1[8] * m2[6] + m1[12] * m2[7];
        result[5] = m1[1] * m2[4] + m1[5] * m2[5] + m1[9] * m2[6] + m1[13] * m2[7];
        result[6] = m1[2] * m2[4] + m1[6] * m2[5] + m1[10] * m2[6] + m1[14] * m2[7];
        result[7] = m1[3] * m2[4] + m1[7] * m2[5] + m1[11] * m2[6] + m1[15] * m2[7];

        // Third Column
        result[8] = m1[0] * m2[8] + m1[4] * m2[9] + m1[8] * m2[10] + m1[12] * m2[11];
        result[9] = m1[1] * m2[8] + m1[5] * m2[9] + m1[9] * m2[10] + m1[13] * m2[11];
        result[10] = m1[2] * m2[8] + m1[6] * m2[9] + m1[10] * m2[10] + m1[14] * m2[11];
        result[11] = m1[3] * m2[8] + m1[7] * m2[9] + m1[11] * m2[10] + m1[15] * m2[11];

        // Fourth Column
        result[12] = m1[0] * m2[12] + m1[4] * m2[13] + m1[8] * m2[14] + m1[12] * m2[15];
        result[13] = m1[1] * m2[12] + m1[5] * m2[13] + m1[9] * m2[14] + m1[13] * m2[15];
        result[14] = m1[2] * m2[12] + m1[6] * m2[13] + m1[10] * m2[14] + m1[14] * m2[15];
        result[15] = m1[3] * m2[12] + m1[7] * m2[13] + m1[11] * m2[14] + m1[15] * m2[15];

        return result;
    }
    friend Vector3 operator*(Matrix4x4 m1, Vector3 m2)
    {
        //матрица на вектор3
        Vector4 result;

        return result;
    }
    friend Vector2 operator*(Matrix4x4 m1, Vector2 m2)
    {
        //матрица на вектор2, не забудь поделить
        Vector3 v;
        return new Vector2(v.x/ v.z, v.y/v.z);
    }
    Matrix4x4 Transpose()
    {
        //транспонировать, вернуть новую

    }

    static Matrix4x4 Translate(Vector2 pos)
    {
        // 1 0 x
        // 0 1 y
        // 0 0 1

        return matrix;
    }
    static Matrix4x4 Rotate(double rotation)
    {
        // cos sin 0
        //-sin cos 0
        //  0   0  1
        Matrix4x4 matrix;

        return matrix;
    }
    static Matrix4x4 Scale(double scale)
    {
        // s 0 0
        // 0 s 0
        // 0 0 1
        return matrix;
    }
    static Matrix3x3 TRS(Vector2 pos, double rotation, double scale)
    {
        //fout << Matrix4x4::Scale(s);
        //fout << Matrix4x4::Rotate(q);
        //fout << Matrix4x4::Translate(pos);
        return Matrix4x4::Translate(pos) * Matrix4x4::Rotate(q) * Matrix4x4::Scale(s);
    }
}
