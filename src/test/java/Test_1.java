import org.junit.Test;

import java.util.Arrays;

public class Test_1
{

    @Test
    public void test1()
    {
        int[][] a = new int[][]
                {
                        {1,2,3,4,5},
                        {6,7,8,9,10}
                };
        int [] b = new int[5];
        System.arraycopy(a[0], 0, b, 0, a[0].length);
        System.out.println(Arrays.toString(b));
    }
}
