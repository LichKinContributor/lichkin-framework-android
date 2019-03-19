package javax.util;

import java.util.Locale;

/**
 * 重新实现读文件逻辑
 * @author SuZhou LichKin Information Technology Co., Ltd.
 */
public class ResourceBundle {

    private String fileName;
    private Locale locale;

    public ResourceBundle(String fileName, Locale locale) {
        this.fileName = fileName;
        this.locale = locale;
    }

    public static ResourceBundle getBundle(String fileName, Locale locale) {
        return new ResourceBundle(fileName, locale);
    }

    public String getString(String key) {
        switch (fileName) {
            case "dateTimeType": {
                switch (locale.toString()) {
                    case "zh_CN": {
                        switch (key) {
                            case "MILLISECOND":
                                return "SSS毫秒";
                            case "SECOND":
                                return "ss秒";
                            case "MINUTE":
                                return "mm分";
                            case "HOUR":
                                return "HH时";
                            case "DAY":
                                return "dd日";
                            case "MONTH":
                                return "MM月";
                            case "YEAR":
                                return "yyyy年";
                            case "TIMESTAMP":
                                return "yyyy年MM月dd日HH时mm分ss秒SSS毫秒";
                            case "TIMESTAMP_MIN":
                                return "yyyyMMddHHmmssSSS";
                            case "STANDARD":
                                return "yyyy年MM月dd日HH时mm分ss秒";
                            case "STANDARD_MIN":
                                return "yyyyMMddHHmmss";
                            case "yyyyMMddHHmm":
                                return "yyyy年MM月dd日HH时mm分";
                            case "yyyyMMddHHmm_MIN":
                                return "yyyyMMddHHmm";
                            case "yyyyMMddHH":
                                return "yyyy年MM月dd日HH时";
                            case "yyyyMMddHH_MIN":
                                return "yyyyMMddHH";
                            case "DATE_ONLY":
                                return "yyyy年MM月dd日";
                            case "DATE_ONLY_MIN":
                                return "yyyyMMdd";
                            case "yyyyMM":
                                return "yyyy年MM月";
                            case "yyyyMM_MIN":
                                return "yyyyMM_MIN";
                            case "TIME_MILLISECOND":
                                return "HH时mm分ss秒SSS毫秒";
                            case "TIME_MILLISECOND_MIN":
                                return "HHmmssSSS";
                            case "TIME_ONLY":
                                return "HH时mm分ss秒";
                            case "TIME_ONLY_MIN":
                                return "HHmmss";
                            case "HHmm":
                                return "HH时mm分";
                            case "HHmm_MIN":
                                return "HHmm";
                        }
                    }
                    break;
                    case "en": {
                        switch (key) {
                            case "MILLISECOND":
                                return "SSS";
                            case "SECOND":
                                return "ss";
                            case "MINUTE":
                                return "mm";
                            case "HOUR":
                                return "HH";
                            case "DAY":
                                return "dd";
                            case "MONTH":
                                return "MM";
                            case "YEAR":
                                return "yyyy";
                            case "TIMESTAMP":
                                return "yyyy-MM-dd HH:mm:ss.SSS";
                            case "TIMESTAMP_MIN":
                                return "yyyyMMddHHmmssSSS";
                            case "STANDARD":
                                return "yyyy-MM-dd HH:mm:ss";
                            case "STANDARD_MIN":
                                return "yyyyMMddHHmmss";
                            case "yyyyMMddHHmm":
                                return "yyyy-MM-dd HH:mm";
                            case "yyyyMMddHHmm_MIN":
                                return "yyyyMMddHHmm";
                            case "yyyyMMddHH":
                                return "yyyy-MM-dd HH";
                            case "yyyyMMddHH_MIN":
                                return "yyyyMMddHH";
                            case "DATE_ONLY":
                                return "yyyy-MM-dd";
                            case "DATE_ONLY_MIN":
                                return "yyyyMMdd";
                            case "yyyyMM":
                                return "yyyy-MM";
                            case "yyyyMM_MIN":
                                return "yyyyMM";
                            case "TIME_MILLISECOND":
                                return "HH:mm:ss.SSS";
                            case "TIME_MILLISECOND_MIN":
                                return "HHmmssSSS";
                            case "TIME_ONLY":
                                return "HH:mm:ss";
                            case "TIME_ONLY_MIN":
                                return "HHmmss";
                            case "HHmm":
                                return "HH:mm";
                            case "HHmm_MIN":
                                return "HHmm";
                        }
                    }
                    break;
                }
            }
            break;
        }
        return "";
    }
}
