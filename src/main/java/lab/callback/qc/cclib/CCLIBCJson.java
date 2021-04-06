package lab.callback.qc.cclib;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 由CCLIB导出的CJson格式数据
 */
@Data
public class CCLIBCJson implements Serializable
{
    @JSONField(name = "chemical json")
    private Integer jsonVersion;

    @JSONField(name = "properties")
    private CCLIBProperties properties;

    @JSONField(name = "vibrations")
    private CCLIBVibration vibrations;

    @JSONField(name = "atoms")
    private CCLIBAtoms atoms;

    /* 原子遮挡表 */
    private boolean[] shadeTable;
}
