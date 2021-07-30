package com.example.bluetoothgatewaytool.model;

/**
 * @author 章可政
 * @date 2021/7/20 16:24
 * 场景
 */
public class Scene extends BaseModel{

    /**
     * 主键
     */
    private  Integer id;
    /**
     * 楼层编号
     */
    private String floorNumber;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域编号
     */
    private String areaNumber;

    private float x;

    private float y;

    private float z;

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaNumber() {
        return areaNumber;
    }

    public void setAreaNumber(String areaNumber) {
        this.areaNumber = areaNumber;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
