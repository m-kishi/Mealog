// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import mealog.common.MlUtility.UTL;
import mealog.data.MlMaster;
import mealog.data.MlRecord;
import mealog.form.MlFormRecord;
import mealog.form.table.MlRecordTable.TABLE;

/**
 * 記録情報テーブルモデル
 */
public class MlRecordTableModel extends AbstractTableModel implements TableModelListener {

    /** 親のフレーム */
    private MlFormRecord frame;

    /** 現在の日付 */
    private LocalDate date;

    /** 現在の日付の記録情報 */
    private List<Object[]> currentRecords;

    /** 内部データ */
    private Map<LocalDate, List<MlRecord>> internalRecords;

    /** 列ヘッダ定義 */
    private static final String[] COLUMNS = {
            "時間帯",
            "名前",
            "数量",
            "単位",
            "kcal",
            "塩分",
            "備考1",
            "備考2",
    };

    /**
     * コンストラクタ
     * 
     * @param frame   親のフレーム
     * @param date    日付
     * @param records 記録情報リスト
     */
    public MlRecordTableModel(MlFormRecord frame, LocalDate date, List<MlRecord> records) {
        super();
        this.frame = frame;
        this.date = date;
        load(records);
        addTableModelListener(this);
    }

    /**
     * 記録情報の読み込み
     * 
     * @param records 記録情報リスト
     */
    public void load(List<MlRecord> records) {

        // 初期化
        internalRecords = new HashMap<LocalDate, List<MlRecord>>();

        // 日付ごとにグループ化
        var groupByDate = records.stream().collect(Collectors.groupingBy(MlRecord::getDate, Collectors.toList()));
        for (var entry : groupByDate.entrySet()) {
            internalRecords.put(entry.getKey(), entry.getValue());
        }

        // 現在の日付の記録情報を設定
        setCurrentRecord();
    }

    /**
     * 現在の日付の記録情報を設定
     */
    private void setCurrentRecord() {
        if (currentRecords == null) {
            currentRecords = new ArrayList<Object[]>();
        } else {
            currentRecords.clear();
        }
        if (internalRecords.containsKey(date)) {
            for (MlRecord record : internalRecords.get(date)) {
                currentRecords.add(new Object[] {
                        record.getZone(),
                        record.getName(),
                        record.getMass(),
                        record.getUnit(),
                        record.getKcal(),
                        record.getSalt(),
                        record.getNote1(),
                        record.getNote2(),
                });
            }
        }
        fireTableDataChanged();
    }

    /**
     * 日付を取得
     * 
     * @return 日付
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * 行追加
     */
    public void addRow() {
        for (int i = 0; i < 10; i++) {
            currentRecords.add(new Object[] { "", "", BigDecimal.ZERO, "", BigDecimal.ZERO, BigDecimal.ZERO, "", "" });
        }
        fireTableRowsInserted(0, currentRecords.size() - 1);
    }

    /**
     * 記録情報リストを取得
     * 
     * @return 記録情報リスト
     */
    public List<MlRecord> getRecords() {

        // 現在の日付の記録情報を内部データへ退避
        storeCurrentRecords();

        // TreeMapを使って日付順にソート
        TreeMap<LocalDate, List<MlRecord>> sorted = new TreeMap<>(internalRecords);

        // 内部データを一つのリストにして返却
        List<MlRecord> records = new ArrayList<MlRecord>();
        for (var entry : sorted.entrySet()) {
            records.addAll(entry.getValue());
        }

        return records;
    }

    /**
     * 日付の切り替え
     * 
     * @param date 日付
     * @return ラムダ式を使う都合で返しているだけ
     */
    private int changeDate(LocalDate date) {

        // 日付を切り替える前に現在の記録情報を退避
        storeCurrentRecords();

        // 日付を切り替え
        this.date = date;

        // 切り替えた日付の記録情報を設定
        setCurrentRecord();

        return 0;
    }

    /**
     * 前年へ切り替え
     * 
     * @return ラムダ式を使う都合で返しているだけ
     */
    public int loadPrevYear() {
        return changeDate(date.minusYears(1));
    }

    /**
     * 前月へ切り替え
     * 
     * @return ラムダ式を使う都合で返しているだけ
     */
    public int loadPrevMonth() {
        return changeDate(date.minusMonths(1));
    }

    /**
     * 前日へ切り替え
     * 
     * @return ラムダ式を使う都合で返しているだけ
     */
    public int loadPrevDay() {
        return changeDate(date.minusDays(1));
    }

    /**
     * 翌日へ切り替え
     * 
     * @return ラムダ式を使う都合で返しているだけ
     */
    public int loadNextDay() {
        return changeDate(date.plusDays(1));
    }

    /**
     * 翌月へ切り替え
     * 
     * @return ラムダ式を使う都合で返しているだけ
     */
    public int loadNextMonth() {
        return changeDate(date.plusMonths(1));
    }

