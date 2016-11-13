package com.ampdev.platform.module.tictactoe.resource;

import com.ampdev.platform.framework.dataaccess.IDataAccess;
import com.ampdev.platform.framework.dataaccess.exception.DataAccessException;
import com.ampdev.platform.framework.rest.RestBaseResource;
import com.ampdev.platform.framework.rest.security.token.TokenAuthenticationFilter;
import com.ampdev.platform.framework.rest.security.token.TokenInfo;
import com.ampdev.platform.framework.rest.security.token.TokenManagerImpl;
import com.ampdev.platform.framework.uuid.Guid;
import com.ampdev.platform.module.common.constants.URIConstants;
import com.ampdev.platform.module.common.dao.UuidGenerator;
import com.ampdev.platform.module.common.dataobject.Uuid;
import com.ampdev.platform.module.common.util.FacebookUtil;
import com.ampdev.platform.module.common.util.Util;
import com.ampdev.platform.module.tictactoe.dao.CurrentGameDao;
import com.ampdev.platform.module.tictactoe.dao.UserDao;
import com.ampdev.platform.module.tictactoe.dao.UserFriendDao;
import com.ampdev.platform.module.tictactoe.dataobject.CurrentGame;
import com.ampdev.platform.module.tictactoe.dataobject.User;
import com.ampdev.platform.module.tictactoe.dataobject.UserFriend;
import com.ampdev.platform.module.tictactoe.pojo.UserData;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping(value = "/ws/tictactoe")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TicTacToeResource extends RestBaseResource {

    private static final String HANDSHAKE_HEADER = "APPUS_HANDSHAKE";
    private static final String HANDSHAKE_HEADER_VAL = "APPUS_CONNECTED";

    @Autowired
    private CurrentGameDao currentGameDao;

    @Autowired
    private UserDao userCrudDao;

    @Autowired
    private IDataAccess dataAccess;

    @Autowired
    private UuidGenerator uuidGenerator;

    @Autowired
    private FacebookUtil facebookUtil;

    @Autowired
    private UserFriendDao userFriendDao;


    /************************************************************************************************************
     * Current related APIS
     ************************************************************************************************************/

    @RequestMapping(value = "/game" + URIConstants.GET_ALL, method = RequestMethod.GET)
    public ResponseEntity<?> getAllGames(@RequestParam(value = URIConstants.PAGE_NUM, required = true) final Integer pageNum) {
        final PageRequest pageRequest = new PageRequest(pageNum,
                URIConstants.NUMBER_OF_ITEMS,
                new Sort(Sort.Direction.DESC, "whenModified"));

        final Page<CurrentGame> page = currentGameDao.findAll(pageRequest);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @RequestMapping(value = "/game" + URIConstants.GET_IDS, method = RequestMethod.GET)
    public ResponseEntity<?> getGameByIds(@PathVariable(value = "ids") String countryIds) {
        final Set<Long> ids = Util.getLongList(countryIds, ",");
        final List<CurrentGame> currentGames = Lists.newArrayList();
        for (Long id : ids) {
            final CurrentGame currentGame = currentGameDao.findOne(id);
            if (currentGame != null) {
                currentGames.add(currentGame);
            }
        }
        return new ResponseEntity<>(currentGames, HttpStatus.OK);
    }

    @RequestMapping(value = "/game" + URIConstants.ADD, method = RequestMethod.POST)
    public ResponseEntity<?> addGame(RequestEntity<CurrentGame> requestEntity) {
        CurrentGame currentGame = requestEntity.getBody();
        try {
            currentGame.createGameId();
            dataAccess.create(currentGame);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(currentGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/game" + URIConstants.UPDATE, method = RequestMethod.PUT)
    public ResponseEntity<?> updateGame(RequestEntity<CurrentGame> requestEntity) {
        final CurrentGame currentGame = requestEntity.getBody();
        try {
            final CurrentGame currentGameDB = currentGameDao.findOne(currentGame.getId());
            if (currentGameDB != null) {
                currentGame.setWhenCreated(currentGameDB.getWhenCreated());
                currentGame.createGameId();
                dataAccess.update(currentGame);
            } else {
                return new ResponseEntity<>(String.format("Entity with id %d doesn't exist", currentGame.getId()),
                        HttpStatus.BAD_REQUEST);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(currentGame, HttpStatus.OK);
    }

    @RequestMapping(value = "/game" + URIConstants.DELETE_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteGame(@PathVariable(value = "id") String countryId) {
        currentGameDao.delete(Long.valueOf(countryId));
        return new ResponseEntity<>(countryId, HttpStatus.OK);
    }

    @RequestMapping(value = "/game/users/{userid1}/{userid2}", method = RequestMethod.GET)
    public ResponseEntity<?> getGameBetweenUsers(@PathVariable(value = "userid1") final String userId1,
                                                 @PathVariable(value = "userid2") final String userId2,
                                                 @RequestParam(value = URIConstants.PAGE_NUM, required = true) final Integer pageNum) {
        final PageRequest pageRequest = new PageRequest(pageNum,
                URIConstants.NUMBER_OF_ITEMS, new Sort(Sort.Direction.DESC, "whenModified"));
        Page<CurrentGame> currentGames = currentGameDao.findCurrentGamesBetweenUsers(userId1, userId2, pageRequest);
        return new ResponseEntity<>(currentGames, HttpStatus.OK);
    }

    @RequestMapping(value = "/game/stats/{userid}", method = RequestMethod.GET)
    public ResponseEntity<?> getStatsForUser(@PathVariable(value = "userid") final String userId,
                                             @RequestParam(value = URIConstants.PAGE_NUM, required = true) final Integer pageNum) {
        final PageRequest pageRequest = new PageRequest(pageNum,
                URIConstants.NUMBER_OF_ITEMS, new Sort(Sort.Direction.DESC, "whenModified"));
        Page<CurrentGame> currentGames = currentGameDao.findStatsForUser(userId, pageRequest);
        return new ResponseEntity<>(currentGames, HttpStatus.OK);
    }

    @RequestMapping(value = "/game/stats/{userid1}/{userid2}", method = RequestMethod.GET)
    public ResponseEntity<?> getStatsBetweenUsers(@PathVariable(value = "userid1") final String userId1,
                                                  @PathVariable(value = "userid2") final String userId2,
                                                  @RequestParam(value = URIConstants.PAGE_NUM, required = true) final Integer pageNum) {
        final PageRequest pageRequest = new PageRequest(pageNum,
                URIConstants.NUMBER_OF_ITEMS, new Sort(Sort.Direction.DESC, "whenModified"));
        Page<CurrentGame> currentGames = currentGameDao.getStatsBetweenUsers(userId1, userId2, pageRequest);
        return new ResponseEntity<>(currentGames, HttpStatus.OK);
    }

    /************************************************************************************************************
     * User and login related APIS
     ************************************************************************************************************/

    @RequestMapping(value = "/handshake", method = RequestMethod.GET)
    public ResponseEntity<?> handShake() {
        HttpHeaders responseHeaders = new HttpHeaders();

        responseHeaders.set(HANDSHAKE_HEADER, HANDSHAKE_HEADER_VAL);
        ResponseEntity<String> responseEntity = new ResponseEntity<>(
                responseHeaders, HttpStatus.CREATED);
        return responseEntity;
    }

    @RequestMapping(value = "/user" + URIConstants.GET_ALL, method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        Iterable<User> allUsers = userCrudDao.findAll();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @RequestMapping(value = "/user" + URIConstants.GET_IDS, method = RequestMethod.GET)
    public ResponseEntity<?> getUserByIds(@PathVariable(value = "ids") String countryIds) {
        final Set<String> ids = Util.getStringList(countryIds, ",");
        final List<User> currentGames = Lists.newArrayList();
        for (String id : ids) {
            final User currentGame = userCrudDao.findOne(id);
            if (currentGame != null) {
                currentGames.add(currentGame);
            }
        }
        return new ResponseEntity<>(currentGames, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(RequestEntity<UserData> requestEntity) {
        try {
            final UserData userData = requestEntity.getBody();
            final Guid guid = new Guid(userData.getSource().toString(), userData.getUserId());
            final Uuid uuid = uuidGenerator.getOrCreateUUID(guid);
            User user = userCrudDao.findOne(uuid.getUuid());
            if (user != null) {
                user.populateUser(userData);
                dataAccess.update(user);
            } else {
                user = new User();
                user.setUuid(uuid.getUuid());
                user.populateUser(userData);
                dataAccess.create(user);
                final String userId = user.getFbUserId();
                final String password = user.getPassword();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        facebookUtil.saveFBFriends(userId, password, uuid.getUuid());
                    }
                }).start();
            }
            final TokenInfo tokenInfo = TokenManagerImpl.getTokenManager().createNewToken(user.getFbUserId(),
                    userData.getPassword());
            final HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set(TokenAuthenticationFilter.HEADER_TOKEN, tokenInfo.getToken());
            TokenManagerImpl.getTokenManager().putUserName(user.getFbUserId(), user);
            return new ResponseEntity<>(user, responseHeaders, HttpStatus.OK);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        //Save uui
        return null;
    }

    @RequestMapping(value = "/user" + URIConstants.DELETE_ID, method = RequestMethod.DELETE)
    public ResponseEntity<?> deteletUser(@PathVariable(value = "id") String countryId) {
        userCrudDao.delete(countryId);
        return new ResponseEntity<>(countryId, HttpStatus.OK);
    }

    /*************************************************************************************
     * User friends API
     ************************************************************************************/
    @RequestMapping(value = "/friend/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserFriends(@PathVariable(value = "userId") String userId,
                                            @RequestParam(value = URIConstants.PAGE_NUM, required = true) final Integer pageNum) {
        final PageRequest pageRequest = new PageRequest(pageNum, URIConstants.NUMBER_OF_ITEMS);
        Page<UserFriend> page = userFriendDao.findByUserId(userId, pageRequest);
        //TODO When we have date for friends in user table then fetch min data for user and set in response
        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
