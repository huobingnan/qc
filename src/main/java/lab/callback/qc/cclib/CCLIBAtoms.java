package lab.callback.qc.cclib;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

@Data
public class CCLIBAtoms implements Serializable
{


    @Data
    public static class Elements
    {
        @JSONField(name = "number")
        private int[] number;

        @JSONField(name = "atom count")
        private int atomCount;

        @JSONField(name = "heavy atom count")
        private int heavyAtomCount;

    }


    @JSONField(name = "elements")
    private Elements elements;

    @JSONField(name = "core electrons")
    private int[] coreElectrons;
}
