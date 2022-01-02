package com.fitnessplayground.dao.domain.temp;

/**
 * Created by micheal on 4/03/2017.
 */
public class SubmissionArray {
    private String value;
    private String label;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFullValue() {
        return label + " " + value;
    }


    @Override
    public String toString() {
        return "SubmissionArray{" +
                "value='" + value + '\'' +
                ", label='" + label + '\'' +
                '}';
    }
}
