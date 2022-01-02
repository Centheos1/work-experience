package com.fitnessplayground.model.mindbody.api.result;


/**
 * Created by micheal on 19/02/2017.
 */
public class MindBodyApiResult {
    private boolean success;
    private String mindBodyId;
    private String errorMessage;

    public MindBodyApiResult(boolean success, String mindBodyId, String errorMessage) {
        this.success = success;
        this.mindBodyId = mindBodyId;
        this.errorMessage = errorMessage;
    }

    public String getMindBodyId() {
        return mindBodyId;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "MindBodyApiResult{" +
                "mindBodyId='" + mindBodyId + '\'' +
                ", success=" + success +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
