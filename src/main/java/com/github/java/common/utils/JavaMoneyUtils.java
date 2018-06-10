package com.github.java.common.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import com.github.java.common.base.Money;

/**
 */
public class JavaMoneyUtils {

    /**
     * 0
     */
    public final static Money ZERO = Money.createInstance(0L);

    /**
     * 相加
     * 
     * @param src 必填
     * @param amt 必填
     * @return
     */
    public static Money add(Money src, Long amt) {
        src = nullToZero(src);
        Money amtMoney = nullToZero(amt);
        return add(src, amtMoney);
    }

    /**
     * 相加
     * 
     * @param src 必填
     * @param amt 必填
     * @return
     */
    public static Money add(Money[] amts) {
        if (null == amts || amts.length == 0) {
            return ZERO;
        }
        Money total = ZERO;
        for (Money amt : amts) {
            amt = nullToZero(amt);
            total = add(total, amt);
        }
        return total;
    }

    /**
     * 相加
     * 
     * @param src 必填
     * @param amt 必填
     * @return
     */
    public static Money add(Money src, Money amt) {
        src = nullToZero(src);
        amt = nullToZero(amt);
        return Money.createInstance(src.getAmount().longValue() + amt.getAmount().longValue());
    }

    /**
     * 相减
     * 
     * @param src 必填
     * @param amt 必填
     * @return
     */
    public static Money sub(Money src, Long amt) {
        src = nullToZero(src);
        Money amtMoney = nullToZero(amt);
        return sub(src, amtMoney);
    }

    /**
     * 相减
     * 
     * @param src 必填
     * @param amt 必填
     */
    public static Money sub(Money src, Money amt) {
        src = nullToZero(src);
        amt = nullToZero(amt);
        return Money.createInstance(src.getAmount().longValue() - amt.getAmount().longValue());
    }

    /**
     * 金额转化
     * 
     * @param amt
     * @return
     */
    public static Long moneyToLong(Money amt) {
        if (null == amt) {
            return null;
        }
        return amt.getAmount();
    }

    /**
     * 空转0
     * 
     * @param amt
     * @return
     */
    private static Money nullToZero(Money amt) {
        if (null == amt) {
            return Money.createInstance(0L);
        }
        return amt;
    }

    /**
     * 空转0
     * 
     * @param amt
     * @return
     */
    private static Money nullToZero(Long amt) {
        if (null == amt) {
            return Money.createInstance(0L);
        }
        return Money.createInstance(amt);
    }

    /**
     * 分转换为元.
     * 
     * @param fen 分
     * @return 元
     */
    public static String fromFenToYuan(String fen) {
        return new BigDecimal(fen).divide(new BigDecimal(100)).setScale(2).toString();
    }

    /**
     * 元转换为分.
     * 
     * @param yuan 元
     * @return 分
     */
    public static String fromYuanToFen(final String yuan) {
        NumberFormat format = NumberFormat.getInstance();
        Number number;
        try {
            number = format.parse(yuan);
        } catch (ParseException e) {
            return null;
        }
        double temp = number.doubleValue() * 100.0;
        // 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
        format.setGroupingUsed(false);
        // 设置返回数的小数部分所允许的最大位数
        format.setMaximumFractionDigits(0);
        return format.format(temp);
    }

    public static void main(String[] args) {
        System.out.println(fromFenToYuan("468590"));
        System.out.println(fromYuanToFen("468590.236"));
    }
}
