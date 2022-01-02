package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class PdfApiResponse {

    @JsonProperty("enrolmentDataId")
    private String enrolmentDataId;
    @JsonProperty("fileName")
    private String fileName;
    @JsonProperty("encodedPdf")
    private String encodedPdf;
    @JsonProperty("mediaType")
    private String mediaType;

    public PdfApiResponse() {
    }

    public String getEnrolmentDataId() {
        return enrolmentDataId;
    }

    public void setEnrolmentDataId(String enrolmentDataId) {
        this.enrolmentDataId = enrolmentDataId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getEncodedPdf() {
        return encodedPdf;
    }

    public void setEncodedPdf(String encodedPdf) {
        this.encodedPdf = encodedPdf;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    @Override
    public String toString() {
        return "PdfApiResponse{" +
                "enrolmentDataId='" + enrolmentDataId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", encodedPdf='" + encodedPdf + '\'' +
                ", mediaType='" + mediaType + '\'' +
                '}';
    }
}
