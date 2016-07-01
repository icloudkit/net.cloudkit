/*
 * Copyright (C) 2015. The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cloudkit.flowportal.infrastructure.utilities;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2013年11月21日 下午3:01:14
 */
public class DateTimeUtility {

    public static final String DATE_FORMAT_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 当前前/后多少分钟的时间
     *
     * @param AfterOfBeforeMinute
     * @return
     */
    public static Date getDateTimeAfterOfBeforeMinute(Integer AfterOfBeforeMinute) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, AfterOfBeforeMinute);
        return calendar.getTime();
    }

    /**
     * 当前前/后多少小时的时间
     *
     * @param AfterOfBeforeHour
     * @return
     */
    public static Date getDateTimeAfterOfBeforeHour(Integer AfterOfBeforeHour) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY, AfterOfBeforeHour);
        return calendar.getTime();
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static Date getSpecifiedDayBefore(String specifiedDay, int beforeDay) {//可以用new Date().toLocalString()传递参数
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - beforeDay);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return c.getTime();
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static Date getSpecifiedDayAfter(String specifiedDay, int afterDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + afterDay);

        // String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return c.getTime();
    }

    public static Date formatDate(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.parse(date);
    }

    public static String formateDate(String pattern) throws ParseException {
        Date nowDate = new Date();
        return formatDate(nowDate,pattern);
    }

    public static String formatDate(Date date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

	public static String convertLongToDateString(String dateFormat,Long millSec){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date= new Date(millSec);
        return sdf.format(date);
   }

	public static Date convertLongToDate(Long millSec){
        Date date= new Date(millSec);
        return date;
   }

	public static Timestamp convertLongToTimestamp(Long millSec){
        return new Timestamp(millSec);
   }

	public static String convertLongToTimestampString(String dateFormat ,Long millSec){
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(millSec);
   }
}
