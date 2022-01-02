package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACTag;

import javax.persistence.*;

@Entity
public class AcEmailTag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String tagType;// "contact",
    private String tag;// "Opened 2 weeks free Bunker Email",
    private String description;// "",
    private String subscriberCount;// "1",
    private String tagId;// "79"

    public AcEmailTag() {
    }

    public static AcEmailTag create(ACTag tag) {
        AcEmailTag acEmailTag = new AcEmailTag();
        return build(tag, acEmailTag);
    }

    public static AcEmailTag update(ACTag tag, AcEmailTag acEmailTag) {
        return build(tag, acEmailTag);
    }

    private static AcEmailTag build(ACTag t, AcEmailTag acEmailTag) {

        acEmailTag.setTagType(t.getTagType());
        acEmailTag.setTag(t.getTag());
        acEmailTag.setDescription(t.getDescription());
        acEmailTag.setSubscriberCount(t.getSubscriberCount());
        acEmailTag.setTagId(t.getTagId());

        return acEmailTag;
    }

    public long getId() {
        return id;
    }

    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubscriberCount() {
        return subscriberCount;
    }

    public void setSubscriberCount(String subscriberCount) {
        this.subscriberCount = subscriberCount;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    @Override
    public String toString() {
        return "AcEmailTag{" +
                "id=" + id +
                ", tagType='" + tagType + '\'' +
                ", tag='" + tag + '\'' +
                ", description='" + description + '\'' +
                ", subscriberCount='" + subscriberCount + '\'' +
                ", tagId='" + tagId + '\'' +
                '}';
    }
}
