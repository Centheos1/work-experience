package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientRelationship {

    @JsonProperty("RelatedClientId")
    private String relatedClientId;
    @JsonProperty("Relationship")
    private Relationship relationship;
    @JsonProperty("RelationshipName")
    private String relationshipName;

    public ClientRelationship() {
    }

    public String getRelatedClientId() {
        return relatedClientId;
    }

    public void setRelatedClientId(String relatedClientId) {
        this.relatedClientId = relatedClientId;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    @Override
    public String toString() {
        return "ClientRelationship{" +
                "relatedClientId='" + relatedClientId + '\'' +
                ", relationship=" + relationship +
                ", relationshipName='" + relationshipName + '\'' +
                '}';
    }
}
