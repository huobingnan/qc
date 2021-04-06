package lab.callback.qc.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
    private static final int WIDTH = 1450;
    private static final int HEIGHT = 1000;

    public MainFrame()
    {
        setTitle("QC(V1.1.0)");
        setSize(WIDTH, HEIGHT);
        setContentPane(
                MainView.getInstance().$$$getRootComponent$$$()
        );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = (int)(toolkit.getScreenSize().getWidth()-getWidth())/2;
        int y = (int)(toolkit.getScreenSize().getHeight()-getHeight())/2;
        setLocation(x, y);
        final String path = System.getProperty("user.dir") + "/static/image/icon.png";
        System.out.println(path);
        setIconImage(new ImageIcon(path).getImage());
    }
}
