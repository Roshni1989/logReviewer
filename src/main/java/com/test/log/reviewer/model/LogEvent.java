package com.test.log.reviewer.model;

import java.util.Objects;

public class LogEvent {

    private String id;

    private String state ;

    private long timestamp;

    private String hostname;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogEvent logEvent = (LogEvent) o;
        return id.equals(logEvent.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LogEvent{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", timestamp=" + timestamp +
                ", hostname='" + hostname + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
