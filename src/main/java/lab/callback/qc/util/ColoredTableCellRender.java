package lab.callback.qc.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ColoredTableCellRender extends DefaultTableCellRenderer
{
    private static final int SHADE_VALUE_COLUMN_INDEX = 3;
//    rgb(164, 176, 190)
//    rgba(164, 176, 190,1.0)
    private static final Color SHADE_COLOR = new Color(0.64f, 0.69f, 0.75f, 1.0f);
//    rgba(255, 255, 255,1.0)
    private static final Color ACTIVE_COLOR = new Color(1.0f, 1.0f, 1.0f, 1.0f);

    public ColoredTableCellRender()
    {
        super();
        setHorizontalAlignment(SwingConstants.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus,
                                                   int row, int column)
    {
        if ((Boolean) table.getValueAt(row, SHADE_VALUE_COLUMN_INDEX)) {
            setBackground(SHADE_COLOR);
        } else {
            setBackground(ACTIVE_COLOR);
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
