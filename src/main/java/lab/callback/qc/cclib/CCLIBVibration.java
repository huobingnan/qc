package lab.callback.qc.cclib;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * CCLIB编译的CJson文件中的vibration项
 */
@Data
public class CCLIBVibration implements Serializable
{
    /* 强度 */
    @Data
    public static class Intensities
    {
        @JSONField(name = "IR")
        private double[] IR; /* 红外强度 */

        @JSONField(name = "raman")
        private double[] raman; /* 拉曼强度 */
    }

    @JSONField(name = "displacement")
    private double[][][] displacement;

    @JSONField(name = "frequencies")
    private double[] frequencies;

    @JSONField(name = "intensities")
    private Intensities intensities;

    @JSONField(name = "vibration symmetry")
    private String[] symmetry;

}
