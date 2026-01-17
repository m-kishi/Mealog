package mealog.form.table.editor;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import mealog.form.table.model.MlRecordTableModel;

/**
 * 明細行入れ替えボタン
 */
public class MlRowMoveButtonCellEditor extends AbstractCellEditor implements TableCellEditor {

  /** 明細行インデックス */
  private int row;

  /** true:上ボタン false:下ボタン */
  private final boolean isUp;

  /** ボタン */
  private final JButton button;

  /** モデル */
  private final MlRecordTableModel model;

  /**
   * コンストラクタ
   * 
   * @param model モデル
   * @param isUp  true:上ボタン false:下ボタン
   */
  public MlRowMoveButtonCellEditor(MlRecordTableModel model, boolean isUp) {
    this.isUp = isUp;
    this.model = model;
    this.button = new JButton(isUp ? "↑" : "↓");
    button.addActionListener(e -> {
      if (this.isUp) {
        this.model.moveUp(row);
      } else {
        this.model.moveDown(row);
      }
      fireEditingStopped();
    });
  }

  @Override
  public Object getCellEditorValue() {
    return null;
  }

  @Override
  public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
    this.row = row;
    return button;
  }
}
