// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.data;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import mealog.common.MlUtility.UTL;

/**
 * マスタ情報
 */
public class MlMaster {

    /** 名前 */
    private String name;

    /** 基準量 */
    private BigDecimal base;

    /** 単位 */
    private String unit;

    /** kcal */
    private BigDecimal kcal;

    /** タンパク質 */
    private BigDecimal prtn;

    /** 脂質 */
    private BigDecimal fats;

    /** 塩分 */
    private BigDecimal salt;

    /** リンク */
    private String link;

    /**
     * コンストラクタ
     * v1.3.0 より前のバージョンで利用
     * 
     * @param name 名前
     * @param base 基準量
     * @param unit 単位
     * @param kcal kcal
     * @param salt 塩分
     */
    public MlMaster(String name, String base, String unit, String kcal, String salt) {
        this.name = name;
        this.base = UTL.toBigDecimal(base);
        this.unit = unit;
        this.kcal = UTL.toBigDecimal(kcal);
        this.prtn = BigDecimal.ZERO;
        this.fats = BigDecimal.ZERO;
        this.salt = UTL.toBigDecimal(salt);
        this.link = decideExtention(name);
    }

    /**
     * コンストラクタ
     * v1.3.0 〜 1.3.1 で利用
     * 
     * @param name 名前
     * @param base 基準量
     * @param unit 単位
     * @param kcal kcal
     * @param salt 塩分
     * @param fats 脂質
     */
    public MlMaster(String name, String base, String unit, String kcal, String salt, String fats) {
        this.name = name;
        this.base = UTL.toBigDecimal(base);
        this.unit = unit;
        this.kcal = UTL.toBigDecimal(kcal);
        this.prtn = BigDecimal.ZERO;
        this.fats = UTL.toBigDecimal(fats);
        this.salt = UTL.toBigDecimal(salt);
        this.link = decideExtention(name);
    }

    /**
     * コンストラクタ
     * v1.4.0 以降のバージョンで利用
     * 
     * @param name 名前
     * @param base 基準量
     * @param unit 単位
     * @param kcal kcal
     * @param prtn タンパク質
     * @param fats 脂質
     * @param salt 塩分
     */
    public MlMaster(String name, String base, String unit, String kcal, String prtn, String fats, String salt) {
        this.name = name;
        this.base = UTL.toBigDecimal(base);
        this.unit = unit;
        this.kcal = UTL.toBigDecimal(kcal);
        this.prtn = UTL.toBigDecimal(prtn);
        this.fats = UTL.toBigDecimal(fats);
        this.salt = UTL.toBigDecimal(salt);
        this.link = decideExtention(name);
    }

    /**
     * リンクの決定
     * 
     * @param name 名前
     * @return 拡張子付きリンク
     */
    private String decideExtention(String name) {
        String[] extensions = { ".png", ".jpg" };
        for (String ext : extensions) {
            String link = name + ext;
            Path path = Paths.get("images/" + link);
            if (Files.exists(path)) {
                return link;
            }
        }
        return "";
    }

    /**
     * マスタファイル出力形式(CSV)
     * 
     * @return マスタファイル出力形式(CSV)
     */
    public String toMasterFileFormat() {
        return String.format(
                "\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
                name,
                UTL.format(base),
                unit,
                UTL.format(kcal),
                UTL.format(prtn),
                UTL.format(fats),
                UTL.format(salt)
        );
    }

    /**
     * getter(名前)
     * 
     * @return 名前
     */
    public String getName() {
        return name;
    }

    /**
     * getter(基準量)
     * 
     * @return 基準量
     */
    public BigDecimal getBase() {
        return base;
    }

    /**
     * getter(単位)
     * 
     * @return 単位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * getter(kcal)
     * 
     * @return kcal
     */
    public BigDecimal getKcal() {
        return kcal;
    }

    /**
     * タンパク質
     * 
     * @return タンパク質
     */
    public BigDecimal getPrtn() {
        return prtn;
    }

    /**
     * getter(脂質)
     * 
     * @return 脂質
     */
    public BigDecimal getFats() {
        return fats;
    }

    /**
     * getter(塩分)
     * 
     * @return 塩分
     */
    public BigDecimal getSalt() {
        return salt;
    }

    /**
     * getter(リンク)
     * 
     * @return リンク
     */
    public String getLink() {
        return link;
    }
}
