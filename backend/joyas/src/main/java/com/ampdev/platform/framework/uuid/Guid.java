package com.ampdev.platform.framework.uuid;

/**
 * Created by Avi on 5/22/16.
 */
public class Guid {

    private final String manager;
    private final String id;


    public Guid(final String manager, final String id) {
        this.manager = manager;
        this.id = id;
    }

    public final String getGuid(){
        return String.format("[%s]-[%s]", manager, id);
    }

    @Override
    public String toString() {
        return getGuid();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guid guid = (Guid) o;

        if (manager != null ? !manager.equals(guid.manager) : guid.manager != null) return false;
        return id != null ? id.equals(guid.id) : guid.id == null;

    }

    @Override
    public int hashCode() {
        int result = manager != null ? manager.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
