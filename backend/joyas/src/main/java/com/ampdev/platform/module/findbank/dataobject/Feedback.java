package com.ampdev.platform.module.findbank.dataobject;

import com.ampdev.platform.module.common.dataobject.PersistedDataObject;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Avi on 5/14/16.
 */

@Entity
@Table(name = "feedback")
public class Feedback extends PersistedDataObject implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private long id;

    @Column(name = "subject")
    private String subject;

    @Column(name = "message")
    private String message;

    @Override
    public long getDataId() {
        return id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if (id != feedback.id) return false;
        if (subject != null ? !subject.equals(feedback.subject) : feedback.subject != null) return false;
        return message != null ? message.equals(feedback.message) : feedback.message == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
