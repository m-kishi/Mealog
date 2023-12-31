// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.form.subform;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 画像サブフォーム
 */
public class MlSubformImage extends JDialog {

    /**
     * コンストラクタ
     * 
     * @param file ファイル
     */
    public MlSubformImage(String file) {

        // フォーム
        setTitle("画像");
        setSize(514, 458);
        setModal(true);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // コンポーネントの生成
        setComponent(file);
    }

    /**
     * コンポーネントの生成
     * 
     * @param file ファイル
     */
    private void setComponent(String file) {
        String filePath = "images/" + file;

        Image image = null;
        ImageIcon icon = null;

        Path path = Paths.get(filePath);
        if (Files.notExists(path)) {
            try {
                // エラー画像を読み込み
                ClassLoader loader = getClass().getClassLoader();
                BufferedImage bufferedImage = ImageIO.read(loader.getResource("mealog/resource/image/error.png"));
                image = bufferedImage.getScaledInstance(378, 378, Image.SCALE_SMOOTH);
            } catch (Exception ex) {
                // 無視
            }
        } else {

            // 画像を読み込み
            icon = new ImageIcon(filePath);

            // サイズ調整
            image = icon.getImage().getScaledInstance(504, -1, Image.SCALE_SMOOTH);
        }

        // 完了まで待機
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(image, 1);
            tracker.waitForAll();
        } catch (InterruptedException ex) {
            // 無視
        }

        // 調整した画像を読み込み
        ImageIcon resizedImage = new ImageIcon(image);

        setLayout(new BorderLayout());
        add(new JLabel(resizedImage), BorderLayout.CENTER);
    }
}
