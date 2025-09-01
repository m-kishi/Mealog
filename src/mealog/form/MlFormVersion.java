// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * バージョン情報
 */
public class MlFormVersion extends JDialog {

    /** アプリ */
    public static final String APP_NAME = "Mealog";

    /** タイトル */
    public static final String TITLE = "Mealogのバージョン情報";

    /** バージョン */
    public static final String VERSION = "v1.4.1";

    /** コピーライト */
    public static final String COPYRIGHT = "© 2023 https://github.com/m-kishi";

    /** アプリの説明 */
    public static final String DESCRIPTION = "日々の食事を記録するための個人的なツール";

    /**
     * コンストラクタ
     */
    public MlFormVersion() {
        super();

        // フォーム
        setTitle(TITLE);
        setModal(true);
        setSize(360, 112);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // バージョン情報の表示エリア
        SpringLayout layout = new SpringLayout();
        JPanel panel = new JPanel(layout);

        // アイコン
        ImageIcon icon = null;
        try {
            ClassLoader loader = getClass().getClassLoader();
            BufferedImage bufferedImage = ImageIO.read(loader.getResourceAsStream("mealog/resource/image/mealog.png"));
            icon = new ImageIcon(bufferedImage.getScaledInstance(52, 52, Image.SCALE_SMOOTH));
        } catch (Exception ex) {
            // 無視
        }
        JLabel lblIcon = new JLabel(icon);
        panel.add(lblIcon);

        // アプリ
        VersionLabel lblAppName = new VersionLabel(APP_NAME);
        panel.add(lblAppName);

        // バージョン
        VersionLabel lblVersion = new VersionLabel(VERSION);
        panel.add(lblVersion);

        // コピーライト
        VersionLabel lblCopyright = new VersionLabel(COPYRIGHT);
        panel.add(lblCopyright);

        // アプリの説明
        VersionLabel lblDescription = new VersionLabel(DESCRIPTION);
        panel.add(lblDescription);

        // レイアウトの配置設定
        layout.putConstraint(SpringLayout.NORTH, lblIcon, 5, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, lblIcon, 10, SpringLayout.WEST, panel);
        layout.putConstraint(SpringLayout.NORTH, lblAppName, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, lblAppName, 10, SpringLayout.EAST, lblIcon);
        layout.putConstraint(SpringLayout.NORTH, lblVersion, 10, SpringLayout.NORTH, panel);
        layout.putConstraint(SpringLayout.WEST, lblVersion, 8, SpringLayout.EAST, lblAppName);
        layout.putConstraint(SpringLayout.NORTH, lblCopyright, 2, SpringLayout.SOUTH, lblVersion);
        layout.putConstraint(SpringLayout.WEST, lblCopyright, 10, SpringLayout.EAST, lblIcon);
        layout.putConstraint(SpringLayout.NORTH, lblDescription, 2, SpringLayout.SOUTH, lblCopyright);
        layout.putConstraint(SpringLayout.WEST, lblDescription, 10, SpringLayout.EAST, lblIcon);

        add(panel, BorderLayout.CENTER);
    }

    /**
     * カスタムラベル
     */
    private class VersionLabel extends JLabel {

        /**
         * コンストラクタ
         * 
         * @param text テキスト
         */
        public VersionLabel(String text) {
            super(text);
            setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        }
    }
}
