package lab.callback.qc.cclib;


public class Similarity
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


    private static double[] perform4Row(double[][] m1, double[][] m2) throws IllegalArgumentException
    {
        double[] res = null;
        if (m1 != null && m2 != null && m1.length == m2.length) {
            res = new double[m1.length];

            for (int i = 0; i < m1.length; ++i) {

                final double n1 = normal(m1[i]);
                final double n2 = normal(m2[i]);

                if (n1 == 0 && n2 == 0) {
                    /* 零向量 */
                    res[i] = 1;
                } else if (n1 == 0) {
                    /* 有零向量参与， 如果一方由零向量参与，那么对于波动会十分敏感 */
                    res[i] = 0;
                } else if (n2 == 0) {
                    /* 有零向量参与， 如果一方由零向量参与，那么对于波动会十分敏感 */
                    res[i] = 0;
                } else {
                    res[i] = dot(m1[i], m2[i]) / (n1 * n2);
                }

            }
        } else {
            System.out.println("Dimension not equals");
            throw new IllegalArgumentException("Dimension not equals");
        }
        return res;
    }

    /* 根据shade表，构造 */
    private static void shadeFilter(double[][] dst, double[][] src, boolean[] shadeTable)
    {
        int index = 0;

        for (int i = 0; i < shadeTable.length; ++i) {

            if (!shadeTable[i]) {
                /* Copy数组 */
                System.arraycopy(src[index], 0, dst[index], 0, 3);
                index++;
            }
        }
    }

    /* 获取真实的length */
    public static int getTrueLength(boolean[] shadeTable)
    {
        int res = 0;
        for (boolean shade : shadeTable) if (!shade) res ++;
        return res;
    }

    public static SimilarityTable perform(CCLIBCJson json1, CCLIBCJson json2) throws IllegalArgumentException
    {
        if (json1 == null) throw new IllegalArgumentException("参数错误，json1不能为空");
        if (json2 == null) throw new IllegalArgumentException("参数错误, json2不能为空");

        /* 获取震动分布 */
        double[][][] displacement1 = json1.getVibrations().getDisplacement();
        double[][][] displacement2 = json2.getVibrations().getDisplacement();



        final boolean[] shadeTable_1 = json1.getShadeTable();
        final boolean[] shadeTable_2 = json2.getShadeTable();
        final int trueLength = getTrueLength(shadeTable_1);

        final double[][] BASE = new double[getTrueLength(shadeTable_1)][3];
        final double[][] SAMPLE = new double[getTrueLength(shadeTable_2)][3];

        SimilarityTable table = new SimilarityTable(displacement1.length, displacement2.length,
                trueLength);

        for (int i = 0; i < displacement1.length; ++i) {
            shadeFilter(BASE, displacement1[i], shadeTable_1);
            for (int j = 0; j < displacement2.length; ++j) {
               shadeFilter(SAMPLE, displacement2[j], shadeTable_2);
               table.setValueAt(i, j, perform4Row(BASE, SAMPLE)); /* 对比相似度 */
            }
        }
        return table;
    }
}
