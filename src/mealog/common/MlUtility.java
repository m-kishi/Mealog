// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * ユーティリティ
 */
public class MlUtility {

    /**
     * コンストラクタ
     */
    private MlUtility() {
    }

    /**
     * 便利メソッド
     */
    public static class UTL {

        /**
         * 空文字判定
         * 
         * @param text 文字列
         * @return true:(NULL or Empty) false:空文字でない
         */
        public static boolean isEmpty(String text) {
            return (text == null || text.isEmpty());
        }

        /**
         * 日付へ変換
         * 
         * @param date 日付
         * @return 日付
         */
        public static LocalDate toLocalDate(String date) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        /**
         * 文字列へ変換
         * 
         * @param date 日付
         * @return yyyy-MM-dd
         */
        public static String toString(LocalDate date) {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }

        /**
         * BigDecimalへ変換
         * 
         * @param value 値
         * @return BigDecimal
         */
        public static BigDecimal toBigDecimal(String value) {
            return new BigDecimal(value);
        }

        /**
         * BigDecimalのフォーマット
         * 
         * @param value 値
         * @return #,##0.0
         */
        public static String format(Object value) {
            DecimalFormat df = new DecimalFormat("#,##0.0");
            return df.format(value);
        }

        /**
         * タイトルへ変換
         * 
         * @param date 日付
         * @return yyyy年MM月dd日(E)
         */
        public static String toTitle(LocalDate date) {
            return date.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日(E)", Locale.JAPANESE));
        }

        /**
         * 平均を計算
         * 
         * @param values 値リスト
         * @return 平均
         */
        public static BigDecimal getAverage(List<BigDecimal> values) {
            BigDecimal count = new BigDecimal(values.size());
            BigDecimal total = values.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
            return (count.equals(BigDecimal.ZERO)) ? BigDecimal.ZERO : total.divide(count, 1, RoundingMode.HALF_UP);
        }
    }

    /**
     * メッセージダイアログ
     */
    public static class MSG {

        /**
         * OKダイアログ
         * 
         * @param frame   フレーム
         * @param title   タイトル
         * @param message メッセージ
         */
        public static void ok(JFrame frame, String title, String message) {
            JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
        }

        /**
         * エラーダイアログ
         * 
         * @param frame   フレーム
         * @param message メッセージ
         */
        public static void error(JFrame frame, String message) {
            JOptionPane.showMessageDialog(frame, message, "エラー", JOptionPane.ERROR_MESSAGE);
        }
    }
}
