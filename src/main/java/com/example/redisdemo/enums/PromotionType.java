package com.example.redisdemo.enums;

/**
 * 现有促销类型
 * @author lau
 */
public enum PromotionType {

    /**
     *
     */
    COUPON(-1,"优惠券"),
    SEC_KILL(0,"秒杀"),
    COLLAGE(1,"拼团"),
    PRE_SALE(2,"预售"),
    FIRST_ORDER(3,"首单立减"),
    GIFT_ORDER(4,"买赠/满赠"),
    FUL_CUT(5,"满减"),
    ZERO_BUY(6,"0元购"),
    NGOODS(7,"n件促销"),
    WALLET_REBATE(8,"电子钱包"),
    LIMIT_PRODUCT(9,"商品限购"),
    SNATCH_TREASURE(10, "一元夺宝"),
    OFFLINE(11, "线下通道"),
    SHARE_GIFT(12, "分享有礼"),
    ;

    private Integer value;

    private String description;

    PromotionType(Integer value, String description) {
        this.value = value;
        this.description = description;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
