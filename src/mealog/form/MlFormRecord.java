// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import mealog.common.MlException;
import mealog.common.MlUtility.MSG;
import mealog.common.MlUtility.UTL;
import mealog.data.MlMaster;
import mealog.data.MlRecord;
import mealog.data.manager.MlFileManager;
import mealog.form.table.MlRecordTable;
import mealog.form.table.model.MlRecordTableModel;

/**
 * 記録フォーム
 */
public class MlFormRecord extends JFrame implements WindowListener {

    /** 記録情報ファイル */
    private String recordFile;

    /** 記録情報リスト */
    private List<MlRecord> records;

    /** マスタ情報リスト */
    private List<MlMaster> masters;

    /** タイトル */
    private JLabel lblTitle;

    /** マーク */
    private JCheckBox chkMark;

    /** 日合計(kcal) */
    private JLabel lblKcalTotal;

    /** 日合計(塩分) */
    private JLabel lblSaltTotal;

    /** 月平均(kcal) */
    private JLabel lblKcalAverage;

    /** 月平均(塩分) */
    private JLabel lblSaltAverage;

    /** テーブル */
    private MlRecordTable table;

    /** テーブルモデル */
    private MlRecordTableModel model;

    /** スクロール領域 */
    private JScrollPane scrollPane;

    /**
     * コンストラクタ
     * 
     * @param recordFile 記録情報ファイル
     * @param masterFile マスタ情報ファイル
     * @throws MlException
     */
    public MlFormRecord(String recordFile, String masterFile) throws MlException {
        super();

        this.recordFile = recordFile;

        // フォーム
        setTitle("Mealog");
        setSize(884, 446);
        setResizable(false);
        addWindowListener(this);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // メニュー
        setJMenuBar(new MlMenuBar(this, recordFile, masterFile));

        // 記録情報読み込み
        records = MlFileManager.loadRecord(recordFile);

        // マスタ情報読み込み
        masters = MlFileManager.loadMaster(masterFile);

        // コンポーネントの生成
        setComponent();

        // 初期化
        initialize();
    }

