package lab.callback.qc.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * 结果表格渲染器
 */
public class ResultTableCellRender extends DefaultTableCellRenderer
{
//    rgba(236, 240, 241,1.0)

    private static final Color ODD_ROW_COLOR = new Color(236.0f/255, 240.0f/255, 241.0f/255, 1.0f);

    public ResultTableCellRender()
    {
        setHorizontalAlignment(SwingConstants.CENTER);
    }
}
