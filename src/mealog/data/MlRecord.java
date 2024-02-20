// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.data;

import java.math.BigDecimal;
import java.time.LocalDate;

import mealog.common.MlUtility.UTL;

/**
 * 記録情報
 */
public class MlRecord {

    /** マーク */
    private String mark;

    /** 日付 */
    private LocalDate date;

    /** 時間帯 */
    private String zone;

    /** 名前 */
    private String name;

    /** 数量 */
    private BigDecimal mass;

    /** 単位 */
    private String unit;

    /** kcal */
    private BigDecimal kcal;

    /** 塩分 */
    private BigDecimal salt;

    /** 備考1 */
    private String note1;

    /** 備考2 */
    private String note2;

    /**
     * コンストラクタ
     * 
     * @param mark  マーク
     * @param date  日付
     * @param zone  時間帯
     * @param name  名前
     * @param mass  数量
     * @param unit  単位
     * @param kcal  kcal
     * @param salt  塩分
     * @param note1 備考1
     * @param note2 備考2
     */
    public MlRecord(
            String mark,
            String date,
            String zone,
            String name,
            String mass,
            String unit,
            String kcal,
            String salt,
            String note1,
            String note2
    ) {
        this.mark = mark;
        this.date = UTL.toLocalDate(date);
        this.zone = zone;
        this.name = name;
        this.mass = UTL.toBigDecimal(mass);
        this.unit = unit;
        this.kcal = UTL.toBigDecimal(kcal);
        this.salt = UTL.toBigDecimal(salt);
        this.note1 = note1;
        this.note2 = note2;
    }

    /**
     * DBファイル出力形式(CSV)
     * 
     * @return DBファイル出力形式(CSV)
     */
    public String toDBFileFormat() {
        return String.format(
                "\"%s\",\"%s\",\"%s\",\"%s\",\"%.2f\",\"%s\",\"%.2f\",\"%.2f\",\"%s\",\"%s\"",
                mark,
                UTL.toString(date),
                zone,
                name,
                mass,
                unit,
                kcal,
                salt,
                note1,
                note2
        );
    }

    /**
     * getter(マーク)
     * 
     * @return
     */
    public String getMark() {
        return mark;
    }

    /**
     * getter(日付)
     * 
     * @return 日付
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * getter(時間帯)
     * 
     * @return 時間帯
     */
    public String getZone() {
        return zone;
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
     * getter(数量)
     * 
     * @return 数量
     */
    public BigDecimal getMass() {
        return mass;
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
     * getter(塩分)
     * 
     * @return 塩分
     */
    public BigDecimal getSalt() {
        return salt;
    }

    /**
     * getter(備考1)
     * 
     * @return 備考1
     */
    public String getNote1() {
        return note1;
    }

    /**
     * getter(備考2)
     * 
     * @return 備考2
     */
    public String getNote2() {
        return note2;
    }
}
