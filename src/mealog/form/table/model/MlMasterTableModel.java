// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import mealog.common.MlConstant.COL;
import mealog.common.MlUtility.UTL;
import mealog.data.MlMaster;

/**
 * マスタ情報テーブルモデル
 */
public class MlMasterTableModel extends AbstractTableModel {

    /** 内部データ */
    private List<Object[]> masters;

    /** 列ヘッダ定義 */
    private static final String[] COLUMNS = {
            "名前",
            "基準量",
            "単位",
            "kcal",
            "塩分",
            "画像",
    };

    /**
     * コンストラクタ
     * 
     * @param masters マスタ情報リスト
     */
    public MlMasterTableModel(List<MlMaster> masters) {
        super();
        load(masters);
    }

    /**
     * マスタ情報の読み込み
     * 
     * @param masters マスタ情報
     */
    public void load(List<MlMaster> masters) {
        if (this.masters == null) {
            this.masters = new ArrayList<Object[]>();
        } else {
            this.masters.clear();
        }
        for (MlMaster master : masters) {
            this.masters.add(new Object[] {
                    master.getName(),
                    master.getBase(),
                    master.getUnit(),
                    master.getKcal(),
                    master.getSalt(),
                    master.getLink(),
            });
        }
        fireTableDataChanged();
    }

    /**
     * 行追加
     */
    public void addRow() {
        for (int i = 0; i < 10; i++) {
            masters.add(new Object[] { "", BigDecimal.ZERO, "", BigDecimal.ZERO, BigDecimal.ZERO, "" });
        }
        fireTableRowsInserted(0, masters.size() - 1);
    }

    /**
     * マスタ情報リストを取得
     * 
     * @return マスタ情報リスト
     */
    public List<MlMaster> getMasters() {
        List<MlMaster> masters = new ArrayList<MlMaster>();
        for (Object[] master : this.masters) {

            // 未入力行は飛ばす
            if (UTL.isEmpty(master[0].toString()) || UTL.isEmpty(master[2].toString())) {
                continue;
            }

            masters.add(
                    new MlMaster(
                            master[0].toString(),
                            master[1].toString(),
                            master[2].toString(),
                            master[3].toString(),
                            master[4].toString()
                    )
            );
        }
        return masters;
    }

    @Override
    public int getRowCount() {
        return masters.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return masters.get(rowIndex)[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return masters.get(0)[columnIndex].getClass();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMNS[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != COL.MASTER.LINK;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        masters.get(rowIndex)[columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
