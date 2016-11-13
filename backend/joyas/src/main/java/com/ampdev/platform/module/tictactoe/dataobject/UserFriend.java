package com.ampdev.platform.module.tictactoe.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Avi on 5/23/16.
 */
@Entity
@Table(name = "user_friend")
public class UserFriend extends PersistedDataObject implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "user_id")
    private String userId;

    @Column(name = "friend_id")
    private String friendId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    @Override
    public long getDataId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserFriend that = (UserFriend) o;

        if (id != that.id) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return friendId != null ? friendId.equals(that.friendId) : that.friendId == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (friendId != null ? friendId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserFriend{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", friendId=" + friendId +
                '}';
    }
}
