// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import mealog.form.table.editor.MlGeneralNumberCellEditor;
import mealog.form.table.editor.MlGeneralStringCellEditor;
import mealog.form.table.model.MlRecordTableModel;
import mealog.form.table.renderer.MlGeneralNumberCellRenderer;
import mealog.form.table.renderer.MlGeneralStringCellRenderer;

/**
 * 記録情報テーブル
 */
public class MlRecordTable extends JTable {

    /**
     * 列インデックス
     * 表示上は日付列がないため「MlConstant.COL.RECORD」から1つずつずれる
     */
    public static class TABLE {
        public static final int ZONE = 0;
        public static final int NAME = 1;
        public static final int MASS = 2;
        public static final int UNIT = 3;
        public static final int KCAL = 4;
        public static final int PRTN = 5;
        public static final int FATS = 6;
        public static final int SALT = 7;
        public static final int NOTE = 8;
    }

    /**
     * コンストラクタ
     * 
     * @param model テーブルモデル
     */
    public MlRecordTable(MlRecordTableModel model) {
        super(model);

        // テーブル設定
        setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columns = getColumnModel();
        TableColumn colZone = columns.getColumn(TABLE.ZONE);
        TableColumn colName = columns.getColumn(TABLE.NAME);
        TableColumn colMass = columns.getColumn(TABLE.MASS);
        TableColumn colUnit = columns.getColumn(TABLE.UNIT);
        TableColumn colKcal = columns.getColumn(TABLE.KCAL);
        TableColumn colPrtn = columns.getColumn(TABLE.PRTN);
        TableColumn colFats = columns.getColumn(TABLE.FATS);
        TableColumn colSalt = columns.getColumn(TABLE.SALT);
        TableColumn colNote = columns.getColumn(TABLE.NOTE);

        colZone.setCellRenderer(new MlGeneralStringCellRenderer(JLabel.CENTER));
        colName.setCellRenderer(new MlGeneralStringCellRenderer());
        colMass.setCellRenderer(new MlGeneralNumberCellRenderer());
        colUnit.setCellRenderer(new MlGeneralStringCellRenderer(JLabel.CENTER));
        colKcal.setCellRenderer(new MlGeneralNumberCellRenderer());
        colPrtn.setCellRenderer(new MlGeneralNumberCellRenderer());
        colFats.setCellRenderer(new MlGeneralNumberCellRenderer());
        colSalt.setCellRenderer(new MlGeneralNumberCellRenderer());
        colNote.setCellRenderer(new MlGeneralStringCellRenderer());

        colZone.setCellEditor(new MlGeneralStringCellEditor());
        colName.setCellEditor(new MlGeneralStringCellEditor());
        colMass.setCellEditor(new MlGeneralNumberCellEditor());
        colUnit.setCellEditor(new MlGeneralStringCellEditor());
        colKcal.setCellEditor(new MlGeneralNumberCellEditor());
        colPrtn.setCellEditor(new MlGeneralNumberCellEditor());
        colFats.setCellEditor(new MlGeneralNumberCellEditor());
        colSalt.setCellEditor(new MlGeneralNumberCellEditor());
        colNote.setCellEditor(new MlGeneralStringCellEditor());

        // 列幅設定
        columns.getColumn(TABLE.ZONE).setPreferredWidth(60);
        columns.getColumn(TABLE.NAME).setPreferredWidth(200);
        columns.getColumn(TABLE.MASS).setPreferredWidth(60);
        columns.getColumn(TABLE.UNIT).setPreferredWidth(60);
        columns.getColumn(TABLE.KCAL).setPreferredWidth(80);
        columns.getColumn(TABLE.PRTN).setPreferredWidth(80);
        columns.getColumn(TABLE.FATS).setPreferredWidth(80);
        columns.getColumn(TABLE.SALT).setPreferredWidth(80);
        columns.getColumn(TABLE.NOTE).setPreferredWidth(220);

        // セル選択の設定
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setRowSelectionAllowed(true);
        setCellSelectionEnabled(true);

        // キー入力したらフォーカスをセルに設定する
        setSurrendersFocusOnKeystroke(true);
    }

    @Override
    public boolean editCellAt(int row, int column, EventObject e) {
        boolean result = super.editCellAt(row, column, e);
        if (column != TABLE.MASS && column != TABLE.KCAL && column != TABLE.SALT && column != TABLE.FATS) {
            return result;
        }
        // 数量・kcal・たんぱく質・塩分・脂質は編集開始時に全選択する
        if (result) {
            if (e instanceof KeyEvent) {
                KeyEvent event = (KeyEvent) e;
                char c = event.getKeyChar();
                if (!Character.isISOControl(c)) {
                    if (super.editorComp instanceof JTextField) {
                        JTextField field = (JTextField) super.editorComp;
                        field.selectAll();
                    }
                }
            }
        }
        return result;
    }
}
