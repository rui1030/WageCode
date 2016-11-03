package com.dffc.wp.util;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wangzg on 16/9/2.
 */
public final class DateTimeUtils {
    private static final String DEFAULT = "yyyy-MM-dd HH:mm:ss SSS";
    private static SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT);

    private DateTimeUtils() {
    }

    /**
     * get a adjust pattern SimpleDateFormat
     */
    public static SimpleDateFormat newFormatter(@Nullable String pattern) {
        if (TextUtils.isEmpty(pattern)) {
            pattern = DEFAULT;
        }
        String oldPattern = formatter.toPattern();
        if (!pattern.equals(oldPattern)) {
            try {
                formatter.applyPattern(pattern);
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return formatter;
    }

    /**
     * @see #DEFAULT
     */
    public static String format(long time) {
        return format(time, null);
    }

    public static String format(long time, @Nullable String pattern) {
        return newFormatter(pattern).format(new Date(time));
    }

    public static Date parse(@Nullable String datetime, @Nullable String pattern) throws ParseException {
        return newFormatter(pattern).parse(datetime);
    }
}
