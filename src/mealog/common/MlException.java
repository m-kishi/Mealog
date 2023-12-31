// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.common;

/**
 * 例外
 */
public class MlException extends Exception {

    /**
     * コンストラクタ
     * 
     * @param ex 例外
     */
    public MlException(Exception ex) {
        super(ex);
    }

    /**
     * コンストラクタ
     * 
     * @param message メッセージ
     */
    public MlException(String message) {
        super(message);
    }
}
