package com.dfans.enums;

public enum  Merchant {
        /*US("US","USD","549440172770001","52491302b30c63647088b5f624c37247","21237990"),
        EU("EU","EUR","549440172770002","1db0a170d9693c13ad4d13794b80cbfa","21237991"),
        UK("UK","GBP","549440172770003","397073e83b87d00d60e05028c44f220d","21238000"),
        HK("HK","HKD","549440172770004","d55b295f0b8dc2e596cb686a7389ba27","21238001"),
        JP("JP","JPY","549440172770005","574eeba022365eea4c2c66fc70734465","21238012"),
        CA("CA","CAD","549440172770006","554ad0f58427b6d7f76925a828e69923","21238013"),
        AU("AU","AUD","549440172770007","a19492581dadf591f1eeb4e47c5628c5","21238014"),
        SF("SF","CHF","549440172770008","c31ccb6bdb116ae88ab32f923bad5dee","21238018");*/

        T0("CN","CNY","549440172770013","18a4dfab0bf8cde61db099248084e283","21242289"),
        T1("CN","CNY","549440172770014","3ba4fa9a2ee4948c049b9fe4fe470a01","21242307");

        private String country;
        private String name;
        private String merchant;
        private String key;
        private String terminal;

    Merchant(String country, String name, String merchant,String key, String terminal) {
        this.country = country;
        this.name = name;
        this.merchant = merchant;
        this.key = key;
        this.terminal = terminal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public static Merchant getMerchantByCountry(String country){
            for(Merchant merchant:Merchant.values()){
                if(merchant.getCountry().equals(country)){
                    return merchant;
                }
            }
            return null;
        }


}
