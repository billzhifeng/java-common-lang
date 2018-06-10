package com.github.java.common.utils;

import java.util.Calendar;
import java.util.Date;

/**
 */
public class JavaDateUtils {

    /**
     * 计算还款日
     * 
     * @return
     */
    public static Date getRepayDate(Integer billDay, Integer repayDay) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, repayDay);
        if (repayDay < billDay) {
            //月加1
            cal.add(Calendar.MONTH, 1);
        }
        return cal.getTime();
    }

    /**
     * 计算滞纳金开始日期
     * 
     * @return
     */
    public static Date getPenaltyStartDate(Date repayDate, Integer penaltyFreeDays) {
        if (null == penaltyFreeDays) {
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(repayDate);
        cal.add(Calendar.DAY_OF_MONTH, penaltyFreeDays + 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        return cal.getTime();
    }

    /**
     * 计算账单日
     * 
     * @return
     */
    public static Date getBillDate(Integer billDay) {
        Calendar c = Calendar.getInstance();
        Integer today = c.get(Calendar.DAY_OF_MONTH);
        if (today == billDay) {
            return new Date();
        }
        //非当日生成账单,如账单日故障未生成
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, billDay);
        return cal.getTime();
    }

    /**
     * 获取上一个账单开始时间(>=该时间)
     * 
     * @param currentBillDate 当前账单日
     * @return
     */
    public static Date getPreBillStartDate(Date currentBillDate, int billDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentBillDate);
        //上个月
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, billDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取上一个账单截止时间(<该时间)
     * 
     * @param currentBillDate 当前账单日
     * @return
     */
    public static Date getPreBillEndDate(Date currentBillDate, int billDay) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(currentBillDate);
        cal.set(Calendar.DAY_OF_MONTH, billDay);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static void main(String[] args) {
        Date repayDate = getRepayDate(10, 15);
        String longFormat = "yyyyMMddHHmmssSSS";
        System.out.println(DateUtil.format(repayDate, longFormat));
        System.out.println(DateUtil.format(getPenaltyStartDate(repayDate, 3), longFormat));

        Date today = new Date();
        System.out.println(DateUtil.format(getPreBillStartDate(today, 1), longFormat));
        System.out.println(DateUtil.format(getPreBillEndDate(today, 1), longFormat));

    }
}
