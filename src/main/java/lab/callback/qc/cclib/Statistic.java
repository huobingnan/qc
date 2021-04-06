package lab.callback.qc.cclib;

import java.util.ArrayList;

public class Statistic
{

    public static double AVG(double[] sample)
    {
        double res = 0.0D;
        if (sample != null && sample.length > 0) {
            for (double s : sample) res += s;
            res /= sample.length;
        }
        return res;
    }

    public static double SD(double[] sample, double avg)
    {
        double res = 0.0D;

        if (sample != null && sample.length > 0) {
            for (double s : sample) {
                res += (Math.pow(s-avg, 2));
            }
            res = Math.sqrt(res) / sample.length;
        }
        return res;
    }

    public static double SD(double[] sample)
    {
        double res = 0.0D;
        if (sample != null && sample.length > 0) {
            double avg = 0.0D;
            for (double s : sample) avg += s;
            avg /= sample.length;
            res = SD(sample, avg);
        }
        return res;
    }



}
