package com.example.bluetoothgatewaytool.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 章可政
 * @date 2021/7/21 2:37
 */
public class ApplicationData {
    private List<Scene> scenes=new ArrayList<>();

    private User user;

    public List<Scene> getScenes() {
        return scenes;
    }

    public void setScenes(List<Scene> scenes) {
        this.scenes = scenes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
