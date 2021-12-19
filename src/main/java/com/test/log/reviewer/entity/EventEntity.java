package com.test.log.reviewer.entity;

import javax.persistence.*;

@Entity
@Table(name = "events")
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long duration ;

    private String type ;

    private String host;

    private String alert;

    public EventEntity(long duration, String type, String host, String alert) {
        this.duration = duration;
        this.type = type;
        this.host = host;
        this.alert = alert;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }


}
