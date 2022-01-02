package com.fitnessplayground.dao.domain.reportDto;

import com.fitnessplayground.dao.domain.CancellationData;
import com.fitnessplayground.dao.domain.PtFeedbackData;

import java.util.ArrayList;

public class PtFeedbackReportData {

    ArrayList<PtFeedbackData> ptFeedbackData;
    ArrayList<CancellationData> ptCancellationData;

    public PtFeedbackReportData() {
    }

    public ArrayList<PtFeedbackData> getPtFeedbackData() {
        return ptFeedbackData;
    }

    public void setPtFeedbackData(ArrayList<PtFeedbackData> ptFeedbackData) {
        this.ptFeedbackData = ptFeedbackData;
    }

    public ArrayList<CancellationData> getPtCancellationData() {
        return ptCancellationData;
    }

    public void setPtCancellationData(ArrayList<CancellationData> ptCancellationData) {
        this.ptCancellationData = ptCancellationData;
    }

    @Override
    public String toString() {
        return "PtFeedbackReportData{" +
                "ptFeedbackData=" + ptFeedbackData +
                ", ptCancellationData=" + ptCancellationData +
                '}';
    }
}
