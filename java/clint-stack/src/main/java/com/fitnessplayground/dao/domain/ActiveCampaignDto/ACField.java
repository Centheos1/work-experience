package com.fitnessplayground.dao.domain.ActiveCampaignDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ACField {

    @JsonProperty("title")
    private String title;// "Personal Trainer Name",
    @JsonProperty("descript")
    private String descript;// "",
    @JsonProperty("type")
    private String type;// "text",
//    @JsonProperty("")
//    private String "isrequired": "0",
    @JsonProperty("perstag")
    private String perstag;// "PERSONAL_TRAINER_NAME",
//    @JsonProperty("")
//    private String "defval": "",
//    @JsonProperty("")
//    private String "show_in_list": "0",
//    @JsonProperty("")
//    private String "rows": "0",
//    @JsonProperty("")
//    private String "cols": "0",
//    @JsonProperty("")
//    private String "visible": "1",
//    @JsonProperty("")
//    private String "service": "",
//    @JsonProperty("")
//    private String "ordernum": "0",
    @JsonProperty("cdate")
    private String cdate;//"2019-05-27T19:08:17-05:00",
    @JsonProperty("udate")
    private String udate;// "2019-07-11T22:49:10-05:00",
//    @JsonProperty("")
//    private String "options": [],
//    @JsonProperty("")
//    "relations": [
//        "80"
//        ],
//    @JsonProperty("")
//    "links": {
//    "options": "https://thefitnessplayground.api-us1.com/api/3/fields/37/options",
//            "relations": "https://thefitnessplayground.api-us1.com/api/3/fields/37/relations"
//    },
    @JsonProperty("id")
    private String fieldId;// "37"


    public ACField() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPerstag() {
        return perstag;
    }

    public void setPerstag(String perstag) {
        this.perstag = perstag;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
}
