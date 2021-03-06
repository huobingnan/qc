package lab.callback.qc.view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lab.callback.qc.cclib.CCLIBCJson;
import lab.callback.qc.cclib.ElementsTable;
import lab.callback.qc.util.ColoredTableCellRender;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;

public class DetailDialog extends JDialog
{

    private static volatile DetailDialog instance = null; /* 实例 */

    private static final String[] COLUMN = new String[]
            {
                    "元素", "序号", "电性", "遮挡"
            };

    private static final Font TABLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        bottomPane = new JPanel();
        bottomPane.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(bottomPane, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("保存");
        bottomPane.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("关闭");
        bottomPane.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainPane = new JPanel();
        mainPane.setLayout(new GridLayoutManager(2, 6, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(mainPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lbl1 = new JLabel();
        lbl1.setText("文件名");
        mainPane.add(lbl1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JSON_File_Name_Field = new JTextField();
        JSON_File_Name_Field.setEditable(false);
        mainPane.add(JSON_File_Name_Field, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lbl2 = new JLabel();
        lbl2.setText("电性");
        mainPane.add(lbl2, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Electron_Field = new JTextField();
        Electron_Field.setEditable(false);
        mainPane.add(Electron_Field, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        tablePane = new JPanel();
        tablePane.setLayout(new BorderLayout(0, 0));
        mainPane.add(tablePane, new GridConstraints(1, 0, 1, 6, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        Shade_Button = new JButton();
        Shade_Button.setText("遮挡");
        mainPane.add(Shade_Button, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Unshade_Button = new JButton();
        Unshade_Button.setText("取消遮挡");
        mainPane.add(Unshade_Button, new GridConstraints(0, 5, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return contentPane;
    }


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel bottomPane;
    private JPanel mainPane;
    private JTextField JSON_File_Name_Field;
    private JTextField Electron_Field;
    private JLabel lbl1;
    private JLabel lbl2;
    private JPanel tablePane;
    private JButton Shade_Button;
    private JButton Unshade_Button;

    /*-------------------------- Custom UI -------------------*/
    private DefaultTableModel Detail_Table_Model = new DefaultTableModel(COLUMN, 0);


    private JTable Detail_Table = new JTable(Detail_Table_Model)
    {

        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };

    private JScrollPane Detail_Table_Scroll_Pane = new JScrollPane(Detail_Table);

    /*----------------------- properties -----------------------*/
    CCLIBCJson JSON = null;

    public DetailDialog()
    {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(750, 500);
        setResizable(false);
        setVisible(false);
        setTitle("原子遮挡面板");

        /* 设置居中 */
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        int w = getWidth();
        int h = getHeight();
        setLocation((width - w) / 2, (height - h) / 2);

        buttonOK.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        });


        Shade_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[] selectedRows = Detail_Table.getSelectedRows();
                if (selectedRows != null && selectedRows.length > 0) {
                    for (int row : selectedRows) {
                        Detail_Table_Model.setValueAt(true, row, 3);
                    }
                    Detail_Table.revalidate();
                }
            }
        });

        Unshade_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int[] selectedRows = Detail_Table.getSelectedRows();
                if (selectedRows != null && selectedRows.length > 0) {
                    for (int row : selectedRows) {
                        Detail_Table_Model.setValueAt(false, row, 3);
                    }
                    Detail_Table.revalidate();
                }
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void customUI()
    {
        Shade_Button.setEnabled(false);
        Unshade_Button.setEnabled(false);

        tablePane.add(Detail_Table_Scroll_Pane, BorderLayout.CENTER);
//        String columnName = Detail_Table_Model.getColumnName(3);
//        Detail_Table.getColumn(columnName).setCellRenderer(new ColoredTableCellRender());
        Detail_Table.setFont(TABLE_FONT);
        Detail_Table.setDefaultRenderer(Object.class, new ColoredTableCellRender());

        Detail_Table.getSelectionModel().addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                boolean status = Detail_Table.getSelectedRows() != null && Detail_Table.getSelectedRows().length > 0;
                if (status) {
                    Shade_Button.setEnabled(true);
                    Unshade_Button.setEnabled(true);
                } else {
                    Shade_Button.setEnabled(false);
                    Unshade_Button.setEnabled(false);
                }
            }
        });

    }

    private void onOK()
    {
        final int count = Detail_Table_Model.getRowCount();
        for (int i = 0; i < count; ++i) {
            boolean shade = (Boolean) Detail_Table_Model.getValueAt(i, 3);
            JSON.getShadeTable()[i] = shade;
        }
        dispose();
    }

    private void onCancel()
    {
        // add your code here if necessary
        dispose();
    }

    public void showDialog(String name, CCLIBCJson cJson)
    {
        if (cJson == null || name == null) return;
        JSON = cJson;
        JSON_File_Name_Field.setText(name);
        Electron_Field.setText(
                String.valueOf(cJson.getProperties().getCharge())
        );
        boolean[] shadeTable = cJson.getShadeTable();
        int[] coreElectrons = cJson.getAtoms().getCoreElectrons();
        int[] number = cJson.getAtoms().getElements().getNumber();

        Detail_Table_Model.setRowCount(0);
        for (int i = 0; i < number.length; ++i) {
            Detail_Table_Model.addRow(
                    new Object[]{
                            ElementsTable.Z(number[i]), i + 1, coreElectrons[i], shadeTable[i]
                    }
            );
        }
        setVisible(true);
    }

    public static DetailDialog getInstance()
    {
        if (instance == null) {
            synchronized (DetailDialog.class) {
                if (instance == null) {
                    instance = new DetailDialog();
                    instance.customUI();
                }
            }
        }
        return instance;
    }

}
