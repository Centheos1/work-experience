package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MboPurchaseContractResponse {

    @JsonProperty("ClientId")
    private String clientId;
    @JsonProperty("LocationId")
    private Integer locationId;
    @JsonProperty("ContractId")
    private Integer contractId;
    @JsonProperty("ClientContractId")
    private Integer clientContractId;

    public MboPurchaseContractResponse() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getClientContractId() {
        return clientContractId;
    }

    public void setClientContractId(Integer clientContractId) {
        this.clientContractId = clientContractId;
    }

    @Override
    public String toString() {
        return "MboPurchaseContractResponse{" +
                "clientId='" + clientId + '\'' +
                ", locationId=" + locationId +
                ", contractId=" + contractId +
                ", clientContractId=" + clientContractId +
                '}';
    }
}
