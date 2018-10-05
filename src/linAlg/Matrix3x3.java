package linAlg;

import linAlg.Vector2.Vector2;
import linAlg.Vector3.Vector3;

public class Matrix3x3 {
    private double m[];
    public Matrix3x3()
    { //еденичная 3 на 3;
        m = new double[9];
    }

    public double get(int i)
    {
        return m[i];
    }

    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < 3; i++)
        {
           string.append(m[i]);
           string.append(' ');
           string.append(m[i + 3]);
           string.append(' ');
           string.append(m[i + 6]);
           string.append('\n');
        }
        return string.toString();
    }

    public Matrix3x3 dot(Matrix3x3 m2)
    {
        return dot(this, m2);
    }

    public Vector3 dot(Vector3 v2)
    {
        return dot(this, v2);
    }

    public Vector2 dot(Vector2 v2)
    {
        return dot(this, v2);
    }

    public static Matrix3x3 dot(Matrix3x3 m1, Matrix3x3 m2)
    {
        //перемножение
        Matrix3x3 matrix = new Matrix3x3();
        // Fisrt Column
        matrix.m[0] = m1.m[0] * m2.m[0] + m1.m[3] * m2.m[1] + m1.m[6] * m2.m[2];
        matrix.m[1] = m1.m[1] * m2.m[0] + m1.m[4] * m2.m[1] + m1.m[7] * m2.m[2];
        matrix.m[2] = m1.m[2] * m2.m[0] + m1.m[5] * m2.m[1] + m1.m[8] * m2.m[2];
        // Second Column
        matrix.m[3] = m1.m[0] * m2.m[3] + m1.m[3] * m2.m[4] + m1.m[6] * m2.m[5];
        matrix.m[4] = m1.m[1] * m2.m[3] + m1.m[4] * m2.m[4] + m1.m[7] * m2.m[5];
        matrix.m[5] = m1.m[2] * m2.m[3] + m1.m[5] * m2.m[4] + m1.m[8] * m2.m[5];
        // Third Column
        matrix.m[6] = m1.m[0] * m2.m[6] + m1.m[3] * m2.m[7] + m1.m[6] * m2.m[8];
        matrix.m[7] = m1.m[1] * m2.m[6] + m1.m[4] * m2.m[7] + m1.m[7] * m2.m[8];
        matrix.m[8] = m1.m[2] * m2.m[6] + m1.m[5] * m2.m[7] + m1.m[8] * m2.m[8];
        return matrix;
    }
    public static Vector3 dot(Matrix3x3 m1, Vector3 m2)
    {
        //матрица на вектор3
        Vector3 result = Vector3.getVector3();
        result.x = m1.m[0] * m2.x + m1.m[3] * m2.y + m1.m[6] * m2.z;
        result.y = m1.m[1] * m2.x + m1.m[4] * m2.y + m1.m[7] * m2.z;
        result.z = m1.m[2] * m2.x + m1.m[5] * m2.y + m1.m[8] * m2.z;
        return result;
    }

    public static Vector2 dot(Matrix3x3 m1, Vector2 m2)
    {
        //матрица на вектор2, не забудь поделить

        Vector3 v = Vector3.getVector3(m2.x, m2.y, 1);
        v = dot(m1, v);
        return Vector2.getVector2(v.x/ v.z, v.y/v.z);
    }

    public Matrix3x3 Transpose()
    {
        Matrix3x3 matrix = new Matrix3x3();
        matrix.m[0] = m[0];
        matrix.m[1] = m[3];
        matrix.m[2] = m[6];
        matrix.m[3] = m[1];
        matrix.m[4] = m[4];
        matrix.m[5] = m[7];
        matrix.m[6] = m[2];
        matrix.m[7] = m[5];
        matrix.m[8] = m[8];
        return matrix;
    }

    public static Matrix3x3 Translate(Vector2 pos)
    {
        // 1 0 x
        // 0 1 y
        // 0 0 1
        Matrix3x3 matrix = new Matrix3x3();
        matrix.m[6] = pos.x;
        matrix.m[7] = pos.y;
        return matrix;
    }
    public static Matrix3x3 Rotate(double rotation)
    {
        // cos sin 0
        //-sin cos 0
        //  0   0  1
        rotation = rotation / (720 * Math.PI);
        Matrix3x3 matrix = new Matrix3x3();
        matrix.m[0] = Math.cos(rotation);
        matrix.m[4] = matrix.m[0];
        matrix.m[3] = Math.sin(rotation);
        matrix.m[1] = -matrix.m[3];
        return matrix;
    }
    public static Matrix3x3 Scale(double scale)
    {
        // s 0 0
        // 0 s 0
        // 0 0 1
        Matrix3x3 matrix = new Matrix3x3();
        matrix.m[0] = scale;
        matrix.m[4] = scale;
        return matrix;
    }
    public static Matrix3x3 TRS(Vector2 pos, double rotation, double scale)
    {
        //fout << Matrix4x4::Scale(s);
        //fout << Matrix4x4::Rotate(q);
        //fout << Matrix4x4::Translate(pos);
        return Matrix3x3.Translate(pos).dot(Matrix3x3.Rotate(rotation)).dot(Matrix3x3.Scale(scale));
    }
}
