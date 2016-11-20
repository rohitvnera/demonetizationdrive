package com.ampdev.platform.module.common.util;

import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.framework.uuid.Guid;
import com.ampdev.platform.module.common.constants.ApplicationConstants;
import com.ampdev.platform.module.common.dao.UuidGenerator;
import com.ampdev.platform.module.common.dataobject.Uuid;
import com.ampdev.platform.module.tictactoe.constants.Source;
import com.ampdev.platform.module.tictactoe.dao.UserFriendDao;
import com.ampdev.platform.module.tictactoe.dataobject.User;
import com.ampdev.platform.module.tictactoe.dataobject.UserFriend;
import facebook4j.*;
import facebook4j.auth.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("facebookUtil")
public class FacebookUtil {

    @Autowired
    private IDataAccess dataAccess;

    @Autowired
    private UuidGenerator uuidGenerator;

    @Autowired
    private UserFriendDao userFriendDao;

    public boolean isUserOuthToken(String userId, String accessToken) {
        boolean isUserOuthToken = false;

        if (Util.isEmpty(userId)) {
            return false;
        }
        String expectedUserId = getUserId(accessToken);

        if (userId.equals(expectedUserId)) {
            isUserOuthToken = true;
        }

        return isUserOuthToken;
    }

    public String getUserId(String accessToken) {
        String userId = null;
        Facebook facebook = getFacebookHandle(accessToken);
        try {
            userId = facebook.getId();
        } catch (IllegalStateException | FacebookException e) {
            e.printStackTrace();
        }
        return userId;
    }

    public Facebook getFacebookHandle(String accessToken) {
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(ApplicationConstants.APP_ID, ApplicationConstants.APP_SECRET_TOKEN);
        facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
        return facebook;
    }

    public Collection<String> getFriendsUserName(User fbUser) {
        String accessToken = fbUser.getPassword();
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(ApplicationConstants.APP_ID, ApplicationConstants.APP_SECRET_TOKEN);
        facebook.setOAuthAccessToken(new AccessToken(accessToken, null));
        Set<String> friends = new HashSet<>();

        try {
            ResponseList<Friend> fbFriends = facebook.getFriends();
            for (Friend friend : fbFriends) {
                friends.add(friend.getId());
            }

        } catch (FacebookException e) {
            e.printStackTrace();
        }

        return friends;

    }

    public String getFullName(User userData) {
        if (userData == null) {
            return null;
        }

        String fullName = null;
        Facebook facebook = getFacebookHandle(userData.getPassword());
        try {
            fullName = facebook.getName();
        } catch (IllegalStateException | FacebookException e) {
            e.printStackTrace();
        }
        return fullName;
    }

    public void saveFBFriends(@Nonnull final String fbUserId, @Nonnull final String oAuth, String uuid) {
        final Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(ApplicationConstants.APP_ID, ApplicationConstants.APP_SECRET_TOKEN);
        facebook.setOAuthAccessToken(new AccessToken(oAuth, null));

        try {
            final ResponseList<Friend> fbFriends = facebook.getFriends();
            for (final Friend friend : fbFriends) {
                try {
                    final Uuid friendUUID =
                            uuidGenerator.getOrCreateUUID(new Guid(Source.FB.toString(), friend.getId()));
                        final List<UserFriend> userFriends =
                            userFriendDao.findByUserIdAndFriendId(fbUserId, friendUUID.getUuid());
                    if (Util.isEmpty(userFriends)) {
                        final UserFriend userFriend = new UserFriend();
                        userFriend.setUserId(uuid);
                        userFriend.setFriendId(friendUUID.getUuid());
                        dataAccess.create(userFriend);
                    } else {

                    }
                } catch (DataAccessException e) {
                    e.printStackTrace();
                }
            }
        } catch (FacebookException e) {
            e.printStackTrace();
        }
    }

}
