package lab.callback.qc.cclib;


public class SimilarityTable
{
    private double[][][] similarityTable;
    private int size;
    private int number;
    private int x;
    private int y;

    public SimilarityTable(int size, int number)
    {
        similarityTable = new double[size][size][number];
        this.size = size;
        this.number = number;
    }

    public SimilarityTable(int x1, int x2, int number)
    {
        similarityTable = new double[x1][x2][number];
        x = x1;
        y = x2;
        this.number = number;
    }


    public void setValueAt(int x, int y, int z, double r)
    {
        similarityTable[x][y][z] = r;
    }

    public void setValueAt(int x, int y, double[] r)
    {
        similarityTable[x][y] = r;
    }

    public double getValueAt(int x, int y, int z)
    {
        return similarityTable[x][y][z];
    }

    public double[] getValueAt(int x, int y)
    {
        return similarityTable[x][y];
    }

    public int size() { return size; }

    public int number() { return number; }

    public int x(){return x;}
    public int y(){return y;}
}
