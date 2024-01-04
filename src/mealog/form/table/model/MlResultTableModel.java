// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;

import mealog.common.MlConstant.MARK;
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

        // 集計対象のみに絞り込み
        List<MlRecord> filtered = records.stream().filter(x -> x.getMark().equals(MARK.ON)).collect(Collectors.toList());

        // 年ごとにグループ化
        var groupByYear = filtered.stream().collect(Collectors.groupingBy(x -> x.getDate().getYear()));
        for (var entry : groupByYear.entrySet()) {
            Object[] result = createNewRow(entry.getKey());

            // 月ごとにグループ化
            var groupByMonth = entry.getValue().stream().collect(Collectors.groupingBy(x -> x.getDate().getMonthValue()));

            // 月ごとの平均を計算
            BigDecimal count = BigDecimal.ZERO;
            BigDecimal total = BigDecimal.ZERO;
            for (int month = 1; month <= 12; month++) {
                if (groupByMonth.containsKey(month)) {
                    result[month] = UTL.getAverage(groupByMonth.get(month), type);
                    count = count.add(BigDecimal.ONE);
                    total = total.add((BigDecimal) result[month]);
                }
            }

            // 年の平均を計算
            result[COLUMNS.length - 1] = (count.equals(BigDecimal.ZERO)) ? BigDecimal.ZERO : total.divide(count, 1, RoundingMode.HALF_UP);

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
