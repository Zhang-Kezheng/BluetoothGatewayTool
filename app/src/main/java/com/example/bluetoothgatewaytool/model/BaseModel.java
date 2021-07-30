package com.example.bluetoothgatewaytool.model;

import org.litepal.crud.DataSupport;

/**
 * @author 章可政
 * @date 2021/7/21 1:34
 */
public class BaseModel extends DataSupport {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
