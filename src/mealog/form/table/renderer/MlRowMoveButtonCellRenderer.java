package mealog.form.table.renderer;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 明細行入れ替えボタン列
 */
public class MlRowMoveButtonCellRenderer extends JButton implements TableCellRenderer {

  /**
   * コンストラクタ
   * 
   * @param text ボタンラベル
   */
  public MlRowMoveButtonCellRenderer(String text) {
    setText(text);
    setFocusPainted(false);
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
    return this;
  }
}
