package lab.callback.qc.view;

import com.alibaba.fastjson.JSON;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import lab.callback.qc.cclib.CCLIBCJson;
import lab.callback.qc.cclib.Similarity;
import lab.callback.qc.cclib.SimilarityTable;
import lab.callback.qc.cclib.Statistic;
import lab.callback.qc.util.MouseClickAdapter;
import lab.callback.qc.util.ResultTableCellRender;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ThreadPoolExecutor;

public class MainView
{

    private static volatile MainView instance = null;
    private static final String[] SIMILARITY_TABLE_COLUMN = new String[]
            {
                    "JSON1", "能量", "JSON2", "能量", "相似度", "标准差", "能量差"
            };

    private static final String[] UNIQUE_1_TABLE_COLUMN = new String[]
            {
                    "JSON1(编号)", "相似度", "JSON2(编号)", "能量"
            };

    private static final String[] UNIQUE_2_TABLE_COLUMN = new String[]
            {
                    "JSON2(编号)", "相似度", "JSON2(编号)", "能量"
            };

    private static final Font TABLE_FONT = new Font(Font.SERIF, Font.BOLD, 18);

    private JPanel mainPane;
    private JPanel leftPane;
    private JSpinner Similar_Spinner;
    private JSpinner SD_Spinner;
    private JSpinner Gap_Spinner;
    private JButton Run_Button;
    private JPanel paramPane;
    private JLabel lbl3;
    private JLabel lbl4;
    private JLabel lbl5;
    private JPanel logPane;
    private JTextField JSON_1_Field;
    private JButton JSON_1_Open_Button;
    private JTextField JSON_2_Field;
    private JButton JSON_2_Open_Button;
    private JPanel inputPane;
    private JLabel lbl11;
    private JLabel lbl12;
    private JPanel centralPane;
    private JPanel statusPane;
    private JLabel Default_Lable;
    private JLabel Status_Label;

    /*------------------------- Custom UI Controls ------------------------*/

    private JFileChooser fileChooser = new JFileChooser();
    private DefaultTableModel Similarity_Table_Model = new DefaultTableModel(SIMILARITY_TABLE_COLUMN, 0);
    private DefaultTableModel Unique_1_Table_Model = new DefaultTableModel(UNIQUE_1_TABLE_COLUMN, 0);
    private DefaultTableModel Unique_2_Table_Model = new DefaultTableModel(UNIQUE_2_TABLE_COLUMN, 0);

    private JTable Similarity_Table = new JTable(Similarity_Table_Model)
    {
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };


    private JTable Unique_1_Table = new JTable(Unique_1_Table_Model)
    {
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };

    private JTable Unique_2_Table = new JTable(Unique_2_Table_Model)
    {
        @Override
        public boolean isCellEditable(int row, int column)
        {
            return false;
        }
    };


    private JScrollPane Similarity_Table_Scroll_Pane = new JScrollPane(Similarity_Table);
    private JScrollPane Unique_1_Table_Scroll_Pane = new JScrollPane(Unique_1_Table);
    private JScrollPane Unique_2_Table_Scroll_Pane = new JScrollPane(Unique_2_Table);


    private JTextArea Log_Area = new JTextArea();
    private JScrollPane Log_Scroll_Pane = new JScrollPane(Log_Area);


    /*---------------- properties ----------------*/
    private File JSON_1_File, JSON_2_File;
    private volatile CCLIBCJson JSON_1 = null, JSON_2 = null;

