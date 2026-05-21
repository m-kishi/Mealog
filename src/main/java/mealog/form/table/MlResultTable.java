// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import mealog.form.table.model.MlResultTableModel;
import mealog.form.table.renderer.MlGeneralNumberCellRenderer;
import mealog.form.table.renderer.MlGeneralStringCellRenderer;

/**
 * 実績情報テーブル
 */
public class MlResultTable extends JTable {

    /**
     * コンストラクタ
     * 
     * @param model テーブルモデル
     */
    public MlResultTable(MlResultTableModel model) {
        super(model);

        // テーブル設定
        setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // カラムモデル
        TableColumnModel columns = getColumnModel();

        // 年列の設定
        TableColumn colYear = columns.getColumn(0);
        colYear.setCellRenderer(new MlGeneralStringCellRenderer(JLabel.CENTER));
        columns.getColumn(0).setPreferredWidth(50);

        // 各月の設定(12ヶ月+年平均)
        for (int i = 1; i <= 13; i++) {
            TableColumn col = columns.getColumn(i);
            col.setCellRenderer(new MlGeneralNumberCellRenderer());
            columns.getColumn(i).setPreferredWidth(60);
        }
    }
}