    /**
     * 翌年へ切り替え
     * 
     * @return ラムダ式を使う都合で返しているだけ
     */
    public int loadNextYear() {
        return changeDate(date.plusYears(1));
    }

    /**
     * 現在の日付の記録情報を内部データへ退避
     */
    private void storeCurrentRecords() {
        String zone = "";
        List<MlRecord> records = new ArrayList<MlRecord>();
        for (Object[] record : currentRecords) {

            // 未入力行は退避しない
            if (UTL.isEmpty(record[1].toString()) || UTL.isEmpty(record[3].toString())) {
                continue;
            }

            // 時間帯は前の行に合せる
            if (UTL.isEmpty(zone)) {
                zone = record[0].toString();
            }
            if (UTL.isEmpty(record[0].toString())) {
                record[0] = zone;
            } else {
                zone = record[0].toString();
            }

            records.add(
                    new MlRecord(
                            UTL.toString(date),
                            record[0].toString(),
                            record[1].toString(),
                            record[2].toString(),
                            record[3].toString(),
                            record[4].toString(),
                            record[5].toString(),
                            record[6].toString(),
                            record[7].toString()
                    )
            );
        }
        if (internalRecords.containsKey(date)) {
            internalRecords.remove(date);
        }
        internalRecords.put(date, records);
    }

    /**
     * 画面の日合計と月平均の表示を更新
     */
    public void updateInfo() {
        List<BigDecimal> infos = new ArrayList<BigDecimal>();

        // streamで扱いやすいように記録情報リストに変換
        List<MlRecord> records = new ArrayList<MlRecord>();
        for (Object[] record : currentRecords) {

            // 未入力行は飛ばす
            if (UTL.isEmpty(record[1].toString()) || UTL.isEmpty(record[3].toString())) {
                continue;
            }

            records.add(
                    new MlRecord(
                            UTL.toString(date),
                            record[0].toString(),
                            record[1].toString(),
                            record[2].toString(),
                            record[3].toString(),
                            record[4].toString(),
                            record[5].toString(),
                            record[6].toString(),
                            record[7].toString()
                    )
            );
        }

        // 日合計
        infos.add(records.stream().map(MlRecord::getKcal).reduce(BigDecimal.ZERO, BigDecimal::add));
        infos.add(records.stream().map(MlRecord::getSalt).reduce(BigDecimal.ZERO, BigDecimal::add));

        // 月平均
        LocalDate str = LocalDate.of(date.getYear(), date.getMonthValue(), 1);
        LocalDate end = str.plusMonths(1).minusDays(1);
        for (var entry : internalRecords.entrySet()) {
            LocalDate dt = entry.getKey();
            if ((str.isBefore(dt) || str.isEqual(dt)) && (end.isAfter(dt) || end.isEqual(dt))) {
                records.addAll(entry.getValue());
            }
        }
        List<BigDecimal> kcalValues = records.stream().map(MlRecord::getKcal).collect(Collectors.toList());
        List<BigDecimal> saltValues = records.stream().map(MlRecord::getSalt).collect(Collectors.toList());
        infos.add(UTL.getAverage(kcalValues));
        infos.add(UTL.getAverage(saltValues));
        frame.updateInfo(infos);
    }

    @Override
    public int getRowCount() {
        return currentRecords.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return currentRecords.get(rowIndex)[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return currentRecords.get(0)[columnIndex].getClass();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMNS[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        currentRecords.get(rowIndex)[columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    @Override
    public void tableChanged(TableModelEvent e) {
        int col = e.getColumn();
        int row = e.getFirstRow();

        // 名前を入力したら単位を補完
        if (col == TABLE.NAME) {
            String name = getValueAt(row, col).toString();
            List<MlMaster> masters = frame.getMasters();
            MlMaster master = masters.stream().filter(x -> x.getName().equals(name)).findFirst().orElse(null);
            if (master != null) {
                setValueAt(master.getUnit(), row, TABLE.UNIT);
            } else {
                setValueAt("", row, TABLE.UNIT);
            }
        }

        // 数量を入力したら基準量を元にkcalと塩分を計算
        if (col == TABLE.MASS) {
            String name = getValueAt(row, TABLE.NAME).toString();
            String unit = getValueAt(row, TABLE.UNIT).toString();
            if (UTL.isEmpty(name) || UTL.isEmpty(unit)) {
                return;
            }
            List<MlMaster> masters = frame.getMasters();
            MlMaster master = masters.stream().filter(x -> x.getName().equals(name) && x.getUnit().equals(unit)).findFirst().orElse(null);
            if (master != null) {
                BigDecimal mass = UTL.toBigDecimal(getValueAt(row, col).toString());
                setValueAt(master.getKcal().multiply(mass).divide(master.getBase(), 1, RoundingMode.HALF_UP), row, TABLE.KCAL);
                setValueAt(master.getSalt().multiply(mass).divide(master.getBase(), 1, RoundingMode.HALF_UP), row, TABLE.SALT);
            }
        }

        // 画面の表示を更新
        updateInfo();
    }
}
