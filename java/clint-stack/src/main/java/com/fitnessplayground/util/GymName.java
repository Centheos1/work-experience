package com.fitnessplayground.util;

import org.springframework.beans.factory.annotation.Value;

public enum GymName {

    ZETLAND(Constants.ZETLAND_LOCATION_ID, Constants.ZETLAND_GYM_NAME, Constants.SYDNEY_SITE_ID, null, null, null),
    BUNKER(Constants.BUNKER_LOCATION_ID, Constants.BUNKER_GYM_NAME, Constants.SYDNEY_SITE_ID, null, null, null),
    NEWTOWN(Constants.NEWTOWN_LOCATION_ID, Constants.NEWTOWN_GYM_NAME, Constants.SYDNEY_SITE_ID, null, null, null),
    MARRICKVILLE(Constants.MARRICKVILLE_LOCATION_ID, Constants.MARRICKVILLE_GYM_NAME, Constants.SYDNEY_SITE_ID, null, null, null),
    GATEWAY(Constants.GATEWAY_LOCATION_ID, Constants.GATEWAY_GYM_NAME, Constants.DARWIN_SITE_ID, null, null, null),
    SURRY_HILLS(Constants.SURRY_HILLS_LOCATION_ID, Constants.SURRY_HILLS_GYM_NAME, Constants.SYDNEY_SITE_ID, null, null, null),
    FITNESS_PLAYGROUND(0, "Fitness Playground", Constants.SYDNEY_SITE_ID, null, null, null);

    private Integer locationId;
    private String name;
    private Long siteId;
    private Long clubManager;
    private Long personalTrainingManager;
    private Long groupFitnessManager;


//    GymName(int locationId, String name, long siteId) {
//        this.locationId = locationId;
//        this.name = name;
//        this.siteId = siteId;
//    }

    GymName(Integer locationId, String name, Long siteId, Long clubManager, Long personalTrainingManager, Long groupFitnessManager) {
        this.locationId = locationId;
        this.name = name;
        this.siteId = siteId;
        this.clubManager = clubManager;
        this.personalTrainingManager = personalTrainingManager;
        this.groupFitnessManager = groupFitnessManager;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public Long getSiteId() {
        return siteId;
    }

    public Long getClubManager() {
        return clubManager;
    }

    public Long getPersonalTrainingManager() {
        return personalTrainingManager;
    }

    public Long getGroupFitnessManager() {
        return groupFitnessManager;
    }

    public static GymName getGymName(int locationId) {
        GymName gymName = FITNESS_PLAYGROUND;
        switch (locationId) {
            case Constants.SURRY_HILLS_LOCATION_ID:
                return SURRY_HILLS;
            case Constants.BUNKER_LOCATION_ID:
                return BUNKER;
            case Constants.GATEWAY_LOCATION_ID:
                return GATEWAY;
            case Constants.MARRICKVILLE_LOCATION_ID:
                return MARRICKVILLE;
            case Constants.NEWTOWN_LOCATION_ID:
                return NEWTOWN;
            case Constants.ZETLAND_LOCATION_ID:
                return ZETLAND;
        }
        return gymName;
    }

    public static String convertLocationId(String locationId) {
        if (locationId == null) {
            return locationId;
        }
        int _locationId = Integer.parseInt(locationId);
        _locationId = Math.abs(_locationId);

        locationId = String.valueOf(_locationId);

        return locationId;
    }

    public static Integer getLocationIdByGymName(String gymName) {

        if (gymName == null) return null;

        if (gymName.equals("The Bunker")) gymName = "Bunker";

        switch (gymName) {
            case Constants.SURRY_HILLS_GYM_NAME:
                return Constants.SURRY_HILLS_LOCATION_ID;
            case Constants.NEWTOWN_GYM_NAME:
                return Constants.NEWTOWN_LOCATION_ID;
            case Constants.MARRICKVILLE_GYM_NAME:
                return Constants.MARRICKVILLE_LOCATION_ID;
            case Constants.GATEWAY_GYM_NAME:
                return Constants.GATEWAY_LOCATION_ID;
            case Constants.BUNKER_GYM_NAME:
                return Constants.BUNKER_LOCATION_ID;
            case Constants.ZETLAND_GYM_NAME:
                return Constants.ZETLAND_LOCATION_ID;
            default:
                return null;
        }
    }

}
