package com.dct.model;

/**
 * Created by stukolov_m on 23.04.2015.
 */
public class Shop {
    public String shopIndex;
    public String shopName;

    public Shop(String shopIndex, String shopName) {
        this.shopIndex = shopIndex;
        this.shopName = shopName;
    }

    public String getShopIndex() {
        return shopIndex;
    }

    public void setShopIndex(String shopIndex) {
        this.shopIndex = shopIndex;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
