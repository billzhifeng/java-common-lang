package com.github.java.common.base;

import java.math.BigDecimal;
import java.util.Currency;

import org.apache.commons.lang3.StringUtils;

/**
 * money对象
 */
public final class Money implements java.io.Serializable {

    private static final long serialVersionUID = 6257367531532365891L;

    /**
     * 币种
     */
    private Currency          currency         = Currency.getInstance("CNY");

    /**
     * 以分为单位的金额
     */
    private long              amount           = 0;

    public Money() {
    }

    public Money(Long cent, Currency currency) {
        this.amount = cent != null ? cent : 0;
        if (currency != null) {
            this.currency = currency;
        }
    }

    public Money(Long cent, String currency) {
        this(cent, Currency.getInstance(currency));
    }

    public Money(Long cent) {
        this.amount = cent != null ? cent : 0;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getAmount() {
        return amount;
    }

    public String toYuan() {
        BigDecimal d = new BigDecimal(getAmount());
        BigDecimal d100 = new BigDecimal(100);
        return d.divide(d100).toString();
    }

    public static Money createInstance(String yuan) {
        if (StringUtils.isBlank(yuan)) {
            return null;
        }

        BigDecimal d = new BigDecimal(yuan);
        BigDecimal d100 = new BigDecimal(100);
        d.multiply(d100);
        return new Money((d.multiply(d100)).longValue());
    }

    public static Money createInstance(Long cent) {
        if (cent == null) {
            return null;
        } else {
            return new Money(cent);
        }
    }

    /**
     * 货币比较
     * 
     * @param src
     * @param dest
     * @return
     */
    public static int compare(Money src, Money dest) {
        if (!src.currency.equals(dest.getCurrency())) {
            throw new IllegalArgumentException(
                    "不同币种不能比较srcCurrency:[" + src.currency + "],destCurrency[" + dest.getCurrency() + "]");
        }

        long result = src.amount - dest.amount;
        if (result > 0L) {
            return 1;
        } else if (result < 0L) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 判断是否大于0
     * 
     * @return
     */
    public boolean gtZero() {
        return compare(this, new Money(0L)) > 0;
    }

    /**
     * 判断是否小于0
     * 
     * @return
     */
    public boolean ltZero() {
        return compare(this, new Money(0L)) < 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + new Long(amount).hashCode();
        result = prime * result + ((currency == null) ? 0 : currency.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        Money other = (Money) obj;
        if (currency == null) {
            if (other.currency != null)
                return false;
        } else if (!currency.equals(other.currency))
            return false;

        return amount == other.amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Money{");
        sb.append("currency=").append(currency);
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }

    public static Long getCentOfMoney(Money money) {
        if (money == null) {
            return null;
        }
        return money.getAmount();
    }

    public static boolean isGtZero(Money money) {
        if (money == null) {
            return false;
        }
        return money.gtZero();
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Money clone() {
        Money money = new Money(0L);
        money.setAmount(this.amount);
        if (this.currency != null) {
            money.setCurrency(Currency.getInstance(this.currency.toString()));
        }
        return money;
    }

}
