// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.table;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import mealog.common.MlConstant.COL;
import mealog.form.table.editor.MlGeneralNumberCellEditor;
import mealog.form.table.editor.MlGeneralStringCellEditor;
import mealog.form.table.model.MlMasterTableModel;
import mealog.form.table.renderer.MlGeneralNumberCellRenderer;
import mealog.form.table.renderer.MlGeneralStringCellRenderer;
import mealog.form.table.renderer.MlImageLinkCellRenderer;

/**
 * マスタ情報テーブル
 */
public class MlMasterTable extends JTable {

    /**
     * コンストラクタ
     * 
     * @param model     テーブルモデル
     * @param component 親の要素
     */
    public MlMasterTable(MlMasterTableModel model, Component component) {
        super(model);

        // テーブル設定
        setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 12));
        setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        TableColumnModel columns = getColumnModel();
        TableColumn colName = getColumnModel().getColumn(COL.MASTER.NAME);
        TableColumn colBase = getColumnModel().getColumn(COL.MASTER.BASE);
        TableColumn colUnit = getColumnModel().getColumn(COL.MASTER.UNIT);
        TableColumn colKcal = getColumnModel().getColumn(COL.MASTER.KCAL);
        TableColumn colSalt = getColumnModel().getColumn(COL.MASTER.SALT);
        TableColumn colFats = getColumnModel().getColumn(COL.MASTER.FATS);
        TableColumn colLink = getColumnModel().getColumn(COL.MASTER.LINK);

        TableCellRenderer rendererName = new MlGeneralStringCellRenderer();
        TableCellRenderer rendererBase = new MlGeneralNumberCellRenderer();
        TableCellRenderer rendererUnit = new MlGeneralStringCellRenderer(JLabel.CENTER);
        TableCellRenderer rendererKcal = new MlGeneralNumberCellRenderer();
        TableCellRenderer rendererSalt = new MlGeneralNumberCellRenderer();
        TableCellRenderer rendererFats = new MlGeneralNumberCellRenderer();
        TableCellRenderer rendererLink = new MlImageLinkCellRenderer(component);

        colName.setCellRenderer(rendererName);
        colBase.setCellRenderer(rendererBase);
        colUnit.setCellRenderer(rendererUnit);
        colKcal.setCellRenderer(rendererKcal);
        colSalt.setCellRenderer(rendererSalt);
        colFats.setCellRenderer(rendererFats);
        colLink.setCellRenderer(rendererLink);

        colName.setCellEditor(new MlGeneralStringCellEditor());
        colBase.setCellEditor(new MlGeneralNumberCellEditor());
        colUnit.setCellEditor(new MlGeneralStringCellEditor());
        colKcal.setCellEditor(new MlGeneralNumberCellEditor());
        colSalt.setCellEditor(new MlGeneralNumberCellEditor());
        colFats.setCellEditor(new MlGeneralNumberCellEditor());

        // 列幅設定
        columns.getColumn(COL.MASTER.NAME).setPreferredWidth(200);
        columns.getColumn(COL.MASTER.BASE).setPreferredWidth(60);
        columns.getColumn(COL.MASTER.UNIT).setPreferredWidth(60);
        columns.getColumn(COL.MASTER.KCAL).setPreferredWidth(80);
        columns.getColumn(COL.MASTER.SALT).setPreferredWidth(80);
        columns.getColumn(COL.MASTER.FATS).setPreferredWidth(80);
        columns.getColumn(COL.MASTER.LINK).setPreferredWidth(200);

        // マウスイベントの登録
        addMouseListener((MlImageLinkCellRenderer) rendererLink);
        addMouseMotionListener((MlImageLinkCellRenderer) rendererLink);

        // セル選択の設定
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setRowSelectionAllowed(true);
        setCellSelectionEnabled(true);

        // キー入力したらフォーカスをセルに設定する
        setSurrendersFocusOnKeystroke(true);
    }
}
