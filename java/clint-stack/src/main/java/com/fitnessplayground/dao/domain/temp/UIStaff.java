package com.fitnessplayground.dao.domain.temp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitnessplayground.util.Constants;

import java.util.Comparator;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UIStaff implements Comparable<UIStaff> {

    @JsonProperty("name")
    private String name;
    @JsonProperty("mboId")
    private String mboIdAndSiteId;
    @JsonProperty("role")
    private String role;

    public UIStaff() {
    }

    public UIStaff(String name, String mboIdAndSiteId, String role) {
        this.name = name;
        this.mboIdAndSiteId = mboIdAndSiteId;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMboIdAndSiteId() {
        return mboIdAndSiteId;
    }

    public void setMboIdAndSiteId(String mboIdAndSiteId) {
        this.mboIdAndSiteId = mboIdAndSiteId;
    }

    public void setMboIdAndSiteId(Long mboId, Long siteId) {
        this.mboIdAndSiteId = mboId+ Constants.MBO_ID_AND_SITE_IS_SPLIT_CHARACTER +siteId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int compareTo(UIStaff o) {
        return o.getName().compareToIgnoreCase(this.getName());
    }

    public static Comparator<UIStaff> UIStaffComparator = new Comparator<UIStaff>() {
        @Override
        public int compare(UIStaff o1, UIStaff o2) {
            String uiStaff1 = o1.getName().toLowerCase();
            String uiStaff2 = o2.getName().toLowerCase();

            return uiStaff1.compareTo(uiStaff2);
        }
    };
}
