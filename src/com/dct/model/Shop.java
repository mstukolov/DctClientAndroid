package com.dct.model;

/**
 * Created by stukolov_m on 23.04.2015.
 */
public class Shop {
    public String shopindex;
    public String shopname;

    public Shop(String shopindex, String shopname) {
        this.shopindex = shopindex;
        this.shopname = shopname;
    }

    public Shop() {
    }

    public String getShopindex() {
        return shopindex;
    }

    public void setShopindex(String shopindex) {
        this.shopindex = shopindex;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shop shop = (Shop) o;

        if (shopindex != null ? !shopindex.equals(shop.shopindex) : shop.shopindex != null) return false;
        return !(shopname != null ? !shopname.equals(shop.shopname) : shop.shopname != null);

    }

    @Override
    public int hashCode() {
        int result = shopindex != null ? shopindex.hashCode() : 0;
        result = 31 * result + (shopname != null ? shopname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopindex='" + shopindex + '\'' +
                ", shopname='" + shopname + '\'' +
                '}';
    }
}
