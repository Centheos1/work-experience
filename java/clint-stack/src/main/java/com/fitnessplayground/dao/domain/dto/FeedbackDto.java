package com.fitnessplayground.dao.domain.dto;

/**
 * Created by micheal on 24/07/2017.
 */
public class FeedbackDto {

    private String id;
    private String createDate;
    private String howDidWeDo;
    private String whatDidYouDo;
    private String gym;
    private String comment;

    public FeedbackDto() {
    }

    public FeedbackDto(String id, String gym) {
        this.id = id;
        this.gym = gym;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateDate() { return createDate; }

    public void setCreateDate(String createDate) { this.createDate = createDate; }

    public String getHowDidWeDo() {
        return howDidWeDo;
    }

    public void setHowDidWeDo(String howDidWeDo) {
        this.howDidWeDo = howDidWeDo;
    }

    public String getWhatDidYouDo() {
        return whatDidYouDo;
    }

    public void setWhatDidYouDo(String whatDidYouDo) {
        this.whatDidYouDo = whatDidYouDo;
    }

    public String getGym() {
        return gym;
    }

    public void setGym(String gym) {
        this.gym = gym;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
