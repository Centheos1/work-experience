package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Comparator;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UIGym implements Comparable<UIGym> {

    @JsonProperty("locationId")
    private Integer locationId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("siteId")
    private Long siteId;
    @JsonProperty("clubManager")
    private UIStaff clubManager;
    @JsonProperty("personalTrainingManager")
    private UIStaff personalTrainingManager;
    @JsonProperty("groupFitnessManager")
    private UIStaff groupFitnessManager;
//    @JsonProperty("membershipConsultants")
//    private TestHashMap membershipConsultants;
//    @JsonProperty("personalTrainers")
//    private TestHashMap personalTrainers;

//    @JsonProperty("membershipConsultants")
//    private HashMap<Long, String> membershipConsultants;
//    @JsonProperty("personalTrainers")
//    private HashMap<Long, String> personalTrainers;

    @JsonProperty("membershipConsultants")
    private List<UIStaff> membershipConsultants;
    @JsonProperty("personalTrainers")
    private List<UIStaff> personalTrainers;

    public UIGym() {
    }



    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public UIStaff getClubManager() {
        return clubManager;
    }

    public void setClubManager(UIStaff clubManager) {
        this.clubManager = clubManager;
    }

    public UIStaff getPersonalTrainingManager() {
        return personalTrainingManager;
    }

    public void setPersonalTrainingManager(UIStaff personalTrainingManager) {
        this.personalTrainingManager = personalTrainingManager;
    }

    public UIStaff getGroupFitnessManager() {
        return groupFitnessManager;
    }

    public void setGroupFitnessManager(UIStaff groupFitnessManager) {
        this.groupFitnessManager = groupFitnessManager;
    }

    public List<UIStaff> getMembershipConsultants() {
        return membershipConsultants;
    }

    public void setMembershipConsultants(List<UIStaff> membershipConsultants) {
        this.membershipConsultants = membershipConsultants;
    }

    public List<UIStaff> getPersonalTrainers() {
        return personalTrainers;
    }

    public void setPersonalTrainers(List<UIStaff> personalTrainers) {
        this.personalTrainers = personalTrainers;
    }

    @Override
    public String toString() {
        return "UIGym{" +
                "locationId=" + locationId +
                ", name='" + name + '\'' +
                ", siteId=" + siteId +
                ", clubManager=" + clubManager +
                ", personalTrainingManager=" + personalTrainingManager +
                ", groupFitnessManager=" + groupFitnessManager +
                ", membershipConsultants=" + membershipConsultants +
//                ", personalTrainers=" + personalTrainers +
                '}';
    }


    public static Comparator<UIGym> UIGymComparator = new Comparator<UIGym>() {
        @Override
        public int compare(UIGym o1, UIGym o2) {
            return o1.getLocationId() - o2.getLocationId();
        }
    };

    @Override
    public int compareTo(UIGym o) {
        return 0;
    }
}
