package com.ampdev.platform.module.tictactoe.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.tictactoe.pojo.UserData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by Avi on 5/22/16.
 */
@Entity
@Table(name = "user")
public class User extends PersistedDataObject {
    @JsonIgnore
    @Override
    public long getDataId() {
        return 0;
    }

    @Id
    @Column(name = "user_id")
    private String uuid;

    @Column(name = "fb_user_id")
    private String fbUserId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "gcm_reg_id")
    private String gcmRegId;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGcmRegId() {
        return gcmRegId;
    }

    public void setGcmRegId(String gcmRegId) {
        this.gcmRegId = gcmRegId;
    }

    @JsonIgnore
    public void populateUser(UserData userData){
        this.setFbUserId(userData.getUserId());
        this.setPassword(userData.getPassword());
        this.setUserName(userData.getUserName());
        this.setDeviceId(userData.getDeviceId());
        this.setGcmRegId(userData.getGcmRegId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (uuid != null ? !uuid.equals(user.uuid) : user.uuid != null) return false;
        if (fbUserId != null ? !fbUserId.equals(user.fbUserId) : user.fbUserId != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (deviceId != null ? !deviceId.equals(user.deviceId) : user.deviceId != null) return false;
        return gcmRegId != null ? gcmRegId.equals(user.gcmRegId) : user.gcmRegId == null;

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fbUserId != null ? fbUserId.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (deviceId != null ? deviceId.hashCode() : 0);
        result = 31 * result + (gcmRegId != null ? gcmRegId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid='" + uuid + '\'' +
                ", fbUserId='" + fbUserId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", gcmRegId='" + gcmRegId + '\'' +
                '}';
    }
}
