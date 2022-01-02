package com.fitnessplayground.dao.domain.mboDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Class {

    @JsonProperty("ClassScheduleId")
    private Integer classScheduleId;// 302,
    @JsonProperty("Visits")
    private ArrayList<Visit> visits;// null,
    @JsonProperty("Clients")
    private ArrayList<Client> clients;// [],
    @JsonProperty("Location")
    private Location location;
//    @JsonProperty("Resource")
//    "Resource": null,
    @JsonProperty("MaxCapacity")
    private Integer maxCapacity;// 16,
    @JsonProperty("WebCapacity")
    private Integer webCapacity;//: 16,
    @JsonProperty("TotalBooked")
    private Integer totalBooked;// 0,
    @JsonProperty("TotalBookedWaitlist")
    private Integer totalBookedWaitlist;// 0,
    @JsonProperty("WebBooked")
    private Integer webBooked;// 0,
//    @JsonProperty("")
//    "SemesterId": null,
    @JsonProperty("IsCanceled")
    private Boolean isCanceled;// true,
    @JsonProperty("Substitute")
    private Boolean substitute;// false,
    @JsonProperty("Active")
    private Boolean active;// true,
    @JsonProperty("IsWaitlistAvailable")
    private Boolean isWaitlistAvailable;// false,
    @JsonProperty("IsEnrolled")
    private Boolean isEnrolled;// null,
    @JsonProperty("HideCancel")
    private Boolean hideCancel;// true,
    @JsonProperty("Id")
    private Long classId;// 298498,
    @JsonProperty("IsAvailable")
    private Boolean isAvailable;// true,
    @JsonProperty("StartDateTime")
    private String startDateTime;// "2019-07-13T10:30:00",
    @JsonProperty("EndDateTime")
    private String endDateTime;// "2019-07-13T11:30:00",
    @JsonProperty("LastModifiedDateTime")
    private String lastModifiedDateTime;// "0001-01-01T00:00:00",
    @JsonProperty("ClassDescription")
    private ClassDescription classDescription;
    @JsonProperty("SessionType")
    private SessionType sessionType;
    @JsonProperty("Category")
    private String category;// "Gym classes",
    @JsonProperty("CategoryId")
    private Integer categoryId;// 75,
    @JsonProperty("Subcategory")
    private String subcategory;// "Weight training",
    @JsonProperty("SubcategoryId")
    private Integer subcategoryId;// 91
    @JsonProperty("Staff")
    private Staff staff;
    @JsonProperty("BookingWindow")
    private BookingWindow bookingWindow;
    @JsonProperty("BookingStatus")
    private String bookingStatus;// "BookAndPayLater"

    public Class() {
    }

    public Integer getClassScheduleId() {
        return classScheduleId;
    }

    public void setClassScheduleId(Integer classScheduleId) {
        this.classScheduleId = classScheduleId;
    }

    public ArrayList<Visit> getVisits() {
        return visits;
    }

    public void setVisits(ArrayList<Visit> visits) {
        this.visits = visits;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Integer getWebCapacity() {
        return webCapacity;
    }

    public void setWebCapacity(Integer webCapacity) {
        this.webCapacity = webCapacity;
    }

    public Integer getTotalBooked() {
        return totalBooked;
    }

    public void setTotalBooked(Integer totalBooked) {
        this.totalBooked = totalBooked;
    }

    public Integer getTotalBookedWaitlist() {
        return totalBookedWaitlist;
    }

    public void setTotalBookedWaitlist(Integer totalBookedWaitlist) {
        this.totalBookedWaitlist = totalBookedWaitlist;
    }

    public Integer getWebBooked() {
        return webBooked;
    }

    public void setWebBooked(Integer webBooked) {
        this.webBooked = webBooked;
    }

    public Boolean getCanceled() {
        return isCanceled;
    }

    public void setCanceled(Boolean canceled) {
        isCanceled = canceled;
    }

    public Boolean getSubstitute() {
        return substitute;
    }

    public void setSubstitute(Boolean substitute) {
        this.substitute = substitute;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getWaitlistAvailable() {
        return isWaitlistAvailable;
    }

    public void setWaitlistAvailable(Boolean waitlistAvailable) {
        isWaitlistAvailable = waitlistAvailable;
    }

    public Boolean getEnrolled() {
        return isEnrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        isEnrolled = enrolled;
    }

    public Boolean getHideCancel() {
        return hideCancel;
    }

    public void setHideCancel(Boolean hideCancel) {
        this.hideCancel = hideCancel;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(String lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public ClassDescription getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(ClassDescription classDescription) {
        this.classDescription = classDescription;
    }

    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public Integer getSubcategoryId() {
        return subcategoryId;
    }

    public void setSubcategoryId(Integer subcategoryId) {
        this.subcategoryId = subcategoryId;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public BookingWindow getBookingWindow() {
        return bookingWindow;
    }

    public void setBookingWindow(BookingWindow bookingWindow) {
        this.bookingWindow = bookingWindow;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    @Override
    public String  toString() {
        return "Class{" +
                "classScheduleId=" + classScheduleId +
                ", visits=" + visits +
                ", clients=" + clients +
                ", location=" + location +
                ", maxCapacity=" + maxCapacity +
                ", webCapacity=" + webCapacity +
                ", totalBooked=" + totalBooked +
                ", totalBookedWaitlist=" + totalBookedWaitlist +
                ", webBooked=" + webBooked +
                ", isCanceled=" + isCanceled +
                ", substitute=" + substitute +
                ", active=" + active +
                ", isWaitlistAvailable=" + isWaitlistAvailable +
                ", isEnrolled=" + isEnrolled +
                ", hideCancel=" + hideCancel +
                ", classId=" + classId +
                ", isAvailable=" + isAvailable +
                ", startDateTime='" + startDateTime + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", lastModifiedDateTime='" + lastModifiedDateTime + '\'' +
                ", classDescription=" + classDescription +
                ", sessionType=" + sessionType +
                ", category='" + category + '\'' +
                ", categoryId=" + categoryId +
                ", subcategory='" + subcategory + '\'' +
                ", subcategoryId=" + subcategoryId +
                ", staff=" + staff +
                ", bookingWindow=" + bookingWindow +
                ", bookingStatus='" + bookingStatus + '\'' +
                '}';
    }
}
