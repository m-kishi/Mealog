// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * テキスト列
 */
public class MlGeneralStringCellRenderer extends DefaultTableCellRenderer {

    /** テキスト揃え */
    private int alignment;

    /**
     * コンストラクタ
     */
    public MlGeneralStringCellRenderer() {
        super();
        alignment = JLabel.LEFT;
    }

    /**
     * コンストラクタ
     * 
     * @param alignment テキスト揃え
     */
    public MlGeneralStringCellRenderer(int alignment) {
        super();
        this.alignment = alignment;
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {
        setHorizontalAlignment(alignment);
        return super.getTableCellRendererComponent(
                table,
                value,
                isSelected,
                hasFocus,
                row,
                column
        );
    }
}
