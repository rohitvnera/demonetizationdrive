package com.ampdev.platform.module.findbank.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Avi on 5/14/16.
 */

@Entity
@Table(name = "bank_status")
public class BankStatus extends PersistedDataObject implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "latX")
    private String latX;

    @Column(name = "latY")
    private String latY;

    @Column(name = "type")
    private String type;

    @Column(name = "name")
    private String name;


    @Column(name = "map_id")
    private String mapId;

    @Column(name = "map_reference")
    private String mapReference;

    @Column(name = "address")
    private String address;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "bank_open_status")
    private int bankOpenStatus;

    @Column(name = "cash_available")
    private int cashAvailable;

    @Column(name = "avg_wait_time")
    private long avgWaitTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "next_availabilty")
    private Date nextAvailabilty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "when_modified")
    private Date whenModified;

    @Transient
    private boolean isAdded;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getDataId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public int getBankOpenStatus() {
        return bankOpenStatus;
    }

    public void setBankOpenStatus(int bankOpenStatus) {
        this.bankOpenStatus = bankOpenStatus;
    }

    public int getCashAvailable() {
        return cashAvailable;
    }

    public void setCashAvailable(int cashAvailable) {
        this.cashAvailable = cashAvailable;
    }

    public long getAvgWaitTime() {
        return avgWaitTime;
    }

    public void setAvgWaitTime(long avgWaitTime) {
        this.avgWaitTime = avgWaitTime;
    }

    public Date getNextAvailabilty() {
        return nextAvailabilty;
    }

    public void setNextAvailabilty(Date nextAvailabilty) {
        this.nextAvailabilty = nextAvailabilty;
    }

    public Date getWhenModified() {
        return whenModified;
    }

    public void setWhenModified(Date whenModified) {
        this.whenModified = whenModified;
    }

    public String getLatX() {
        return latX;
    }

    public void setLatX(String latX) {
        this.latX = latX;
    }

    public String getLatY() {
        return latY;
    }

    public void setLatY(String latY) {
        this.latY = latY;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getMapId() {
        return mapId;
    }

    public void setMapId(String mapId) {
        this.mapId = mapId;
    }

    public String getMapReference() {
        return mapReference;
    }

    public void setMapReference(String mapReference) {
        this.mapReference = mapReference;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BankStatus that = (BankStatus) o;

        if (id != that.id) return false;
        if (bankOpenStatus != that.bankOpenStatus) return false;
        if (cashAvailable != that.cashAvailable) return false;
        if (avgWaitTime != that.avgWaitTime) return false;
        if (isAdded != that.isAdded) return false;
        if (latX != null ? !latX.equals(that.latX) : that.latX != null) return false;
        if (latY != null ? !latY.equals(that.latY) : that.latY != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (mapId != null ? !mapId.equals(that.mapId) : that.mapId != null) return false;
        if (mapReference != null ? !mapReference.equals(that.mapReference) : that.mapReference != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (contactNumber != null ? !contactNumber.equals(that.contactNumber) : that.contactNumber != null)
            return false;
        if (nextAvailabilty != null ? !nextAvailabilty.equals(that.nextAvailabilty) : that.nextAvailabilty != null)
            return false;
        return whenModified != null ? whenModified.equals(that.whenModified) : that.whenModified == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (latX != null ? latX.hashCode() : 0);
        result = 31 * result + (latY != null ? latY.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (mapId != null ? mapId.hashCode() : 0);
        result = 31 * result + (mapReference != null ? mapReference.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (contactNumber != null ? contactNumber.hashCode() : 0);
        result = 31 * result + bankOpenStatus;
        result = 31 * result + cashAvailable;
        result = 31 * result + (int) (avgWaitTime ^ (avgWaitTime >>> 32));
        result = 31 * result + (nextAvailabilty != null ? nextAvailabilty.hashCode() : 0);
        result = 31 * result + (whenModified != null ? whenModified.hashCode() : 0);
        result = 31 * result + (isAdded ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BankStatus{" +
                "id=" + id +
                ", latX='" + latX + '\'' +
                ", latY='" + latY + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", mapId='" + mapId + '\'' +
                ", mapReference='" + mapReference + '\'' +
                ", address='" + address + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", bankOpenStatus=" + bankOpenStatus +
                ", cashAvailable=" + cashAvailable +
                ", avgWaitTime=" + avgWaitTime +
                ", nextAvailabilty=" + nextAvailabilty +
                ", whenModified=" + whenModified +
                ", isAdded=" + isAdded +
                '}';
    }
}
