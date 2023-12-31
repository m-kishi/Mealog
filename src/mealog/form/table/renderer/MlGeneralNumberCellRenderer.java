// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.renderer;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import mealog.common.MlUtility.UTL;

/**
 * 数値列
 */
public class MlGeneralNumberCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column
    ) {
        setHorizontalAlignment(JLabel.RIGHT);
        return super.getTableCellRendererComponent(
                table,
                value == null ? "" : UTL.format(value),
                isSelected,
                hasFocus,
                row,
                column
        );
    }
}
