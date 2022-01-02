package com.fitnessplayground.dao.domain.fpSourceDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FPPTParQData {

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("ACID")
    private String ACID;
    @JsonProperty("howManyTimesAWeekAreYouExercising")
    private String howManyTimesAWeekAreYouExercising;
    @JsonProperty("haveYouExercisedInThePast")
    private String haveYouExercisedInThePast;
    @JsonProperty("whatTypeOfExerciseDoYouEnjoy")
    private String whatTypeOfExerciseDoYouEnjoy;
    @JsonProperty("whatAreYourEatingHabits")
    private String whatAreYourEatingHabits;
    @JsonProperty("areYouPhysicallyActiveAtWork")
    private String areYouPhysicallyActiveAtWork;
    @JsonProperty("howDoYouRateYourEnergyLevels")
    private String howDoYouRateYourEnergyLevels;
    @JsonProperty("howMuchSleepToYouGetANight")
    private String howMuchSleepToYouGetANight;
    @JsonProperty("howDoYouRateYourStressLevels")
    private String howDoYouRateYourStressLevels;
    @JsonProperty("howDoYouDestress")
    private String howDoYouDestress;
    @JsonProperty("whereDoYouStoreTheMajorityOfYourBodyFat")
    private String whereDoYouStoreTheMajorityOfYourBodyFat;
    @JsonProperty("doYouHaveAnyPainOrInjuries")
    private String doYouHaveAnyPainOrInjuries;
    @JsonProperty("doYouHaveAnyOtherInjuriesIllnessAilments")
    private String doYouHaveAnyOtherInjuriesIllnessAilments;
    @JsonProperty("pleaseGiveDetails")
    private String pleaseGiveDetails;
    @JsonProperty("anyOtherHealthConcerns")
    private String anyOtherHealthConcerns;
    @JsonProperty("details_HealthConcerns")
    private String details_HealthConcerns;
    @JsonProperty("haveYouTriedToAchieveThisBefore")
    private String haveYouTriedToAchieveThisBefore;
    @JsonProperty("onAScaleOf1to5HowImportantIsThisToYou")
    private String onAScaleOf1to5HowImportantIsThisToYou;
    @JsonProperty("whyIsThisSoImportantToYou")
    private String whyIsThisSoImportantToYou;
    @JsonProperty("WhatPreventedYouFromAchievingYourGoal")
    private String WhatPreventedYouFromAchievingYourGoal;
    @JsonProperty("haveYouWorkedWithAPersonalTrainer")
    private String haveYouWorkedWithAPersonalTrainer;
    @JsonProperty("whatWereYourFavouritePartsOfWorkingWithAPT")
    private String whatWereYourFavouritePartsOfWorkingWithAPT;
    @JsonProperty("details_NeedsToBeSuccessful")
    private String details_NeedsToBeSuccessful;

    public FPPTParQData() {
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getACID() {
        return ACID;
    }

    public void setACID(String ACID) {
        this.ACID = ACID;
    }

    public String getHowManyTimesAWeekAreYouExercising() {
        return howManyTimesAWeekAreYouExercising;
    }

    public void setHowManyTimesAWeekAreYouExercising(String howManyTimesAWeekAreYouExercising) {
        this.howManyTimesAWeekAreYouExercising = howManyTimesAWeekAreYouExercising;
    }

    public String getHaveYouExercisedInThePast() {
        return haveYouExercisedInThePast;
    }

    public void setHaveYouExercisedInThePast(String haveYouExercisedInThePast) {
        this.haveYouExercisedInThePast = haveYouExercisedInThePast;
    }

    public String getWhatTypeOfExerciseDoYouEnjoy() {
        return whatTypeOfExerciseDoYouEnjoy;
    }

    public void setWhatTypeOfExerciseDoYouEnjoy(String whatTypeOfExerciseDoYouEnjoy) {
        this.whatTypeOfExerciseDoYouEnjoy = whatTypeOfExerciseDoYouEnjoy;
    }

    public String getWhatAreYourEatingHabits() {
        return whatAreYourEatingHabits;
    }

    public void setWhatAreYourEatingHabits(String whatAreYourEatingHabits) {
        this.whatAreYourEatingHabits = whatAreYourEatingHabits;
    }

    public String getAreYouPhysicallyActiveAtWork() {
        return areYouPhysicallyActiveAtWork;
    }

    public void setAreYouPhysicallyActiveAtWork(String areYouPhysicallyActiveAtWork) {
        this.areYouPhysicallyActiveAtWork = areYouPhysicallyActiveAtWork;
    }

    public String getHowDoYouRateYourEnergyLevels() {
        return howDoYouRateYourEnergyLevels;
    }

    public void setHowDoYouRateYourEnergyLevels(String howDoYouRateYourEnergyLevels) {
        this.howDoYouRateYourEnergyLevels = howDoYouRateYourEnergyLevels;
    }

    public String getHowMuchSleepToYouGetANight() {
        return howMuchSleepToYouGetANight;
    }

    public void setHowMuchSleepToYouGetANight(String howMuchSleepToYouGetANight) {
        this.howMuchSleepToYouGetANight = howMuchSleepToYouGetANight;
    }

    public String getHowDoYouRateYourStressLevels() {
        return howDoYouRateYourStressLevels;
    }

    public void setHowDoYouRateYourStressLevels(String howDoYouRateYourStressLevels) {
        this.howDoYouRateYourStressLevels = howDoYouRateYourStressLevels;
    }

    public String getHowDoYouDestress() {
        return howDoYouDestress;
    }

    public void setHowDoYouDestress(String howDoYouDestress) {
        this.howDoYouDestress = howDoYouDestress;
    }

    public String getWhereDoYouStoreTheMajorityOfYourBodyFat() {
        return whereDoYouStoreTheMajorityOfYourBodyFat;
    }

    public void setWhereDoYouStoreTheMajorityOfYourBodyFat(String whereDoYouStoreTheMajorityOfYourBodyFat) {
        this.whereDoYouStoreTheMajorityOfYourBodyFat = whereDoYouStoreTheMajorityOfYourBodyFat;
    }

    public String getDoYouHaveAnyPainOrInjuries() {
        return doYouHaveAnyPainOrInjuries;
    }

    public void setDoYouHaveAnyPainOrInjuries(String doYouHaveAnyPainOrInjuries) {
        this.doYouHaveAnyPainOrInjuries = doYouHaveAnyPainOrInjuries;
    }

    public String getDoYouHaveAnyOtherInjuriesIllnessAilments() {
        return doYouHaveAnyOtherInjuriesIllnessAilments;
    }

    public void setDoYouHaveAnyOtherInjuriesIllnessAilments(String doYouHaveAnyOtherInjuriesIllnessAilments) {
        this.doYouHaveAnyOtherInjuriesIllnessAilments = doYouHaveAnyOtherInjuriesIllnessAilments;
    }

    public String getPleaseGiveDetails() {
        return pleaseGiveDetails;
    }

    public void setPleaseGiveDetails(String pleaseGiveDetails) {
        this.pleaseGiveDetails = pleaseGiveDetails;
    }

    public String getAnyOtherHealthConcerns() {
        return anyOtherHealthConcerns;
    }

    public void setAnyOtherHealthConcerns(String anyOtherHealthConcerns) {
        this.anyOtherHealthConcerns = anyOtherHealthConcerns;
    }

    public String getDetails_HealthConcerns() {
        return details_HealthConcerns;
    }

    public void setDetails_HealthConcerns(String details_HealthConcerns) {
        this.details_HealthConcerns = details_HealthConcerns;
    }

    public String getHaveYouTriedToAchieveThisBefore() {
        return haveYouTriedToAchieveThisBefore;
    }

    public void setHaveYouTriedToAchieveThisBefore(String haveYouTriedToAchieveThisBefore) {
        this.haveYouTriedToAchieveThisBefore = haveYouTriedToAchieveThisBefore;
    }

    public String getOnAScaleOf1to5HowImportantIsThisToYou() {
        return onAScaleOf1to5HowImportantIsThisToYou;
    }

    public void setOnAScaleOf1to5HowImportantIsThisToYou(String onAScaleOf1to5HowImportantIsThisToYou) {
        this.onAScaleOf1to5HowImportantIsThisToYou = onAScaleOf1to5HowImportantIsThisToYou;
    }

    public String getWhyIsThisSoImportantToYou() {
        return whyIsThisSoImportantToYou;
    }

    public void setWhyIsThisSoImportantToYou(String whyIsThisSoImportantToYou) {
        this.whyIsThisSoImportantToYou = whyIsThisSoImportantToYou;
    }

    public String getWhatPreventedYouFromAchievingYourGoal() {
        return WhatPreventedYouFromAchievingYourGoal;
    }

    public void setWhatPreventedYouFromAchievingYourGoal(String whatPreventedYouFromAchievingYourGoal) {
        WhatPreventedYouFromAchievingYourGoal = whatPreventedYouFromAchievingYourGoal;
    }

    public String getHaveYouWorkedWithAPersonalTrainer() {
        return haveYouWorkedWithAPersonalTrainer;
    }

    public void setHaveYouWorkedWithAPersonalTrainer(String haveYouWorkedWithAPersonalTrainer) {
        this.haveYouWorkedWithAPersonalTrainer = haveYouWorkedWithAPersonalTrainer;
    }

    public String getWhatWereYourFavouritePartsOfWorkingWithAPT() {
        return whatWereYourFavouritePartsOfWorkingWithAPT;
    }

    public void setWhatWereYourFavouritePartsOfWorkingWithAPT(String whatWereYourFavouritePartsOfWorkingWithAPT) {
        this.whatWereYourFavouritePartsOfWorkingWithAPT = whatWereYourFavouritePartsOfWorkingWithAPT;
    }

    public String getDetails_NeedsToBeSuccessful() {
        return details_NeedsToBeSuccessful;
    }

    public void setDetails_NeedsToBeSuccessful(String details_NeedsToBeSuccessful) {
        this.details_NeedsToBeSuccessful = details_NeedsToBeSuccessful;
    }

    @Override
    public String toString() {
        return "FPPTParQData{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createDate='" + createDate + '\'' +
                ", ACID='" + ACID + '\'' +
                ", howManyTimesAWeekAreYouExercising='" + howManyTimesAWeekAreYouExercising + '\'' +
                ", haveYouExercisedInThePast='" + haveYouExercisedInThePast + '\'' +
                ", whatTypeOfExerciseDoYouEnjoy='" + whatTypeOfExerciseDoYouEnjoy + '\'' +
                ", whatAreYourEatingHabits='" + whatAreYourEatingHabits + '\'' +
                ", areYouPhysicallyActiveAtWork='" + areYouPhysicallyActiveAtWork + '\'' +
                ", howDoYouRateYourEnergyLevels='" + howDoYouRateYourEnergyLevels + '\'' +
                ", howMuchSleepToYouGetANight='" + howMuchSleepToYouGetANight + '\'' +
                ", howDoYouRateYourStressLevels='" + howDoYouRateYourStressLevels + '\'' +
                ", howDoYouDestress='" + howDoYouDestress + '\'' +
                ", whereDoYouStoreTheMajorityOfYourBodyFat='" + whereDoYouStoreTheMajorityOfYourBodyFat + '\'' +
                ", doYouHaveAnyPainOrInjuries='" + doYouHaveAnyPainOrInjuries + '\'' +
                ", doYouHaveAnyOtherInjuriesIllnessAilments='" + doYouHaveAnyOtherInjuriesIllnessAilments + '\'' +
                ", pleaseGiveDetails='" + pleaseGiveDetails + '\'' +
                ", anyOtherHealthConcerns='" + anyOtherHealthConcerns + '\'' +
                ", details_HealthConcerns='" + details_HealthConcerns + '\'' +
                ", haveYouTriedToAchieveThisBefore='" + haveYouTriedToAchieveThisBefore + '\'' +
                ", onAScaleOf1to5HowImportantIsThisToYou='" + onAScaleOf1to5HowImportantIsThisToYou + '\'' +
                ", whyIsThisSoImportantToYou='" + whyIsThisSoImportantToYou + '\'' +
                ", WhatPreventedYouFromAchievingYourGoal='" + WhatPreventedYouFromAchievingYourGoal + '\'' +
                ", haveYouWorkedWithAPersonalTrainer='" + haveYouWorkedWithAPersonalTrainer + '\'' +
                ", whatWereYourFavouritePartsOfWorkingWithAPT='" + whatWereYourFavouritePartsOfWorkingWithAPT + '\'' +
                ", details_NeedsToBeSuccessful='" + details_NeedsToBeSuccessful + '\'' +
                '}';
    }
}
