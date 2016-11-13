package com.ampdev.platform.module.tictactoe.pojo;

import com.ampdev.platform.module.tictactoe.constants.Source;

/**
 * Created by Avi on 5/22/16.
 */
public class UserData {

    private Source source;
    private String userId;
    private String userName;
    private String password;
    private String gcmRegId;
    private String deviceId;

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGcmRegId() {
        return gcmRegId;
    }

    public void setGcmRegId(String gcmRegId) {
        this.gcmRegId = gcmRegId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        if (source != userData.source) return false;
        if (userId != null ? !userId.equals(userData.userId) : userData.userId != null) return false;
        if (userName != null ? !userName.equals(userData.userName) : userData.userName != null) return false;
        if (password != null ? !password.equals(userData.password) : userData.password != null) return false;
        if (gcmRegId != null ? !gcmRegId.equals(userData.gcmRegId) : userData.gcmRegId != null) return false;
        return deviceId != null ? deviceId.equals(userData.deviceId) : userData.deviceId == null;

    }

    @Override
    public int hashCode() {
        int result = source != null ? source.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (gcmRegId != null ? gcmRegId.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserData{" +
                "source=" + source +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gcmRegId='" + gcmRegId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
