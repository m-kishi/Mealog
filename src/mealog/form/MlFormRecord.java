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

    /** 日合計(タンパク質) */
    private JLabel lblPrtnTotal;

    /** 日合計(脂質) */
    private JLabel lblFatsTotal;

    /** 日合計(塩分) */
    private JLabel lblSaltTotal;

    /** 月平均(kcal) */
    private JLabel lblKcalAverage;

    /** 月平均(タンパク質) */
    private JLabel lblPrtnAverage;

    /** 月平均(脂質) */
    private JLabel lblFatsAverage;

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
        setSize(964, 446);
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
        btnPrevY.addActionListener(new ChangeDateActionListener(() -> model.loadPrevYear()));
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
        JLabel lblSlash1 = new JLabel(" / ");
        JLabel lblSlash2 = new JLabel(" / ");
        JLabel lblSlash3 = new JLabel(" / ");
        JLabel lblSlash4 = new JLabel(" / ");
        JLabel lblSlash5 = new JLabel(" / ");
        JLabel lblSlash6 = new JLabel(" / ");
        lblKcalTotal = new JLabel("0.0 kcal");
        lblPrtnTotal = new JLabel("0.0 g");
        lblFatsTotal = new JLabel("0.0 g");
        lblSaltTotal = new JLabel("0.0 g");
        lblKcalAverage = new JLabel("0.0 kcal");
        lblPrtnAverage = new JLabel("0.0 g");
        lblFatsAverage = new JLabel("0.0 g");
        lblSaltAverage = new JLabel("0.0 g");
        lblKcalTotal.setHorizontalAlignment(JLabel.RIGHT);
        lblPrtnTotal.setHorizontalAlignment(JLabel.RIGHT);
        lblFatsTotal.setHorizontalAlignment(JLabel.RIGHT);
        lblSaltTotal.setHorizontalAlignment(JLabel.RIGHT);
        lblKcalAverage.setHorizontalAlignment(JLabel.RIGHT);
        lblPrtnAverage.setHorizontalAlignment(JLabel.RIGHT);
        lblFatsAverage.setHorizontalAlignment(JLabel.RIGHT);
        lblSaltAverage.setHorizontalAlignment(JLabel.RIGHT);
        lblTotal.setFont(lblFont);
        lblAverage.setFont(lblFont);
        lblSlash1.setFont(lblFont);
        lblSlash2.setFont(lblFont);
        lblSlash3.setFont(lblFont);
        lblSlash4.setFont(lblFont);
        lblSlash5.setFont(lblFont);
        lblSlash6.setFont(lblFont);
        lblKcalTotal.setFont(lblFont);
        lblPrtnTotal.setFont(lblFont);
        lblFatsTotal.setFont(lblFont);
        lblSaltTotal.setFont(lblFont);
        lblKcalAverage.setFont(lblFont);
        lblPrtnAverage.setFont(lblFont);
        lblFatsAverage.setFont(lblFont);
        lblSaltAverage.setFont(lblFont);
        contentArea.add(lblTotal);
        contentArea.add(lblKcalTotal);
        contentArea.add(lblSlash1);
        contentArea.add(lblPrtnTotal);
        contentArea.add(lblSlash2);
        contentArea.add(lblFatsTotal);
        contentArea.add(lblSlash3);
        contentArea.add(lblSaltTotal);
        contentArea.add(lblAverage);
        contentArea.add(lblKcalAverage);
        contentArea.add(lblSlash4);
        contentArea.add(lblPrtnAverage);
        contentArea.add(lblSlash5);
        contentArea.add(lblFatsAverage);
        contentArea.add(lblSlash6);
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
        layout.putConstraint(SpringLayout.WEST, lblTotal, 88, SpringLayout.WEST, contentArea);
        layout.putConstraint(SpringLayout.NORTH, lblKcalTotal, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblKcalTotal, 1, SpringLayout.EAST, lblTotal);
        layout.putConstraint(SpringLayout.EAST, lblKcalTotal, -685, SpringLayout.EAST, contentArea);
        layout.putConstraint(SpringLayout.NORTH, lblSlash1, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblSlash1, 5, SpringLayout.EAST, lblKcalTotal);
        layout.putConstraint(SpringLayout.NORTH, lblPrtnTotal, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblPrtnTotal, 0, SpringLayout.EAST, lblSlash1);
        layout.putConstraint(SpringLayout.EAST, lblPrtnTotal, -595, SpringLayout.EAST, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblSlash2, 0, SpringLayout.WEST, lblSlash1);
        layout.putConstraint(SpringLayout.NORTH, lblSlash2, 5, SpringLayout.SOUTH, lblSlash1);
        layout.putConstraint(SpringLayout.NORTH, lblFatsTotal, 2, SpringLayout.NORTH, lblSlash2);
        layout.putConstraint(SpringLayout.WEST, lblFatsTotal, 0, SpringLayout.EAST, lblSlash2);
        layout.putConstraint(SpringLayout.EAST, lblFatsTotal, 0, SpringLayout.EAST, lblPrtnTotal);
        layout.putConstraint(SpringLayout.WEST, lblSlash3, 0, SpringLayout.WEST, lblSlash2);
        layout.putConstraint(SpringLayout.NORTH, lblSlash3, 5, SpringLayout.SOUTH, lblSlash2);
        layout.putConstraint(SpringLayout.NORTH, lblSaltTotal, 2, SpringLayout.NORTH, lblSlash3);
        layout.putConstraint(SpringLayout.WEST, lblSaltTotal, 0, SpringLayout.EAST, lblSlash3);
        layout.putConstraint(SpringLayout.EAST, lblSaltTotal, 0, SpringLayout.EAST, lblFatsTotal);
        layout.putConstraint(SpringLayout.NORTH, lblAverage, 10, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblAverage, 50, SpringLayout.EAST, lblSaltTotal);
        layout.putConstraint(SpringLayout.NORTH, lblKcalAverage, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblKcalAverage, 1, SpringLayout.EAST, lblAverage);
        layout.putConstraint(SpringLayout.EAST, lblKcalAverage, -365, SpringLayout.EAST, contentArea);
        layout.putConstraint(SpringLayout.NORTH, lblSlash4, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblSlash4, 5, SpringLayout.EAST, lblKcalAverage);
        layout.putConstraint(SpringLayout.NORTH, lblPrtnAverage, 9, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblPrtnAverage, 0, SpringLayout.EAST, lblSlash4);
        layout.putConstraint(SpringLayout.EAST, lblPrtnAverage, -275, SpringLayout.EAST, contentArea);
        layout.putConstraint(SpringLayout.WEST, lblSlash5, 0, SpringLayout.WEST, lblSlash4);
        layout.putConstraint(SpringLayout.NORTH, lblSlash5, 5, SpringLayout.SOUTH, lblSlash4);
        layout.putConstraint(SpringLayout.NORTH, lblFatsAverage, 2, SpringLayout.NORTH, lblSlash5);
        layout.putConstraint(SpringLayout.WEST, lblFatsAverage, 0, SpringLayout.EAST, lblSlash5);
        layout.putConstraint(SpringLayout.EAST, lblFatsAverage, 0, SpringLayout.EAST, lblPrtnAverage);
        layout.putConstraint(SpringLayout.WEST, lblSlash6, 0, SpringLayout.WEST, lblSlash5);
        layout.putConstraint(SpringLayout.NORTH, lblSlash6, 5, SpringLayout.SOUTH, lblSlash5);
        layout.putConstraint(SpringLayout.NORTH, lblSaltAverage, 2, SpringLayout.NORTH, lblSlash6);
        layout.putConstraint(SpringLayout.WEST, lblSaltAverage, 0, SpringLayout.EAST, lblSlash6);
        layout.putConstraint(SpringLayout.EAST, lblSaltAverage, 0, SpringLayout.EAST, lblFatsAverage);
        layout.putConstraint(SpringLayout.NORTH, btnAddRow, 10, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.EAST, btnAddRow, -10, SpringLayout.EAST, contentArea);
        layout.putConstraint(SpringLayout.NORTH, btnEntry, 10, SpringLayout.NORTH, contentArea);
        layout.putConstraint(SpringLayout.EAST, btnEntry, -10, SpringLayout.WEST, btnAddRow);
        layout.putConstraint(SpringLayout.NORTH, scrollPane, 10, SpringLayout.SOUTH, lblSlash3);
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
     * @param infos [
     *              0:日合計(kcal)
     *              1:日合計(タンパク質)
     *              2:日合計(脂質)
     *              3:日合計(塩分)
     *              4:月平均(kcal)
     *              5:月平均(タンパク質)
     *              6:月平均(脂質)
     *              7:月平均(塩分)
     *              ]
     */
    public void updateInfo(List<BigDecimal> infos) {
        lblKcalTotal.setText(UTL.format(infos.get(0)) + " kcal");
        lblPrtnTotal.setText(UTL.format(infos.get(1)) + " g");
        lblFatsTotal.setText(UTL.format(infos.get(2)) + " g");
        lblSaltTotal.setText(UTL.format(infos.get(3)) + " g");
        lblKcalAverage.setText(UTL.format(infos.get(4)) + " kcal");
        lblPrtnAverage.setText(UTL.format(infos.get(5)) + " g");
        lblFatsAverage.setText(UTL.format(infos.get(6)) + " g");
        lblSaltAverage.setText(UTL.format(infos.get(7)) + " g");
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
