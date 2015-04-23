package com.dct.model;

/**
 * Created by stukolov_m on 23.04.2015.
 */
public class Setup {
    String shopIndex;
    String shopName;
    String shopDirector;
    String serverIP;

    public Setup(String shopIndex, String shopName, String shopDirector, String serverIP) {
        this.shopIndex = shopIndex;
        this.shopName = shopName;
        this.shopDirector = shopDirector;
        this.serverIP = serverIP;
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

    public String getShopDirector() {
        return shopDirector;
    }

    public void setShopDirector(String shopDirector) {
        this.shopDirector = shopDirector;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }
}
