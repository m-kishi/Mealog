// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import mealog.common.MlException;
import mealog.data.MlRecord;
import mealog.data.manager.MlFileManager;
import mealog.form.table.MlResultTable;
import mealog.form.table.model.MlResultTableModel;
import mealog.form.table.model.MlResultTableModel.TYPE;

/**
 * 実績フォーム
 */
public class MlFormResult extends JDialog implements WindowListener {

    /** スクロール領域 */
    private JScrollPane scrollPaneKcal;

    /** スクロール領域 */
    private JScrollPane scrollPanePrtn;

    /** スクロール領域 */
    private JScrollPane scrollPaneFats;

    /** スクロール領域 */
    private JScrollPane scrollPaneSalt;

    /**
     * コンストラクタ
     * 
     * @param recordFile 記録情報ファイル
     * @throws MlException
     */
    public MlFormResult(String recordFile) throws MlException {

        // フォーム
        setTitle("実績");
        setModal(true);
        setSize(858, 320);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(this);

        // コンポーネントの生成
        setComponent(MlFileManager.loadRecord(recordFile));
    }

    /**
     * コンポーネントの生成
     * 
     * @param records 記録情報リスト
     */
    public void setComponent(List<MlRecord> records) {

        // タブ
        JTabbedPane tab = new JTabbedPane();

        // テーブルモデル
        MlResultTableModel modelKcal = new MlResultTableModel(records, TYPE.KCAL);
        MlResultTableModel modelPrtn = new MlResultTableModel(records, TYPE.PRTN);
        MlResultTableModel modelFats = new MlResultTableModel(records, TYPE.FATS);
        MlResultTableModel modelSalt = new MlResultTableModel(records, TYPE.SALT);
        MlResultTable tableKcal = new MlResultTable(modelKcal);
        MlResultTable tablePrtn = new MlResultTable(modelPrtn);
        MlResultTable tableFats = new MlResultTable(modelFats);
        MlResultTable tableSalt = new MlResultTable(modelSalt);

        // テーブル設定
        Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 12);
        tableKcal.setFont(font);
        tablePrtn.setFont(font);
        tableFats.setFont(font);
        tableSalt.setFont(font);
        tableKcal.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablePrtn.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableFats.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableSalt.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // スクロール領域
        scrollPaneKcal = new JScrollPane(tableKcal);
        scrollPanePrtn = new JScrollPane(tablePrtn);
        scrollPaneFats = new JScrollPane(tableFats);
        scrollPaneSalt = new JScrollPane(tableSalt);
        scrollPaneKcal.setBorder(new CompoundBorder(scrollPaneKcal.getBorder(), new EmptyBorder(0, 0, 0, 0)));
        scrollPanePrtn.setBorder(new CompoundBorder(scrollPanePrtn.getBorder(), new EmptyBorder(0, 0, 0, 0)));
        scrollPaneFats.setBorder(new CompoundBorder(scrollPaneFats.getBorder(), new EmptyBorder(0, 0, 0, 0)));
        scrollPaneSalt.setBorder(new CompoundBorder(scrollPaneSalt.getBorder(), new EmptyBorder(0, 0, 0, 0)));

        // 余白設定
        tableKcal.setLayout(new BorderLayout());
        tablePrtn.setLayout(new BorderLayout());
        tableFats.setLayout(new BorderLayout());
        tableSalt.setLayout(new BorderLayout());
        tableKcal.setBorder(new CompoundBorder(tableKcal.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        tablePrtn.setBorder(new CompoundBorder(tablePrtn.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        tableFats.setBorder(new CompoundBorder(tableFats.getBorder(), new EmptyBorder(5, 5, 5, 5)));
        tableSalt.setBorder(new CompoundBorder(tableSalt.getBorder(), new EmptyBorder(5, 5, 5, 5)));

        // タブに追加
        tab.addTab("kcal", scrollPaneKcal);
        tab.addTab("たんぱく質", scrollPanePrtn);
        tab.addTab("脂質", scrollPaneFats);
        tab.addTab("塩分", scrollPaneSalt);

        getContentPane().add(tab, BorderLayout.CENTER);
    }

    /**
     * 初期化
     */
    private void initialize() {

        // スクロールバーを最下層へ
        JScrollBar scrollBarKcal = scrollPaneKcal.getVerticalScrollBar();
        JScrollBar scrollBarPrtn = scrollPanePrtn.getVerticalScrollBar();
        JScrollBar scrollBarFats = scrollPaneFats.getVerticalScrollBar();
        JScrollBar scrollBarSalt = scrollPaneSalt.getVerticalScrollBar();
        scrollBarKcal.setValue(scrollBarKcal.getMaximum());
        scrollBarPrtn.setValue(scrollBarPrtn.getMaximum());
        scrollBarFats.setValue(scrollBarFats.getMaximum());
        scrollBarSalt.setValue(scrollBarSalt.getMaximum());
    }

    @Override
    public void windowOpened(WindowEvent e) {
        initialize();
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
