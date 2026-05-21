// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table.renderer;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.event.MouseInputListener;
import javax.swing.table.DefaultTableCellRenderer;

import mealog.common.MlConstant.COL;
import mealog.common.MlUtility.UTL;
import mealog.form.subform.MlSubformImage;

/**
 * 画像リンク列
 */
public class MlImageLinkCellRenderer extends DefaultTableCellRenderer implements MouseInputListener {

    /** 親の要素 */
    private Component component;

    /**
     * コンストラクタ
     * 
     * @param component 親の要素
     */
    public MlImageLinkCellRenderer(Component component) {
        super();
        this.component = component;
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
        return super.getTableCellRendererComponent(
                table,
                "<html><u><font color='blue'>" + value.toString(),
                isSelected,
                hasFocus,
                row,
                column
        );
    }

    /**
     * カーソルの状態を設定
     * 
     * @param e イベント
     */
    private void setCurrentCursor(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        String text = table.getValueAt(row, col).toString();
        int type = (col == COL.MASTER.LINK && !UTL.isEmpty(text)) ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR;
        component.setCursor(Cursor.getPredefinedCursor(type));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JTable table = (JTable) e.getSource();
        int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
        String file = table.getValueAt(row, col).toString();
        if (!UTL.isEmpty(file)) {
            MlSubformImage frame = new MlSubformImage(file);
            frame.setVisible(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setCurrentCursor(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setCurrentCursor(e);
    }
}
