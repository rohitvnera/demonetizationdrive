package com.ampdev.platform.module.common.dataobject;

/**
 * Created by Avi on 11/13/16.
 */
public class ID {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ID id1 = (ID) o;

        return id != null ? id.equals(id1.id) : id1.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ID{" +
                "id='" + id + '\'' +
                '}';
    }
}
