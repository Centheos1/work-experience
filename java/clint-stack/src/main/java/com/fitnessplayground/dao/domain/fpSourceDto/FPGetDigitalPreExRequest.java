package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FPGetDigitalPreExRequest {

    @JsonProperty("EntityLookUp")
    private EntityLookUp entityLookUp;

    public FPGetDigitalPreExRequest() {
    }

    public FPGetDigitalPreExRequest(EntityLookUp entityLookUp) {
        this.entityLookUp = entityLookUp;
    }

    public EntityLookUp getEntityLookUp() {
        return entityLookUp;
    }

    public void setEntityLookUp(EntityLookUp entityLookUp) {
        this.entityLookUp = entityLookUp;
    }

    @Override
    public String toString() {
        return "FPGetDigitalPreExRequest{" +
                "entityLookUp=" + entityLookUp +
                '}';
    }
}
