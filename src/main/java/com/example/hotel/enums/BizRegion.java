package com.example.hotel.enums;

public enum BizRegion {
    西单("西单"),
    新街口("新街口"),
    仙林中心("仙林中心"),
    东方之门("东方之门"),
    浦东机场("浦东机场");

    private String value;

    BizRegion(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
