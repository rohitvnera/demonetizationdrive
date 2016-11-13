package com.ampdev.platform.module.common.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Avi on 5/22/16.
 */
@Entity
@Table(name = "uuid")
public class Uuid extends PersistedDataObject {
    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "guid")
    private String guid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Uuid uuid1 = (Uuid) o;

        if (uuid != null ? !uuid.equals(uuid1.uuid) : uuid1.uuid != null) return false;
        return guid != null ? guid.equals(uuid1.guid) : uuid1.guid == null;

    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (guid != null ? guid.hashCode() : 0);
        return result;
    }

    @JsonIgnore
    @Override
    public long getDataId() {
        return 0;
    }



    @Override
    public String toString() {
        return "Uuid{" +
                "uuid='" + uuid + '\'' +
                ", guid='" + guid + '\'' +
                '}';
    }
}
