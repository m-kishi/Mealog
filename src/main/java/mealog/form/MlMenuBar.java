// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mealog.common.MlException;
import mealog.common.MlUtility.MSG;

/**
 * メニューバー
 */
public class MlMenuBar extends JMenuBar {

    /** 親のフレーム */
    private MlFormRecord frame;

    /** 記録情報ファイル */
    private String recordFile;

    /** マスタ情報ファイル */
    private String masterFile;

    /**
     * コンストラクタ
     * 
     * @param frame      親のフレーム
     * @param recordFile 記録情報ファイル
     * @param masterFile マスタ情報ファイル
     */
    public MlMenuBar(MlFormRecord frame, String recordFile, String masterFile) {
        super();

        this.frame = frame;
        this.recordFile = recordFile;
        this.masterFile = masterFile;

        JMenu menuFile = new JMenu("ファイル");
        JMenu menuData = new JMenu("データ");
        JMenu menuHelp = new JMenu("ヘルプ");

        JMenuItem menuItemQuit = new JMenuItem("終了");
        JMenuItem menuItemResult = new JMenuItem("実績");
        JMenuItem menuItemMaster = new JMenuItem("マスタ");
        JMenuItem menuItemVersion = new JMenuItem("バージョン情報");

        menuItemQuit.addActionListener(new QuitActionListener());
        menuItemResult.addActionListener(new ResultActionListener());
        menuItemMaster.addActionListener(new MasterActionListener());
        menuItemVersion.addActionListener(new VersionAcionListener());

        menuFile.add(menuItemQuit);
        menuData.add(menuItemResult);
        menuData.add(menuItemMaster);
        menuHelp.add(menuItemVersion);
        add(menuFile);
        add(menuData);
        add(menuHelp);
    }

    /**
     * 終了メニュー
     */
    private class QuitActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    /**
     * 実績メニュー
     */
    private class ResultActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MlFormResult frame = new MlFormResult(recordFile);
                frame.setVisible(true);
            } catch (MlException ex) {
                MSG.error(frame, ex.getMessage());
            }
        }
    }

    /**
     * マスタメニュー
     */
    private class MasterActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MlFormMaster frame = new MlFormMaster(MlMenuBar.this.frame, masterFile);
                frame.setVisible(true);
            } catch (MlException ex) {
                MSG.error(frame, ex.getMessage());
            }
        }
    }

    /**
     * バージョン情報メニュー
     */
    private class VersionAcionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            MlFormVersion frame = new MlFormVersion();
            frame.setVisible(true);
        }
    }
}
