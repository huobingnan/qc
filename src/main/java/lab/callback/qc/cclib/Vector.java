package lab.callback.qc.cclib;

public class Vector
{
    public static double dot(double[] v1, double[] v2)
    {
        double res = 0.0D;
        if (v1 != null && v2 != null && v1.length == v2.length) {
            for (int i = 0; i < v1.length; ++i) {
                res += v1[i] * v2[i];
            }
        }
        return res;
    }


    public static double normal(double[] v)
    {
        double res = 0.0D;
        if (v != null && v.length > 0) {
            for (double s : v) res += Math.pow(s, 2);
            res = Math.sqrt(res);
        }
        return res;
    }
}
