package lab.callback.qc;

import lab.callback.qc.view.MainFrame;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

/**
 * QC程序入口类
 */
public class App
{


    {
        try {
            /* 设置Frame Border */
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.osLookAndFeelDecorated;

            BeautyEyeLNFHelper.launchBeautyEyeLNF();

        } catch (Exception e) {
            System.exit(-1);
        }
    }
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
