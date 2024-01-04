// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.common;

/**
 * 定数
 */
public class MlConstant {

    /**
     * コンストラクタ
     */
    private MlConstant() {
    }

    /** 記録情報ファイル */
    public static final String RECORD_FILE = "Mealog.db";

    /** マスタ情報ファイル */
    public static final String MASTER_FILE = "Master.db";

    /**
     * マーク
     */
    public static class MARK {
        /** ON */
        public static final String ON = "1";
        /** OFF */
        public static final String OFF = "0";
    }

    /**
     * 列インデックス
     */
    public static class COL {

        /** 記録情報テーブル */
        public static class RECORD {
            /** 日付 */
            public static final int DATE = 0;
            /** 時間帯 */
            public static final int ZONE = 1;
            /** 名前 */
            public static final int NAME = 2;
            /** 数量 */
            public static final int MASS = 3;
            /** 単位 */
            public static final int UNIT = 4;
            /** kcal */
            public static final int KCAL = 5;
            /** 塩分 */
            public static final int SALT = 6;
            /** 備考1 */
            public static final int NOTE1 = 7;
            /** 備考2 */
            public static final int NOTE2 = 8;
        }

        /** 実績情報テーブル */
        public static class RESULT {
            /** 年 */
            public static final int YEAR = 0;
            /** 1月 */
            public static final int MONTH01 = 1;
            /** 2月 */
            public static final int MONTH02 = 2;
            /** 3月 */
            public static final int MONTH03 = 3;
            /** 4月 */
            public static final int MONTH04 = 4;
            /** 5月 */
            public static final int MONTH05 = 5;
            /** 6月 */
            public static final int MONTH06 = 6;
            /** 7月 */
            public static final int MONTH07 = 7;
            /** 8月 */
            public static final int MONTH08 = 8;
            /** 9月 */
            public static final int MONTH09 = 9;
            /** 10月 */
            public static final int MONTH10 = 10;
            /** 11月 */
            public static final int MONTH11 = 11;
            /** 12月 */
            public static final int MONTH12 = 12;
            /** 年平均 */
            public static final int AVERAGE = 13;
        }

        /** マスタ情報テーブル */
        public static class MASTER {
            /** 名前 */
            public static final int NAME = 0;
            /** 基準量 */
            public static final int BASE = 1;
            /** 単位 */
            public static final int UNIT = 2;
            /** kcal */
            public static final int KCAL = 3;
            /** 塩分 */
            public static final int SALT = 4;
            /** リンク */
            public static final int LINK = 5;
        }
    }
}
