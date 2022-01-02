package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties
public class EpochTimeStamp {

    @JsonProperty("_seconds")
    private String _seconds;
    @JsonProperty("_nanoseconds")
    private String _nanoseconds;

    public EpochTimeStamp() {
    }

    public String get_seconds() {
        return _seconds;
    }

    public void set_seconds(String _seconds) {
        this._seconds = _seconds;
    }

    public String get_nanoseconds() {
        return _nanoseconds;
    }

    public void set_nanoseconds(String _nanoseconds) {
        this._nanoseconds = _nanoseconds;
    }

    @Override
    public String toString() {
        return "EpochTimeStamp{" +
                "_seconds='" + _seconds + '\'' +
                ", _nanoseconds='" + _nanoseconds + '\'' +
                '}';
    }
}
