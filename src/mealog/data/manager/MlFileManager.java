// ------------------------------------------------------------
// © 2023 https://github.com/m-kishi
// ------------------------------------------------------------
package mealog.data.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import mealog.common.MlException;
import mealog.data.MlMaster;
import mealog.data.MlRecord;

/**
 * ファイル入出力マネージャ
 */
public class MlFileManager {

    /**
     * コンストラクタ
     */
    private MlFileManager() {
    }

    /**
     * 抽象化した読み込み
     * 
     * @param <T>    読み込む型
     * @param file   ファイル
     * @param action インスタンス化アクション
     * @return 読み込んだ情報のリスト
     * @throws MlException
     */
    public static <T> List<T> load(String file, Function<CSVRecord, T> action) throws MlException {

        // ファイルが存在しないなら新規作成
        Path path = Paths.get(file);
        if (Files.notExists(path)) {
            try {
                Files.createFile(path);
            } catch (Exception ex) {
                throw new MlException(ex);
            }
        }

        // ファイル読み込み
        List<T> results = new ArrayList<T>();
        try {
            Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            Iterable<CSVRecord> csvRecords = CSVFormat.DEFAULT.parse(reader);
            for (CSVRecord record : csvRecords) {
                results.add(action.apply(record));
            }
        } catch (Exception ex) {
            throw new MlException(ex);
        }

        return results;
    }

    /**
     * 記録情報読み込み
     * 
     * @param file ファイル
     * @return 記録情報リスト
     * @throws MlException
     */
    public static List<MlRecord> loadRecord(String file) throws MlException {
        return load(file, (record) -> {
            if (record.size() < 11) {
                return new MlRecord(
                        record.get(0),
                        record.get(1),
                        record.get(2),
                        record.get(3),
                        record.get(4),
                        record.get(5),
                        record.get(6),
                        record.get(7),
                        record.get(8),
                        record.get(9)
                );
            } else {
                return new MlRecord(
                        record.get(0),
                        record.get(1),
                        record.get(2),
                        record.get(3),
                        record.get(4),
                        record.get(5),
                        record.get(6),
                        record.get(7),
                        record.get(8),
                        record.get(9),
                        record.get(10)
                );
            }
        });
    }

    /**
     * マスタ情報読み込み
     * 
     * @param file ファイル
     * @return マスタ情報リスト
     * @throws MlException
     */
    public static List<MlMaster> loadMaster(String file) throws MlException {
        return load(file, (record) -> {
            if (record.size() < 6) {
                return new MlMaster(
                        record.get(0),
                        record.get(1),
                        record.get(2),
                        record.get(3),
                        record.get(4)
                );
            } else {
                return new MlMaster(
                        record.get(0),
                        record.get(1),
                        record.get(2),
                        record.get(3),
                        record.get(4),
                        record.get(5)
                );
            }
        });
    }

    /**
     * 抽象化した書き出し
     * 
     * @param <T>     読み込む型
     * @param file    ファイル
     * @param records 書き出す情報のリスト
     * @param action  フォーマットアクション
     * @throws MlException
     */
    private static <T> void store(String file, List<T> records, Function<T, String> action) throws MlException {
        try {
            PrintWriter pw = new PrintWriter(
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"))
            );
            for (T record : records) {
                pw.println(action.apply(record));
            }
            pw.close();
        } catch (IOException ex) {
            throw new MlException(ex);
        }
    }

    /**
     * 記録情報書き出し
     * 
     * @param file    ファイル
     * @param records 記録情報リスト
     * @throws MlException
     */
    public static void storeRecord(String file, List<MlRecord> records) throws MlException {
        store(file, records, (x) -> {
            return x.toDBFileFormat();
        });
    }

    /**
     * マスタ情報書き出し
     * 
     * @param file    ファイル
     * @param masters マスタ情報リスト
     * @throws MlException
     */
    public static void storeMaster(String file, List<MlMaster> masters) throws MlException {
        store(file, masters, (x) -> {
            return x.toMasterFileFormat();
        });
    }
}
