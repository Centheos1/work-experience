package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MboUploadDocumentRequest {

    @JsonProperty("ClientId")
    private String clientId;
    @JsonProperty("File")
    private File file;

    public MboUploadDocumentRequest() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
