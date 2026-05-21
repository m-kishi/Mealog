// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog;

import java.awt.Font;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import mealog.common.MlConstant;
import mealog.common.MlUtility.MSG;
import mealog.form.MlFormRecord;

/**
 * アプリケーション
 */
public class App {

    /**
     * メインメソッド
     * 
     * @param args 引数
     */
    public static void main(String[] args) {
        try {

            // swingデフォルトのデザインを設定
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

            // テーブルヘッダのフォントはここで設定
            Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 12);
            UIManager.put("TableHeader.font", new FontUIResource(font));

            // 記録フォームを表示
            MlFormRecord frame = new MlFormRecord(MlConstant.RECORD_FILE, MlConstant.MASTER_FILE);
            frame.setVisible(true);

        } catch (Exception ex) {
            MSG.error(null, ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
    }
}