    /**
     * コンポーネントの生成
     */
    private void setComponent() {

        // 画面領域
        JPanel baseArea = new JPanel();

        // 画面の余白設定
        baseArea.setLayout(new BorderLayout());
        baseArea.setBorder(new CompoundBorder(baseArea.getBorder(), new EmptyBorder(0, 5, 5, 5)));

        // ヘッダエリア
        JPanel headerArea = new JPanel();
        headerArea.setLayout(new BoxLayout(headerArea, BoxLayout.Y_AXIS));

        // ヘッダエリアのタイトル
        JPanel headerTitle = new JPanel();
        headerArea.add(headerTitle);

        // ボタンのフォント・サイズ・余白
        Font btnFont = new Font(Font.DIALOG_INPUT, Font.BOLD, 8);
        Dimension size = new Dimension(30, 20);
        Insets margin = new Insets(0, 0, 0, 0);

        // 日合計・月平均のフォント・サイズ
        Font lblFont = new Font(Font.DIALOG_INPUT, Font.BOLD, 16);

        // 前年ボタン
        JButton btnPrevY = new JButton("<<<");
        btnPrevY.setFont(btnFont);
        btnPrevY.setMargin(margin);
        btnPrevY.setPreferredSize(size);
        btnPrevY.addActionListener(new ChangeDateActionListener(() -> model.loadNextYear()));
        headerTitle.add(btnPrevY);
        headerTitle.add(Box.createRigidArea(new Dimension(5, 1)));

        // 前月ボタン
        JButton btnPrevM = new JButton("<<");
        btnPrevM.setFont(btnFont);
        btnPrevM.setMargin(margin);
        btnPrevM.setPreferredSize(size);
        btnPrevM.addActionListener(new ChangeDateActionListener(() -> model.loadPrevMonth()));
        headerTitle.add(btnPrevM);
        headerTitle.add(Box.createRigidArea(new Dimension(5, 1)));

        // 前日ボタン
        JButton btnPrevD = new JButton("<");
        btnPrevD.setFont(btnFont);
        btnPrevD.setMargin(margin);
        btnPrevD.setPreferredSize(size);
        btnPrevD.addActionListener(new ChangeDateActionListener(() -> model.loadPrevDay()));
        headerTitle.add(btnPrevD);
        headerTitle.add(Box.createRigidArea(new Dimension(5, 1)));

        // マーク
        chkMark = new JCheckBox();
        chkMark.addActionListener(new CheckActionListener());
        headerTitle.add(chkMark);
        headerTitle.add(Box.createRigidArea(new Dimension(5, 1)));

        // タイトル
        lblTitle = new JLabel();
        lblTitle.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 18));
        headerTitle.add(lblTitle);
        headerTitle.add(Box.createRigidArea(new Dimension(5, 1)));

        // 翌日ボタン
        JButton btnNextD = new JButton(">");
        btnNextD.setFont(btnFont);
        btnNextD.setMargin(margin);
        btnNextD.setPreferredSize(size);
        btnNextD.addActionListener(new ChangeDateActionListener(() -> model.loadNextDay()));
        headerTitle.add(btnNextD);
        headerTitle.add(Box.createRigidArea(new Dimension(5, 1)));

        // 翌月ボタン
        JButton btnNextM = new JButton(">>");
        btnNextM.setFont(btnFont);
        btnNextM.setMargin(margin);
        btnNextM.setPreferredSize(size);
        btnNextM.addActionListener(new ChangeDateActionListener(() -> model.loadNextMonth()));
        headerTitle.add(btnNextM);
        headerTitle.add(Box.createRigidArea(new Dimension(5, 1)));

        // 翌年ボタン
        JButton btnNextY = new JButton(">>>");
        btnNextY.setFont(btnFont);
        btnNextY.setMargin(margin);
        btnNextY.setPreferredSize(size);
        btnNextY.addActionListener(new ChangeDateActionListener(() -> model.loadNextYear()));
        headerTitle.add(btnNextY);

        // 日計・月平均・登録・追加の配置
        SpringLayout layout = new SpringLayout();

        // 日計・月平均・登録・追加・テーブルの表示エリア
        JPanel contentArea = new JPanel(layout);

        // 日計・月平均のラベル
        JLabel lblTotal = new JLabel("日合計：");
        JLabel lblAverage = new JLabel("月平均：");
        lblKcalTotal = new JLabel("0.0 kcal");
        lblSaltTotal = new JLabel(" / 0.0 g");
        lblKcalAverage = new JLabel("0.0 kcal");
        lblSaltAverage = new JLabel(" / 0.0 g");
        lblTotal.setFont(lblFont);
        lblAverage.setFont(lblFont);
        lblKcalTotal.setFont(lblFont);
        lblSaltTotal.setFont(lblFont);
        lblKcalAverage.setFont(lblFont);
        lblSaltAverage.setFont(lblFont);
        contentArea.add(lblTotal);
        contentArea.add(lblKcalTotal);
        contentArea.add(lblSaltTotal);
        contentArea.add(lblAverage);
        contentArea.add(lblKcalAverage);
        contentArea.add(lblSaltAverage);

        // 登録ボタン
        JButton btnEntry = new JButton("登録");
        btnEntry.addActionListener(new EntryAcionListener());
        contentArea.add(btnEntry);

        // 行追加ボタン
        JButton btnAddRow = new JButton("＋");
        btnAddRow.addActionListener(new AddRowActionListener());
        contentArea.add(btnAddRow);

        // テーブル設定
        model = new MlRecordTableModel(this, LocalDate.now(), records);
        table = new MlRecordTable(model);

        // スクロール領域
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new CompoundBorder(scrollPane.getBorder(), new EmptyBorder(0, 0, 0, 1)));
        contentArea.add(scrollPane);

        // レイアウトの配置設定
        layout.putConstraint(SpringLayout.NORTH, lblTotal, 10, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblTotal, 10, SpringLayout.WEST, contentArea);
        layout.putConstraint(SpringLayout.NORTH, lblKcalTotal, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblKcalTotal, 1, SpringLayout.EAST, lblTotal);
        layout.putConstraint(SpringLayout.NORTH, lblSaltTotal, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblSaltTotal, 5, SpringLayout.EAST, lblKcalTotal);
        layout.putConstraint(SpringLayout.NORTH, lblAverage, 10, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblAverage, 15, SpringLayout.EAST, lblSaltTotal);
        layout.putConstraint(SpringLayout.NORTH, lblKcalAverage, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblKcalAverage, 1, SpringLayout.EAST, lblAverage);
        layout.putConstraint(SpringLayout.NORTH, lblSaltAverage, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblSaltAverage, 5, SpringLayout.EAST, lblKcalAverage);
        layout.putConstraint(SpringLayout.NORTH, btnAddRow, 10, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.EAST, btnAddRow, -10, SpringLayout.EAST, contentArea);
        layout.putConstraint(SpringLayout.NORTH, btnEntry, 10, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.EAST, btnEntry, -10, SpringLayout.WEST, btnAddRow);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, btnEntry);
        layout.putConstraint(SpringLayout.WEST, scrollPane, 5, SpringLayout.WEST, contentArea);
        layout.putConstraint(SpringLayout.EAST, scrollPane, -5, SpringLayout.EAST, contentArea);
        layout.putConstraint(SpringLayout.SOUTH, scrollPane, -5, SpringLayout.SOUTH, contentArea);

        baseArea.add(headerArea, BorderLayout.NORTH);
        baseArea.add(contentArea, BorderLayout.CENTER);

        add(baseArea);
    }

    /**
     * 初期化
     */
    private void initialize() {

        // タイトル設定
        lblTitle.setText(UTL.toTitle(model.getDate()));

        // スクロールバーを最下層へ
        JScrollBar scrollBar = scrollPane.getVerticalScrollBar();
        scrollBar.setValue(scrollBar.getMaximum());

        // 画面の表示を更新
        model.updateInfo();

        // 初期マークの設定
        chkMark.setSelected(model.getMark());
    }

    /**
     * マスタ情報リストを取得
     * 
     * @return マスタ情報リスト
     */
    public List<MlMaster> getMasters() {
        return masters;
    }

    /**
     * マスタ情報リストを設定
     * 
     * @param masters マスタ情報リスト
     */
    public void setMasters(List<MlMaster> masters) {
        this.masters = masters;
    }

    /**
     * 日合計と月平均の表示を更新
     * 
     * @param infos [0:日合計(kcal) 1:日合計(塩分) 2:月平均(kcal) 3:月平均(塩分)]
     */
    public void updateInfo(List<BigDecimal> infos) {
        lblKcalTotal.setText(UTL.format(infos.get(0)) + " kcal");
        lblSaltTotal.setText(" / " + UTL.format(infos.get(1)) + " g");
        lblKcalAverage.setText(UTL.format(infos.get(2)) + " kcal");
        lblSaltAverage.setText(" / " + UTL.format(infos.get(3)) + " g");
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
     * 日付切り替えボタン
     */
    private class ChangeDateActionListener implements ActionListener {

        /** 切り替えアクション */
        private Supplier<Integer> action;

        /**
         * コンストラクタ
         * 
         * @param action 切り替えアクション
         */
        public ChangeDateActionListener(Supplier<Integer> action) {
            this.action = action;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            action.get();
            lblTitle.setText(UTL.toTitle(model.getDate()));
            chkMark.setSelected(model.getMark());
        }
    }

    /**
     * マークチェックボックスの切り替え
     */
    private class CheckActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox source = (JCheckBox) e.getSource();

            model.setMark(source.isSelected());
            model.updateInfo();
        }
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

                // 記録情報を書き出し
                List<MlRecord> records = model.getRecords();
                MlFileManager.storeRecord(recordFile, records);

                // 再読み込み
                model.load(records);
                MSG.ok(MlFormRecord.this, "登録", "登録しました．");

                // 初期化
                initialize();

            } catch (MlException ex) {
                MSG.error(MlFormRecord.this, ex.getMessage());
            }
        }
    }
}
