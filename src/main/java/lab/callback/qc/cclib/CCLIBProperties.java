package lab.callback.qc.cclib;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * CJSon properties项
 */
@Data
public class CCLIBProperties implements Serializable
{
    /* 分子轨道 */
    @Data
    public static class Track
    {
        @JSONField(name = "homo")
        private double homo;

        @JSONField(name = "gap")
        private double gap;

        @JSONField(name = "lumo")
        private double lumo;


    }

    /* 分子能量 */
    @Data
    public static class Energy
    {
        @JSONField(name = "free energy")
        private double freeEnergy;

        @JSONField(name = "alpha")
        private Track alpha;

        @JSONField(name = "beta")
        private Track beta;

        @JSONField(name = "total")
        private double total;
    }

    @JSONField(name = "charge")
    private int charge;

    @JSONField(name = "enthalpy")
    private double enthalpy;

    @JSONField(name = "entropy")
    private double entropy;


}
