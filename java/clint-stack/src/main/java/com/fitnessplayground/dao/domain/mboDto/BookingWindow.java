package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingWindow {

    @JsonProperty("StartDateTime")
    private String startDateTime;// "2019-07-13T00:00:00",
    @JsonProperty("EndDateTime")
    private String endDateTime;// "2019-07-13T10:30:00",
    @JsonProperty("DailyStartTime")
    private String dailyStartTime;// null,
    @JsonProperty("DailyEndTime")
    private String dailyEndTime;// null
}
