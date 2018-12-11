package com.iwhalecloud.retail.oms.VO;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import com.ztesoft.net.mall.core.model.Cart;
import com.ztesoft.net.mall.core.model.Coupons;
import com.ztesoft.net.mall.core.model.support.CartItem;

import java.util.List;
/**
 * @author pjq
 * @date 2018/10/8
 */
public class CartVO implements java.io.Serializable{
    @ZteSoftCommentAnnotationParam(
            name = "优惠券信息",
            type = "String",
            isNecessary = "N",
            desc = "优惠券信息"
    )
    private Coupons coupon;
    @ZteSoftCommentAnnotationParam(
            name = "商品列表",
            type = "List",
            isNecessary = "N",
            desc = "商品列表"
    )
    private List<CartItem> goodsItemList;
    @ZteSoftCommentAnnotationParam(
            name = "赠送信息列表",
            type = "String",
            isNecessary = "N",
            desc = "赠送信息列表"
    )
    private List<CartItem> giftItemList;
    @ZteSoftCommentAnnotationParam(
            name = "捆绑商品列表",
            type = "String",
            isNecessary = "N",
            desc = "捆绑商品列表"
    )
    private List<CartItem> pgkItemList;
    @ZteSoftCommentAnnotationParam(
            name = "团购商品列表",
            type = "String",
            isNecessary = "N",
            desc = "团购商品列表"
    )
    private List<CartItem> groupBuyList;
    @ZteSoftCommentAnnotationParam(
            name = "秒杀商品列表",
            type = "String",
            isNecessary = "N",
            desc = "秒杀商品列表"
    )
    private List<CartItem> limitBuyList;

    public CartVO() {
    }

    public Coupons getCoupon() {
        return this.coupon;
    }

    public void setCoupon(Coupons coupon) {
        this.coupon = coupon;
    }

    public List<CartItem> getLimitBuyList() {
        return this.limitBuyList;
    }

    public void setLimitBuyList(List<CartItem> limitBuyList) {
        this.limitBuyList = limitBuyList;
    }

    public List<CartItem> getGoodsItemList() {
        return this.goodsItemList;
    }

    public void setGoodsItemList(List<CartItem> goodsItemList) {
        this.goodsItemList = goodsItemList;
    }

    public List<CartItem> getGiftItemList() {
        return this.giftItemList;
    }

    public void setGiftItemList(List<CartItem> giftItemList) {
        this.giftItemList = giftItemList;
    }

    public List<CartItem> getPgkItemList() {
        return this.pgkItemList;
    }

    public void setPgkItemList(List<CartItem> pgkItemList) {
        this.pgkItemList = pgkItemList;
    }

    public List<CartItem> getGroupBuyList() {
        return this.groupBuyList;
    }

    public void setGroupBuyList(List<CartItem> groupBuyList) {
        this.groupBuyList = groupBuyList;
    }

    @Override
    public String toString() {
        return "CartVO{" +
                "coupon=" + coupon +
                ", goodsItemList=" + goodsItemList +
                ", giftItemList=" + giftItemList +
                ", pgkItemList=" + pgkItemList +
                ", groupBuyList=" + groupBuyList +
                ", limitBuyList=" + limitBuyList +
                '}';
    }
}
