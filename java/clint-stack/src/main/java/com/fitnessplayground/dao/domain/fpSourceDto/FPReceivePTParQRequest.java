package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FPReceivePTParQRequest {

    @JsonProperty("handshakeKey")
    private String handshakeKey;
    @JsonProperty("ptParQData")
    private FPPTParQData data;

    public FPReceivePTParQRequest() {
    }

    public String getHandshakeKey() {
        return handshakeKey;
    }

    public void setHandshakeKey(String handshakeKey) {
        this.handshakeKey = handshakeKey;
    }

    public FPPTParQData getData() {
        return data;
    }

    public void setData(FPPTParQData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FPReceivePTParQRequest{" +
                "handshakeKey='" + handshakeKey + '\'' +
                ", data=" + data +
                '}';
    }
}
