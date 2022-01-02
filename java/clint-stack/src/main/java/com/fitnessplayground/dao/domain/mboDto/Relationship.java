package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Relationship {

    @JsonProperty("Id")
    private Long relationshipId;
    @JsonProperty("RelationshipName1")
    private String relationshipName1;
    @JsonProperty("RelationshipName2")
    private String relationshipName2;

    public Relationship() {
    }

    public Long getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(Long relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getRelationshipName1() {
        return relationshipName1;
    }

    public void setRelationshipName1(String relationshipName1) {
        this.relationshipName1 = relationshipName1;
    }

    public String getRelationshipName2() {
        return relationshipName2;
    }

    public void setRelationshipName2(String relationshipName2) {
        this.relationshipName2 = relationshipName2;
    }

    @Override
    public String toString() {
        return "Relationship{" +
                "relationshipId=" + relationshipId +
                ", relationshipName1='" + relationshipName1 + '\'' +
                ", relationshipName2='" + relationshipName2 + '\'' +
                '}';
    }
}
