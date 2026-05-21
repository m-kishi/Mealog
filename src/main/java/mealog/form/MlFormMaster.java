// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import mealog.common.MlException;
import mealog.common.MlUtility.MSG;
import mealog.data.MlMaster;
import mealog.data.manager.MlFileManager;
import mealog.form.table.MlMasterTable;
import mealog.form.table.model.MlMasterTableModel;

/**
 * マスタフォーム
 */
public class MlFormMaster extends JFrame implements WindowListener {

    /** 親のフレーム */
    private MlFormRecord frame;

    /** マスタ情報ファイル */
    private String masterFile;

    /** テーブル */
    private MlMasterTable table;

    /** テーブルモデル */
    private MlMasterTableModel model;

    /** スクロール領域 */
    private JScrollPane scrollPane;

    /**
     * コンストラクタ
     * 
     * @param frame      親のフレーム
     * @param masterFile マスタ情報ファイル
     * @throws MlException
     */
    public MlFormMaster(MlFormRecord frame, String masterFile) throws MlException {
        super();

        this.frame = frame;
        this.masterFile = masterFile;

        // フォーム
        setTitle("マスタ");
        setSize(872, 700);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(this);

        // コンポーネントの生成
        setComponent();
    }

    /**
     * コンポーネントの生成
     * 
     * @throws MlException
     */
    private void setComponent() throws MlException {

        // 画面領域
        JPanel baseArea = new JPanel();

        // 画面の余白設定
        baseArea.setLayout(new BorderLayout());
        baseArea.setBorder(new CompoundBorder(baseArea.getBorder(), new EmptyBorder(0, 5, 5, 5)));

        // ボタンエリアの右寄せ
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.RIGHT);

        // ボタンエリア
        JPanel buttonArea = new JPanel();
        buttonArea.setLayout(layout);

        // 登録ボタン
        JButton btnEntry = new JButton("登録");
        btnEntry.addActionListener(new EntryAcionListener());
        buttonArea.add(btnEntry);

        // 行追加ボタン
        JButton btnAddRow = new JButton("＋");
        btnAddRow.addActionListener(new AddRowActionListener());
        buttonArea.add(btnAddRow);

        // テーブル設定
        model = new MlMasterTableModel(MlFileManager.loadMaster(masterFile));
        table = new MlMasterTable(model, baseArea);

        // スクロール領域
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new CompoundBorder(scrollPane.getBorder(), new EmptyBorder(0, 0, 0, 1)));

        baseArea.add(buttonArea, BorderLayout.NORTH);
        baseArea.add(scrollPane, BorderLayout.CENTER);

        add(baseArea);
    }

    /**
     * 初期化
     */
    private void initialize() {

        // スクロールバーを最上層へ
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMinimum());
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

    /**
     * 行追加ボタン
     */
    private class AddRowActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            model.addRow();
        }
    }

    /**
     * 登録ボタン
     */
    private class EntryAcionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                // マスタ情報リストを取得
                List<MlMaster> masters = model.getMasters();

                // 基準量ゼロはNG
                if (masters.stream().anyMatch(x -> x.getBase().equals(BigDecimal.ZERO))) {
                    throw new MlException("基準量を入力してください。");
                }

                // マスタ情報ファイルへ書き込み
                MlFileManager.storeMaster(masterFile, masters);

                // 再読み込み
                MSG.ok(MlFormMaster.this, "登録", "登録しました．");
                model.load(masters);

                // 初期化
                initialize();

                // 記録フォームへ反映
                frame.setMasters(masters);

            } catch (MlException ex) {
                MSG.error(MlFormMaster.this, ex.getMessage());
            }
        }
    }
}
