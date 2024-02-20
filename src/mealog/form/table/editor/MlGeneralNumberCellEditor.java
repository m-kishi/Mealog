// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.editor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import mealog.common.MlUtility.UTL;

/**
 * 数値のエディタ
 */
public class MlGeneralNumberCellEditor extends DefaultCellEditor {

    /** 数値 */
    private BigDecimal value = null;

    /**
     * コンストラクタ
     */
    public MlGeneralNumberCellEditor() {
        super(new JTextField());

        // クリックで編集開始
        setClickCountToStart(1);

        JTextField field = (JTextField) getComponent();
        field.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        field.setBorder(BorderFactory.createEmptyBorder());
        field.setHorizontalAlignment(JLabel.RIGHT);
    }

    /**
     * 入力された値を返す
     */
    @Override
    public Object getCellEditorValue() {
        return value;
    }

    /**
     * 編集開始時に呼ばれる
     */
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

        // 初期化
        this.value = null;

        // 枠は不要
        ((JComponent) getComponent()).setBorder(BorderFactory.createEmptyBorder());

        // 文字列として表示
        String text = UTL.isEmpty(String.valueOf(value)) ? "" : UTL.format(value);
        Component component = super.getTableCellEditorComponent(table, text, isSelected, row, column);

        // このタイミングで全選択(フォーカス取得時では上手くいかない)
        if (component instanceof JTextField) {
            JTextField field = (JTextField) component;
            field.selectAll();
            return field;
        }

        return component;
    }

    /**
     * 編集完了時に呼ばれる
     * super.stopCellEditing()を呼び出すと正常終了となる
     */
    @Override
    public boolean stopCellEditing() {

        // 編集された値を取得
        String text = String.valueOf(super.getCellEditorValue());

        // BigDecimalへ変換できるか確認
        if (!UTL.isEmpty(text)) {
            try {
                text = text.replaceAll(",", "");
                value = new BigDecimal(text);
            } catch (NumberFormatException ex) {
                // エラーなら赤枠を表示して編集を完了させない
                ((JComponent) getComponent()).setBorder(new LineBorder(Color.RED));
                return false;
            }
        }

        return super.stopCellEditing();
    }
}
