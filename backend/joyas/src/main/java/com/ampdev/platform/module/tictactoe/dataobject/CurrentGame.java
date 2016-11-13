package com.ampdev.platform.module.tictactoe.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;
import com.ampdev.platform.module.tictactoe.constants.TicTacToeConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avi on 5/14/16.
 */

@Entity
@Table(name = "current_game")
public class CurrentGame extends PersistedDataObject implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "game_id")
    private String gameId;

    @Column(name = "game_play")
    private String gamePlay;

    @Column(name = "game_type")
    private int gameType;

    @Column(name = "game_state")
    private int gameState;

    @Column(name = "first_user")
    private String firstUser;

    @Column(name = "second_user")
    private String secondUser;

    @Column(name = "last_move_by")
    private String lastMoveBy;

    @Column(name = "won_by")
    private String wonBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "when_created")
    private Date whenCreated;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "when_modified")
    private Date whenModified;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGamePlay() {
        return gamePlay;
    }

    public void setGamePlay(String gamePlay) {
        this.gamePlay = gamePlay;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(String secondUser) {
        this.secondUser = secondUser;
    }

    public String getLastMoveBy() {
        return lastMoveBy;
    }

    public void setLastMoveBy(String lastMoveBy) {
        this.lastMoveBy = lastMoveBy;
    }

    public String getWonBy() {
        return wonBy;
    }

    public void setWonBy(String wonBy) {
        this.wonBy = wonBy;
    }

    public Date getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(Date whenCreated) {
        this.whenCreated = whenCreated;
    }

    public Date getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Date whenModified) {
        this.whenModified = whenModified;
    }

    @Override
    public long getDataId() {
        return id;
    }

    @JsonIgnore
    public void createGameId() {
        if (this.firstUser != null && this.secondUser != null) {
            if (this.firstUser.compareTo(this.secondUser) >= 0) {
                this.setGameId(String.format("%s%s", this.firstUser, this.secondUser));
            } else {
                this.setGameId(String.format("%s%s", this.firstUser, this.secondUser));
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentGame that = (CurrentGame) o;

        if (id != that.id) return false;
        if (gameType != that.gameType) return false;
        if (gameState != that.gameState) return false;
        if (gameId != null ? !gameId.equals(that.gameId) : that.gameId != null) return false;
        if (gamePlay != null ? !gamePlay.equals(that.gamePlay) : that.gamePlay != null) return false;
        if (firstUser != null ? !firstUser.equals(that.firstUser) : that.firstUser != null) return false;
        if (secondUser != null ? !secondUser.equals(that.secondUser) : that.secondUser != null) return false;
        if (lastMoveBy != null ? !lastMoveBy.equals(that.lastMoveBy) : that.lastMoveBy != null) return false;
        if (wonBy != null ? !wonBy.equals(that.wonBy) : that.wonBy != null) return false;
        if (whenCreated != null ? !whenCreated.equals(that.whenCreated) : that.whenCreated != null) return false;
        return whenModified != null ? whenModified.equals(that.whenModified) : that.whenModified == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (gameId != null ? gameId.hashCode() : 0);
        result = 31 * result + (gamePlay != null ? gamePlay.hashCode() : 0);
        result = 31 * result + gameType;
        result = 31 * result + gameState;
        result = 31 * result + (firstUser != null ? firstUser.hashCode() : 0);
        result = 31 * result + (secondUser != null ? secondUser.hashCode() : 0);
        result = 31 * result + (lastMoveBy != null ? lastMoveBy.hashCode() : 0);
        result = 31 * result + (wonBy != null ? wonBy.hashCode() : 0);
        result = 31 * result + (whenCreated != null ? whenCreated.hashCode() : 0);
        result = 31 * result + (whenModified != null ? whenModified.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BankStatus{" +
                "id=" + id +
                ", gameId='" + gameId + '\'' +
                ", gamePlay='" + gamePlay + '\'' +
                ", gameType=" + gameType +
                ", gameState=" + gameState +
                ", firstUser='" + firstUser + '\'' +
                ", secondUser='" + secondUser + '\'' +
                ", lastMoveBy='" + lastMoveBy + '\'' +
                ", wonBy='" + wonBy + '\'' +
                ", whenCreated=" + whenCreated +
                ", whenModified=" + whenModified +
                '}';
    }
}
