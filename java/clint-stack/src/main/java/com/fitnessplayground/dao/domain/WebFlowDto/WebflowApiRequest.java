package com.fitnessplayground.dao.domain.WebFlowDto;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WebflowApiRequest {

    //    {
//        "name":"Free PT session",
//        "site":"5ed46aed697783fcc0a86d90",
//        "data":{
//            "Name":"Ed Test",
//            "Email":"test@centheos.com",
//            "Phone":"0412345678",
//            "Location":"Marrickville",
//            "Fitness Challenge":"Difficult to build muscle",
//            "Training Experience":"Currently training"
//    },
//    "d":"2020-07-30T03:22:02.512Z",
//    "_id":"5f223cdafd51884f61ad7689"
//    }

    @JsonProperty("name")
    private String name;
    @JsonProperty("site")
    private String site;
    @JsonProperty("data")
    private WebflowApiLeadSubmissionData data;
    @JsonProperty("d")
    private String d;
    @JsonProperty("_id")
    private String _id;

    public WebflowApiRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public WebflowApiLeadSubmissionData getData() {
        return data;
    }

    public void setData(WebflowApiLeadSubmissionData data) {
        this.data = data;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return "WebflowApiRequest{" +
                "name='" + name + '\'' +
                ", site='" + site + '\'' +
                ", data=" + data +
                ", d='" + d + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}
