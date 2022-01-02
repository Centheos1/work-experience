package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DigitalPreExData {

    @JsonProperty("id")
    private long id;
    @JsonProperty("gymName")
    private String gymName;
//    @JsonProperty("")
//    private String formId;
//    @JsonProperty("")
//    private String uniqueId;
//    //    private String form;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("email")
    private String email;
    @JsonProperty("howDidYouHearAboutUs")
    private String howDidYouHearAboutUs;
    @JsonProperty("haveYouBeenAMemberOfAFitnessFacility")
    private String haveYouBeenAMemberOfAFitnessFacility;
    @JsonProperty("previousGymMembershipLocaions")
    private String previousGymMembershipLocaions;
    @JsonProperty("whatBringsYourHereToday")
    private String whatBringsYourHereToday;
    @JsonProperty("areYouCurrentlyExercising")
    private String areYouCurrentlyExercising;
    @JsonProperty("whatIsYourCurrentExercise")
    private String whatIsYourCurrentExercise;
    @JsonProperty("whenWasTheLastTimeYouExercised")
    private String whenWasTheLastTimeYouExercised;
    @JsonProperty("whatExerciseYouEnjoy")
    private String whatExerciseYouEnjoy;
    @JsonProperty("whatExerciseWouldYouLikeToTry")
    private String whatExerciseWouldYouLikeToTry;
    @JsonProperty("whatIntensityDoYouPrefer")
    private String whatIntensityDoYouPrefer;
    @JsonProperty("areasToImprove_front")
    private String areasToImprove_front;
    @JsonProperty("areaToImporve_back")
    private String areaToImporve_back;
    @JsonProperty("goals_body")
    private String goals_body;
    @JsonProperty("goals_health")
    private String goals_health;
    @JsonProperty("goals_fitness")
    private String goals_fitness;
    @JsonProperty("goals_moreInfo")
    private String goals_moreInfo;
    @JsonProperty("goals_specificallyInNext12Weeks")
    private String goals_specificallyInNext12Weeks;
    @JsonProperty("haveYouReachedTheseGoalsBefore")
    private String haveYouReachedTheseGoalsBefore;
    @JsonProperty("whatHelpedYouAchieveYourGoal")
    private String whatHelpedYouAchieveYourGoal;
    @JsonProperty("whatPreventedYouFromAchievingYouGoal")
    private String whatPreventedYouFromAchievingYouGoal;
    @JsonProperty("onAScaleOf1to10HowImportantIsItToAchieveYourGoal")
    private String onAScaleOf1to10HowImportantIsItToAchieveYourGoal;
    @JsonProperty("howManyDaysAWeekDoYouPlanToExercise")
    private String howManyDaysAWeekDoYouPlanToExercise;
    @JsonProperty("doYouFollowATrainingPlan")
    private String doYouFollowATrainingPlan;
    @JsonProperty("doYouHaveAnEatingPlan")
    private String doYouHaveAnEatingPlan;
    @JsonProperty("howEffectiveWasYourPreviousProgram")
    private String howEffectiveWasYourPreviousProgram;
    @JsonProperty("whatElseCanWeDoToHelp")
    private String whatElseCanWeDoToHelp;
    @JsonProperty("areThereAnyMedicalIssuesThatMayPreventYouFromExercise")
    private String areThereAnyMedicalIssuesThatMayPreventYouFromExercise;
    @JsonProperty("medicalConditions")
    private String medicalConditions;
    @JsonProperty("doYouHaveMedicalClearance")
    private String doYouHaveMedicalClearance;
    @JsonProperty("areYouTakingMedication")
    private String areYouTakingMedication;
    @JsonProperty("doYouHaveMedicalClearance_medication")
    private String doYouHaveMedicalClearance_medication;
//    @JsonProperty("")
//    private String signature;
    @JsonProperty("staffName")
    private String staffName;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("whyHaveYouDecidedToMakeChange")
    private String whyHaveYouDecidedToMakeChange;
    @JsonProperty("isPrivateHealthMember")
    private String isPrivateHealthMember;
    @JsonProperty("hadPainOrInjuryLongerThan6Weeks")
    private String hadPainOrInjuryLongerThan6Weeks;
    @JsonProperty("enquiryType")
    private String enquiryType;
    @JsonProperty("mainGoalFirstMonth")
    private String mainGoalFirstMonth;
    @JsonProperty("howCanWeHelpFirstMonth")
    private String howCanWeHelpFirstMonth;
    @JsonProperty("anythingSpecificToSeeInGym")
    private String anythingSpecificToSeeInGym;
    @JsonProperty("notes")
    private String notes;

    public DigitalPreExData() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHowDidYouHearAboutUs() {
        return howDidYouHearAboutUs;
    }

    public void setHowDidYouHearAboutUs(String howDidYouHearAboutUs) {
        this.howDidYouHearAboutUs = howDidYouHearAboutUs;
    }

    public String getHaveYouBeenAMemberOfAFitnessFacility() {
        return haveYouBeenAMemberOfAFitnessFacility;
    }

    public void setHaveYouBeenAMemberOfAFitnessFacility(String haveYouBeenAMemberOfAFitnessFacility) {
        this.haveYouBeenAMemberOfAFitnessFacility = haveYouBeenAMemberOfAFitnessFacility;
    }

    public String getPreviousGymMembershipLocaions() {
        return previousGymMembershipLocaions;
    }

    public void setPreviousGymMembershipLocaions(String previousGymMembershipLocaions) {
        this.previousGymMembershipLocaions = previousGymMembershipLocaions;
    }

    public String getWhatBringsYourHereToday() {
        return whatBringsYourHereToday;
    }

    public void setWhatBringsYourHereToday(String whatBringsYourHereToday) {
        this.whatBringsYourHereToday = whatBringsYourHereToday;
    }

    public String getAreYouCurrentlyExercising() {
        return areYouCurrentlyExercising;
    }

    public void setAreYouCurrentlyExercising(String areYouCurrentlyExercising) {
        this.areYouCurrentlyExercising = areYouCurrentlyExercising;
    }

    public String getWhatIsYourCurrentExercise() {
        return whatIsYourCurrentExercise;
    }

    public void setWhatIsYourCurrentExercise(String whatIsYourCurrentExercise) {
        this.whatIsYourCurrentExercise = whatIsYourCurrentExercise;
    }

    public String getWhenWasTheLastTimeYouExercised() {
        return whenWasTheLastTimeYouExercised;
    }

    public void setWhenWasTheLastTimeYouExercised(String whenWasTheLastTimeYouExercised) {
        this.whenWasTheLastTimeYouExercised = whenWasTheLastTimeYouExercised;
    }

    public String getWhatExerciseYouEnjoy() {
        return whatExerciseYouEnjoy;
    }

    public void setWhatExerciseYouEnjoy(String whatExerciseYouEnjoy) {
        this.whatExerciseYouEnjoy = whatExerciseYouEnjoy;
    }

    public String getWhatExerciseWouldYouLikeToTry() {
        return whatExerciseWouldYouLikeToTry;
    }

    public void setWhatExerciseWouldYouLikeToTry(String whatExerciseWouldYouLikeToTry) {
        this.whatExerciseWouldYouLikeToTry = whatExerciseWouldYouLikeToTry;
    }

    public String getWhatIntensityDoYouPrefer() {
        return whatIntensityDoYouPrefer;
    }

    public void setWhatIntensityDoYouPrefer(String whatIntensityDoYouPrefer) {
        this.whatIntensityDoYouPrefer = whatIntensityDoYouPrefer;
    }

    public String getAreasToImprove_front() {
        return areasToImprove_front;
    }

    public void setAreasToImprove_front(String areasToImprove_front) {
        this.areasToImprove_front = areasToImprove_front;
    }

    public String getAreaToImporve_back() {
        return areaToImporve_back;
    }

    public void setAreaToImporve_back(String areaToImporve_back) {
        this.areaToImporve_back = areaToImporve_back;
    }

    public String getGoals_body() {
        return goals_body;
    }

    public void setGoals_body(String goals_body) {
        this.goals_body = goals_body;
    }

    public String getGoals_health() {
        return goals_health;
    }

    public void setGoals_health(String goals_health) {
        this.goals_health = goals_health;
    }

    public String getGoals_fitness() {
        return goals_fitness;
    }

    public void setGoals_fitness(String goals_fitness) {
        this.goals_fitness = goals_fitness;
    }

    public String getGoals_moreInfo() {
        return goals_moreInfo;
    }

    public void setGoals_moreInfo(String goals_moreInfo) {
        this.goals_moreInfo = goals_moreInfo;
    }

    public String getGoals_specificallyInNext12Weeks() {
        return goals_specificallyInNext12Weeks;
    }

    public void setGoals_specificallyInNext12Weeks(String goals_specificallyInNext12Weeks) {
        this.goals_specificallyInNext12Weeks = goals_specificallyInNext12Weeks;
    }

    public String getHaveYouReachedTheseGoalsBefore() {
        return haveYouReachedTheseGoalsBefore;
    }

    public void setHaveYouReachedTheseGoalsBefore(String haveYouReachedTheseGoalsBefore) {
        this.haveYouReachedTheseGoalsBefore = haveYouReachedTheseGoalsBefore;
    }

    public String getWhatHelpedYouAchieveYourGoal() {
        return whatHelpedYouAchieveYourGoal;
    }

    public void setWhatHelpedYouAchieveYourGoal(String whatHelpedYouAchieveYourGoal) {
        this.whatHelpedYouAchieveYourGoal = whatHelpedYouAchieveYourGoal;
    }

    public String getWhatPreventedYouFromAchievingYouGoal() {
        return whatPreventedYouFromAchievingYouGoal;
    }

    public void setWhatPreventedYouFromAchievingYouGoal(String whatPreventedYouFromAchievingYouGoal) {
        this.whatPreventedYouFromAchievingYouGoal = whatPreventedYouFromAchievingYouGoal;
    }

    public String getOnAScaleOf1to10HowImportantIsItToAchieveYourGoal() {
        return onAScaleOf1to10HowImportantIsItToAchieveYourGoal;
    }

    public void setOnAScaleOf1to10HowImportantIsItToAchieveYourGoal(String onAScaleOf1to10HowImportantIsItToAchieveYourGoal) {
        this.onAScaleOf1to10HowImportantIsItToAchieveYourGoal = onAScaleOf1to10HowImportantIsItToAchieveYourGoal;
    }

    public String getHowManyDaysAWeekDoYouPlanToExercise() {
        return howManyDaysAWeekDoYouPlanToExercise;
    }

    public void setHowManyDaysAWeekDoYouPlanToExercise(String howManyDaysAWeekDoYouPlanToExercise) {
        this.howManyDaysAWeekDoYouPlanToExercise = howManyDaysAWeekDoYouPlanToExercise;
    }

    public String getDoYouFollowATrainingPlan() {
        return doYouFollowATrainingPlan;
    }

    public void setDoYouFollowATrainingPlan(String doYouFollowATrainingPlan) {
        this.doYouFollowATrainingPlan = doYouFollowATrainingPlan;
    }

    public String getDoYouHaveAnEatingPlan() {
        return doYouHaveAnEatingPlan;
    }

    public void setDoYouHaveAnEatingPlan(String doYouHaveAnEatingPlan) {
        this.doYouHaveAnEatingPlan = doYouHaveAnEatingPlan;
    }

    public String getHowEffectiveWasYourPreviousProgram() {
        return howEffectiveWasYourPreviousProgram;
    }

    public void setHowEffectiveWasYourPreviousProgram(String howEffectiveWasYourPreviousProgram) {
        this.howEffectiveWasYourPreviousProgram = howEffectiveWasYourPreviousProgram;
    }

    public String getWhatElseCanWeDoToHelp() {
        return whatElseCanWeDoToHelp;
    }

    public void setWhatElseCanWeDoToHelp(String whatElseCanWeDoToHelp) {
        this.whatElseCanWeDoToHelp = whatElseCanWeDoToHelp;
    }

    public String getAreThereAnyMedicalIssuesThatMayPreventYouFromExercise() {
        return areThereAnyMedicalIssuesThatMayPreventYouFromExercise;
    }

    public void setAreThereAnyMedicalIssuesThatMayPreventYouFromExercise(String areThereAnyMedicalIssuesThatMayPreventYouFromExercise) {
        this.areThereAnyMedicalIssuesThatMayPreventYouFromExercise = areThereAnyMedicalIssuesThatMayPreventYouFromExercise;
    }

    public String getMedicalConditions() {
        return medicalConditions;
    }

    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    public String getDoYouHaveMedicalClearance() {
        return doYouHaveMedicalClearance;
    }

    public void setDoYouHaveMedicalClearance(String doYouHaveMedicalClearance) {
        this.doYouHaveMedicalClearance = doYouHaveMedicalClearance;
    }

    public String getAreYouTakingMedication() {
        return areYouTakingMedication;
    }

    public void setAreYouTakingMedication(String areYouTakingMedication) {
        this.areYouTakingMedication = areYouTakingMedication;
    }

    public String getDoYouHaveMedicalClearance_medication() {
        return doYouHaveMedicalClearance_medication;
    }

    public void setDoYouHaveMedicalClearance_medication(String doYouHaveMedicalClearance_medication) {
        this.doYouHaveMedicalClearance_medication = doYouHaveMedicalClearance_medication;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getWhyHaveYouDecidedToMakeChange() {
        return whyHaveYouDecidedToMakeChange;
    }

    public void setWhyHaveYouDecidedToMakeChange(String whyHaveYouDecidedToMakeChange) {
        this.whyHaveYouDecidedToMakeChange = whyHaveYouDecidedToMakeChange;
    }

    public String getIsPrivateHealthMember() {
        return isPrivateHealthMember;
    }

    public void setIsPrivateHealthMember(String isPrivateHealthMember) {
        this.isPrivateHealthMember = isPrivateHealthMember;
    }

    public String getHadPainOrInjuryLongerThan6Weeks() {
        return hadPainOrInjuryLongerThan6Weeks;
    }

    public void setHadPainOrInjuryLongerThan6Weeks(String hadPainOrInjuryLongerThan6Weeks) {
        this.hadPainOrInjuryLongerThan6Weeks = hadPainOrInjuryLongerThan6Weeks;
    }

    public String getEnquiryType() {
        return enquiryType;
    }

    public void setEnquiryType(String enquiryType) {
        this.enquiryType = enquiryType;
    }

    public String getMainGoalFirstMonth() {
        return mainGoalFirstMonth;
    }

    public void setMainGoalFirstMonth(String mainGoalFirstMonth) {
        this.mainGoalFirstMonth = mainGoalFirstMonth;
    }

    public String getHowCanWeHelpFirstMonth() {
        return howCanWeHelpFirstMonth;
    }

    public void setHowCanWeHelpFirstMonth(String howCanWeHelpFirstMonth) {
        this.howCanWeHelpFirstMonth = howCanWeHelpFirstMonth;
    }

    public String getAnythingSpecificToSeeInGym() {
        return anythingSpecificToSeeInGym;
    }

    public void setAnythingSpecificToSeeInGym(String anythingSpecificToSeeInGym) {
        this.anythingSpecificToSeeInGym = anythingSpecificToSeeInGym;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "DigitalPreExData{" +
                "id=" + id +
                ", gymName='" + gymName + '\'' +
                ", gender='" + gender + '\'' +
                ", dob='" + dob + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", howDidYouHearAboutUs='" + howDidYouHearAboutUs + '\'' +
                ", haveYouBeenAMemberOfAFitnessFacility='" + haveYouBeenAMemberOfAFitnessFacility + '\'' +
                ", previousGymMembershipLocaions='" + previousGymMembershipLocaions + '\'' +
                ", whatBringsYourHereToday='" + whatBringsYourHereToday + '\'' +
                ", areYouCurrentlyExercising='" + areYouCurrentlyExercising + '\'' +
                ", whatIsYourCurrentExercise='" + whatIsYourCurrentExercise + '\'' +
                ", whenWasTheLastTimeYouExercised='" + whenWasTheLastTimeYouExercised + '\'' +
                ", whatExerciseYouEnjoy='" + whatExerciseYouEnjoy + '\'' +
                ", whatExerciseWouldYouLikeToTry='" + whatExerciseWouldYouLikeToTry + '\'' +
                ", whatIntensityDoYouPrefer='" + whatIntensityDoYouPrefer + '\'' +
                ", areasToImprove_front='" + areasToImprove_front + '\'' +
                ", areaToImporve_back='" + areaToImporve_back + '\'' +
                ", goals_body='" + goals_body + '\'' +
                ", goals_health='" + goals_health + '\'' +
                ", goals_fitness='" + goals_fitness + '\'' +
                ", goals_moreInfo='" + goals_moreInfo + '\'' +
                ", goals_specificallyInNext12Weeks='" + goals_specificallyInNext12Weeks + '\'' +
                ", haveYouReachedTheseGoalsBefore='" + haveYouReachedTheseGoalsBefore + '\'' +
                ", whatHelpedYouAchieveYourGoal='" + whatHelpedYouAchieveYourGoal + '\'' +
                ", whatPreventedYouFromAchievingYouGoal='" + whatPreventedYouFromAchievingYouGoal + '\'' +
                ", onAScaleOf1to10HowImportantIsItToAchieveYourGoal='" + onAScaleOf1to10HowImportantIsItToAchieveYourGoal + '\'' +
                ", howManyDaysAWeekDoYouPlanToExercise='" + howManyDaysAWeekDoYouPlanToExercise + '\'' +
                ", doYouFollowATrainingPlan='" + doYouFollowATrainingPlan + '\'' +
                ", doYouHaveAnEatingPlan='" + doYouHaveAnEatingPlan + '\'' +
                ", howEffectiveWasYourPreviousProgram='" + howEffectiveWasYourPreviousProgram + '\'' +
                ", whatElseCanWeDoToHelp='" + whatElseCanWeDoToHelp + '\'' +
                ", areThereAnyMedicalIssuesThatMayPreventYouFromExercise='" + areThereAnyMedicalIssuesThatMayPreventYouFromExercise + '\'' +
                ", medicalConditions='" + medicalConditions + '\'' +
                ", doYouHaveMedicalClearance='" + doYouHaveMedicalClearance + '\'' +
                ", areYouTakingMedication='" + areYouTakingMedication + '\'' +
                ", doYouHaveMedicalClearance_medication='" + doYouHaveMedicalClearance_medication + '\'' +
                ", staffName='" + staffName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", whyHaveYouDecidedToMakeChange='" + whyHaveYouDecidedToMakeChange + '\'' +
                ", isPrivateHealthMember='" + isPrivateHealthMember + '\'' +
                ", hadPainOrInjuryLongerThan6Weeks='" + hadPainOrInjuryLongerThan6Weeks + '\'' +
                ", enquiryType='" + enquiryType + '\'' +
                ", mainGoalFirstMonth='" + mainGoalFirstMonth + '\'' +
                ", howCanWeHelpFirstMonth='" + howCanWeHelpFirstMonth + '\'' +
                ", anythingSpecificToSeeInGym='" + anythingSpecificToSeeInGym + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
