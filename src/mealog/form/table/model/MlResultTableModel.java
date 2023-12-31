// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import mealog.common.MlUtility.UTL;
import mealog.data.MlRecord;

/**
 * 実績情報テーブルモデル
 */
public class MlResultTableModel extends AbstractTableModel {

    /** 実績区分 */
    public enum TYPE {
        KCAL,
        SALT,
    };

    /** 内部データ */
    private List<Object[]> results;

    /** 列ヘッダ定義 */
    private static final String[] COLUMNS = {
            "年",
            "1月",
            "2月",
            "3月",
            "4月",
            "5月",
            "6月",
            "7月",
            "8月",
            "9月",
            "10月",
            "11月",
            "12月",
            "平均",
    };

    /**
     * コンストラクタ
     * 
     * @param records 記録情報リスト
     * @param type    実績区分
     */
    public MlResultTableModel(List<MlRecord> records, TYPE type) {
        super();

        // 内部データ初期化
        results = new ArrayList<Object[]>();

        // 年ごとにグループ化
        var groupByYear = records.stream().collect(Collectors.groupingBy(x -> x.getDate().getYear()));
        for (var entry : groupByYear.entrySet()) {
            Object[] result = createNewRow(entry.getKey());

            // 月ごとにグループ化
            var groupByMonth = entry.getValue().stream().collect(Collectors.groupingBy(x -> x.getDate().getMonthValue()));

            // 月ごとの平均を計算
            for (int month = 1; month <= 12; month++) {
                if (groupByMonth.containsKey(month)) {
                    List<BigDecimal> values = new ArrayList<BigDecimal>();
                    if (type == TYPE.KCAL) {
                        values = groupByMonth.get(month).stream().map(MlRecord::getKcal).collect(Collectors.toList());
                    }
                    if (type == TYPE.SALT) {
                        values = groupByMonth.get(month).stream().map(MlRecord::getSalt).collect(Collectors.toList());
                    }
                    result[month] = UTL.getAverage(values);
                }
            }

            // 年の平均を計算
            List<BigDecimal> values = new ArrayList<BigDecimal>();
            if (type == TYPE.KCAL) {
                values = entry.getValue().stream().map(MlRecord::getKcal).collect(Collectors.toList());
            }
            if (type == TYPE.SALT) {
                values = entry.getValue().stream().map(MlRecord::getSalt).collect(Collectors.toList());
            }
            result[COLUMNS.length - 1] = UTL.getAverage(values);

            results.add(result);
        }
    }

    /**
     * 新規行の生成
     * 
     * @param year 年
     * @return 新規行
     */
    private Object[] createNewRow(int year) {
        return new Object[] {
                year,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
        };
    }

    @Override
    public int getRowCount() {
        return results.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return results.get(rowIndex)[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return results.get(0)[columnIndex].getClass();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMNS[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
}