    private MainView()
    {

        /*---------------------- inputPane设置 -----------------------*/

        JSON_1_Open_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                onJSON_1_Open_Button_Clicked();
            }
        });

        JSON_2_Open_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                onJSON_2_Open_Button_Clicked();
            }
        });

        Run_Button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Log_Area.setText("");
                final LocalTime s = LocalTime.now();
                Log_Area.append("比对开始@" + s.getHour() + ":" + s.getMinute() + ":" + s.getSecond() + "\n");
                onRun_Button_Clicked();
                final LocalDateTime E = LocalDateTime.now();
                Log_Area.append("对比结束@" + E.getHour() + ":" + E.getMinute() + ":" + E.getSecond() + "\n");

                Status_Label.setText("对比完成@" + E.getYear() + "-" + E.getMonthValue() + "-" + E.getDayOfMonth()
                        + " " + E.getHour() + ":" + E.getMinute() + ":" + E.getSecond());
            }
        });


        JSON_1_Field.addMouseListener(new MouseClickAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                if (e.getButton() == MouseEvent.BUTTON1
                        && e.getClickCount() == 2)
                {
                    if (JSON_1 == null || JSON_1_File == null)
                    {
                        JOptionPane.showMessageDialog(mainPane, "JSON1没有被解析，无法查看详情。\n请尝试重新打开",
                                "查看失败", JOptionPane.WARNING_MESSAGE);
                    } else
                    {
                        DetailDialog.getInstance().showDialog(JSON_1_File.getName(), JSON_1);
                    }
                }
            }
        });


        JSON_2_Field.addMouseListener(new MouseClickAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1
                        && e.getClickCount() == 2)
                {
                    if (JSON_2 == null || JSON_2_File == null)
                    {
                        JOptionPane.showMessageDialog(mainPane, "JSON2没有被解析, 无法查看详情。\n请尝试重新打开",
                                "查看失败", JOptionPane.WARNING_MESSAGE);
                    } else
                    {
                        DetailDialog.getInstance().showDialog(JSON_2_File.getName(), JSON_2);
                    }
                }
            }
        });


        /*---------------------- paramPane设置 ---------------------*/

        Similar_Spinner.setModel(new SpinnerNumberModel(0.95D, 0.00D, 1.00D, 0.01D));
        SD_Spinner.setModel(new SpinnerNumberModel(0.01D, 0.00D, 2.00D, 0.01D));
        Gap_Spinner.setModel(new SpinnerNumberModel(100, 1, 10000, 10));

    }


    /*------------------------------ Button Listener Method ---------------------------*/

    private void onJSON_1_Open_Button_Clicked()
    {
        int result = fileChooser.showOpenDialog(mainPane);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            JSON_1_File = fileChooser.getSelectedFile();
            JSON_1_Field.setText(
                    JSON_1_File.getName()
            );

            new Thread(() -> {
                try
                {
                    String json = FileUtils.readFileToString(JSON_1_File);
                    JSON_1 = JSON.parseObject(json, CCLIBCJson.class);
                    boolean[] shadeTable = new boolean[JSON_1.getAtoms().getElements().getAtomCount()];
                    Arrays.fill(shadeTable, false);
                    JSON_1.setShadeTable(shadeTable);
                    synchronized (JLabel.class)
                    {
                        Status_Label.setText("JSON1解析成功...");
                    }
                } catch (Exception ex)
                {
                    synchronized (JLabel.class)
                    {
                        Status_Label.setText("JSON1解析失败!!!");
                    }
                }
            }).start();
        }
    }


    private void onJSON_2_Open_Button_Clicked()
    {
        int result = fileChooser.showOpenDialog(mainPane);
        if (result == JFileChooser.APPROVE_OPTION)
        {
            JSON_2_File = fileChooser.getSelectedFile();
            JSON_2_Field.setText(
                    JSON_2_File.getName()
            );

            new Thread(() -> {
                try
                {
                    String json = FileUtils.readFileToString(JSON_2_File);
                    JSON_2 = JSON.parseObject(json, CCLIBCJson.class);
                    boolean[] shadeTable = new boolean[JSON_2.getAtoms().getElements().getAtomCount()];
                    Arrays.fill(shadeTable, false);
                    JSON_2.setShadeTable(shadeTable);
                    synchronized (JLabel.class)
                    {
                        Status_Label.setText("JSON2解析成功...");
                    }
                } catch (Exception ex)
                {
                    synchronized (JLabel.class)
                    {
                        Status_Label.setText("JSON2解析失败!!!");
                    }
                }
            }).start();
        }
    }

    /* 开始文件解析 */
    private void onRun_Button_Clicked()
    {
        if (JSON_1 == null)
        {
            Log_Area.append("[ERROR] => JSON1无法完成解析!\n");
            return;
        }

        if (JSON_2 == null)
        {
            Log_Area.append("[ERROR] => JSON2无法完成解析\n");
            return;
        }


        try
        {

            /* 比对构造相似度表 */
            SimilarityTable table = Similarity.perform(JSON_1, JSON_2);

            final double similarity = (Double) Similar_Spinner.getValue();
            final double sd = (Double) SD_Spinner.getValue();
            final int gap = (Integer) Gap_Spinner.getValue();

            /*------------------------------Log-----------------------------*/

            Log_Area.append("[INFO] => 相似度 >= " + similarity + "\n");
            Log_Area.append("[INFO] => 标准差 <= " + sd + "\n");
            Log_Area.append("[INFO] => 能量差 <= " + gap + "\n");
            Log_Area.append("[INFO] => JSON1比较原子数 : " + Similarity.getTrueLength(
                    JSON_1.getShadeTable()
            ) + "\n");

            Log_Area.append("[INFO] => JSON2比较原子数 : " + Similarity.getTrueLength(
                    JSON_2.getShadeTable()
            ) + "\n");

            /*----------------------------End------------------------------*/

            final double[] frequencies_1 = JSON_1.getVibrations().getFrequencies();
            final double[] frequencies_2 = JSON_2.getVibrations().getFrequencies();

            /* 相似计数表 */
            final int[] similarity_count_table_1 = new int[frequencies_1.length];
            final int[] similarity_count_table_2 = new int[frequencies_2.length];


            /* 相似度最大值表 */
            final double[] max_similarity_table_1 = new double[frequencies_1.length];
            final double[] max_similarity_table_2 = new double[frequencies_2.length];

            /* 相似度最大序号表 */
            final int[] max_with_table_1 = new int[frequencies_1.length];
            final int[] max_with_table_2 = new int[frequencies_2.length];


            /* 初始化表 */
            for (int i = 0; i < table.size(); ++i)
            {
                similarity_count_table_1[i] = 0;
                similarity_count_table_2[i] = 0;

                max_similarity_table_1[i] = 0.0D;
                max_similarity_table_2[i] = 0.0D;

                max_with_table_1[i] = 0;
                max_with_table_2[i] = 0;
            }

            int vibration_similarity_count = 0;


            /*------------------------- 分析结果渲染UI --------------------------*/

            Similarity_Table_Model.setRowCount(0);
            for (int i = 0; i < table.x(); ++i)
            {

                for (int j = 0; j < table.y(); ++j)
                {
                    final double[] sample = table.getValueAt(i, j);
                    double AVG = Statistic.AVG(sample);
                    double SD = Statistic.SD(sample, AVG);
                    double GAP = Math.abs(frequencies_1[i] - frequencies_2[j]);

                    boolean status = AVG >= similarity && SD <= sd && GAP <= gap;

                    if (status)
                    {
                        Similarity_Table_Model.addRow(
                                new Object[]{
                                        i + 1,
                                        String.format("%.2f", frequencies_1[i]),
                                        j + 1,
                                        String.format("%.2f", frequencies_2[j]),
                                        String.format("%.2f", AVG),
                                        String.format("%.2f", SD),
                                        String.format("%.2f", GAP)
                                }
                        );
                        vibration_similarity_count++;
                        similarity_count_table_1[i]++;
                        similarity_count_table_2[j]++;
                    }

                    if (AVG > max_similarity_table_1[i])
                    {
                        max_similarity_table_1[i] = AVG;
                        max_with_table_1[i] = j;
                    }
                    if (AVG > max_similarity_table_2[j])
                    {
                        max_similarity_table_2[j] = AVG;
                        max_with_table_2[j] = i;
                    }

                }
            }
            /*------------------------------Log-----------------------------*/
            Log_Area.append("[INFO] => 震动频率相似数 : " + vibration_similarity_count + "\n");
            /*----------------------------End-------------------------------*/

            Unique_1_Table_Model.setRowCount(0);
            final int size = table.size();
            for (int i = 0; i < similarity_count_table_1.length; ++i)
            {

                if (similarity_count_table_1[i] == 0)
                {
                    /* 无相似 */
                    Unique_1_Table_Model.addRow(
                            new Object[]{
                                    i + 1,
                                    String.format("%.2f", max_similarity_table_1[i]),
                                    max_with_table_1[i] + 1,
                                    String.format("%.2f", frequencies_1[i])
                            }
                    );
                }
            }

            Unique_2_Table_Model.setRowCount(0);
            for (int i = 0; i < similarity_count_table_2.length; ++i)
            {

                if (similarity_count_table_2[i] == 0)
                {
                    /* 无相似 */
                    Unique_2_Table_Model.addRow(
                            new Object[]{
                                    i + 1,
                                    String.format("%.2f", max_similarity_table_2[i]),
                                    max_with_table_2[i] + 1,
                                    String.format("%.2f", frequencies_2[i])
                            }
                    );
                }
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
            Log_Area.append("[ERROR] => 运行中出现了异常，原因如下:\n");
            Log_Area.append(ex.getMessage() + "\n");
        }
    }


    /*----------------- instance callback hood functions -------------------*/
    private void customUI()
    {
        centralPane.add(Similarity_Table_Scroll_Pane, BorderLayout.CENTER);

        /* fileChooser */
        fileChooser.setDialogTitle("选择JSON文件");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON文件(.json)", "json"));
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setSize(500, 300);

        /* Similarity_Table */
        Similarity_Table.getTableHeader().setReorderingAllowed(false);
        Similarity_Table.setFont(TABLE_FONT);
        Similarity_Table.setDefaultRenderer(Object.class, new ResultTableCellRender());

        /* Unique_Table */
        Unique_1_Table.getTableHeader().setReorderingAllowed(false);
        Unique_2_Table.getTableHeader().setReorderingAllowed(false);

        Unique_1_Table.setFont(TABLE_FONT);
        Unique_2_Table.setFont(TABLE_FONT);

        Unique_1_Table.setDefaultRenderer(Object.class, new ResultTableCellRender());
        Unique_1_Table.setDefaultRenderer(Object.class, new ResultTableCellRender());
        /* Log_Area */
        Log_Area.setEditable(false);
        logPane.add(Log_Scroll_Pane, BorderLayout.CENTER);

        Box uniqueBox = Box.createHorizontalBox();
        uniqueBox.add(Unique_1_Table_Scroll_Pane);
        uniqueBox.add(Box.createHorizontalStrut(30));
        uniqueBox.add(Unique_2_Table_Scroll_Pane);

        centralPane.add(uniqueBox, BorderLayout.SOUTH);

        JSON_2_Field.setEnabled(false);
        JSON_1_Field.setEnabled(false);

    }


    public static MainView getInstance()
    {
        if (instance == null)
        {
            synchronized (MainView.class)
            {
                if (instance == null)
                {
                    instance = new MainView();
                    instance.customUI();
                }
            }
        }
        return instance;
    }

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
        mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout(0, 0));
        leftPane = new JPanel();
        leftPane.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), 10, -1));
        leftPane.setAlignmentX(1.0f);
        leftPane.setFocusable(false);
        Font leftPaneFont = this.$$$getFont$$$("Microsoft YaHei", Font.PLAIN, 14, leftPane.getFont());
        if (leftPaneFont != null) leftPane.setFont(leftPaneFont);
        leftPane.setPreferredSize(new Dimension(400, 293));
        mainPane.add(leftPane, BorderLayout.WEST);
        paramPane = new JPanel();
        paramPane.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        leftPane.add(paramPane, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
                false));
        paramPane.setBorder(BorderFactory.createTitledBorder(null, "参数控制", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, null));
        lbl3 = new JLabel();
        lbl3.setText("平均相似度");
        paramPane.add(lbl3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Similar_Spinner = new JSpinner();
        Font Similar_SpinnerFont = this.$$$getFont$$$(null, Font.BOLD, 14, Similar_Spinner.getFont());
        if (Similar_SpinnerFont != null) Similar_Spinner.setFont(Similar_SpinnerFont);
        paramPane.add(Similar_Spinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, -1), new Dimension(150, -1), new Dimension(150,
                -1), 0, false));
        lbl4 = new JLabel();
        lbl4.setText("相似度标准差");
        paramPane.add(lbl4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        SD_Spinner = new JSpinner();
        Font SD_SpinnerFont = this.$$$getFont$$$(null, Font.BOLD, 14, SD_Spinner.getFont());
        if (SD_SpinnerFont != null) SD_Spinner.setFont(SD_SpinnerFont);
        paramPane.add(SD_Spinner, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, -1), new Dimension(150, -1), new Dimension(150,
                -1), 0, false));
        lbl5 = new JLabel();
        lbl5.setText("能量差");
        paramPane.add(lbl5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        Gap_Spinner = new JSpinner();
        Font Gap_SpinnerFont = this.$$$getFont$$$(null, Font.BOLD, 14, Gap_Spinner.getFont());
        if (Gap_SpinnerFont != null) Gap_Spinner.setFont(Gap_SpinnerFont);
        paramPane.add(Gap_Spinner, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, new Dimension(150, -1), new Dimension(150, -1), new Dimension(150,
                -1), 0, false));
        logPane = new JPanel();
        logPane.setLayout(new BorderLayout(0, 0));
        leftPane.add(logPane, new GridConstraints(2, 0, 1, 3, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
                false));
        logPane.setBorder(BorderFactory.createTitledBorder(null, "运行日志", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, null));
        Run_Button = new JButton();
        Run_Button.setText("运行");
        leftPane.add(Run_Button, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        inputPane = new JPanel();
        inputPane.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        leftPane.add(inputPane, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_BOTH,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0,
                false));
        inputPane.setBorder(BorderFactory.createTitledBorder(null, "文件输入", TitledBorder.DEFAULT_JUSTIFICATION,
                TitledBorder.DEFAULT_POSITION, null, null));
        lbl11 = new JLabel();
        lbl11.setText("JSON1");
        inputPane.add(lbl11, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JSON_1_Field = new JTextField();
        JSON_1_Field.setEditable(false);
        JSON_1_Field.setEnabled(true);
        JSON_1_Field.setToolTipText("双击查看详情");
        inputPane.add(JSON_1_Field, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        JSON_1_Open_Button = new JButton();
        JSON_1_Open_Button.setText("打开");
        inputPane.add(JSON_1_Open_Button, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lbl12 = new JLabel();
        lbl12.setText("JSON2");
        inputPane.add(lbl12, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE,
                GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        JSON_2_Field = new JTextField();
        JSON_2_Field.setEditable(false);
        JSON_2_Field.setRequestFocusEnabled(true);
        JSON_2_Field.setToolTipText("双击查看详情");
        inputPane.add(JSON_2_Field, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST,
                GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        JSON_2_Open_Button = new JButton();
        JSON_2_Open_Button.setText("打开");
        inputPane.add(JSON_2_Open_Button, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER,
                GridConstraints.FILL_HORIZONTAL,
                GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW,
                GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        centralPane = new JPanel();
        centralPane.setLayout(new BorderLayout(0, 0));
        mainPane.add(centralPane, BorderLayout.CENTER);
        statusPane = new JPanel();
        statusPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, 5));
        mainPane.add(statusPane, BorderLayout.SOUTH);
        Status_Label = new JLabel();
        Status_Label.setText("");
        statusPane.add(Status_Label);
        Default_Lable = new JLabel();
        Default_Lable.setHorizontalAlignment(0);
        Default_Lable.setHorizontalTextPosition(11);
        Default_Lable.setText("QC(v1.1.0)");
        statusPane.add(Default_Lable);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont)
    {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null)
        {
            resultName = currentFont.getName();
        } else
        {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1'))
            {
                resultName = fontName;
            } else
            {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size :
                currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) :
                new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$()
    {
        return mainPane;
    }

}
