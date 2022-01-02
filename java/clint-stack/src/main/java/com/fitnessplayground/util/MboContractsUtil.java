package com.fitnessplayground.util;

import java.util.HashMap;

public class MboContractsUtil {

    public static HashMap<Integer, String> buildContractsMap_Sydney() {

        HashMap<Integer, String> contractsMap = new HashMap<>();

        contractsMap.put(Constants.ACCESS_KEY_ID, Constants.ACCESS_KEY);
//        contractsMap.put(Constants.BUNKER_ACCESS_KEY_ID, Constants.BUNKER_ACCESS_KEY);

        contractsMap.put(Constants.COUPON_CODE_7_DAYS_ID, Constants.COUPON_CODE_7_DAYS);
        contractsMap.put(Constants.COUPON_CODE_14_DAYS_ID, Constants.COUPON_CODE_14_DAYS);
        contractsMap.put(Constants.COUPON_CODE_21_DAYS_ID, Constants.COUPON_CODE_21_DAYS);
        contractsMap.put(Constants.COUPON_CODE_30_DAYS_ID, Constants.COUPON_CODE_30_DAYS);

        contractsMap.put(Constants.PGT_5PACK_ID, Constants.PGT_5PACK);
        contractsMap.put(Constants.PGT_10PACK_ID, Constants.PGT_10PACK);
        contractsMap.put(Constants.BUNKER_PGT_5PACK_ID, Constants.BUNKER_PGT_5PACK);
        contractsMap.put(Constants.BUNKER_PGT_10PACK_ID, Constants.BUNKER_PGT_10PACK);
//
        contractsMap.put(Constants.STARTERPACK_PTPACK_ID, Constants.STARTERPACK_PTPACK);
        contractsMap.put(Constants.STARTERPACK_TRANSFORMER_ID, Constants.STARTERPACK_TRANSFORMER);
        contractsMap.put(Constants.BUNKER_STARTERPACK_PTPACK_ID, Constants.BUNKER_STARTERPACK_PTPACK);

//        ____________________________________________________________________________________ Fitness Playground
        contractsMap.put(Constants.FP_GYM_ID, Constants.FP_GYM);
        contractsMap.put(Constants.FP_PLAY_ID, Constants.FP_PLAY);

        contractsMap.put(Constants.FP_GYM_NOCOMMIT_ID, Constants.FP_GYM_NOCOMMIT);
        contractsMap.put(Constants.FP_PLAY_NOCOMMIT_ID, Constants.FP_PLAY_NOCOMMIT);

        contractsMap.put(Constants.CORPORATE_FP_GYM_ID, Constants.CORPORATE_FP_GYM);
        contractsMap.put(Constants.CORPORATE_FP_PLAY_ID, Constants.CORPORATE_FP_PLAY);

        contractsMap.put(Constants.CORPORATE_FP_GYM_NOCOMMIT_ID, Constants.CORPORATE_FP_GYM_NOCOMMIT);
        contractsMap.put(Constants.CORPORATE_FP_PLAY_NOCOMMIT_ID, Constants.CORPORATE_FP_PLAY_NOCOMMIT);

        contractsMap.put(Constants.FP_GYM_1MTH_ID, Constants.FP_GYM_1MTH);
//        contractsMap.put(Constants.FP_GYM_2MTH_ID, Constants.FP_GYM_2MTH);
        contractsMap.put(Constants.FP_GYM_3MTH_ID, Constants.FP_GYM_3MTH);
        contractsMap.put(Constants.FP_GYM_6MTH_ID, Constants.FP_GYM_6MTH);
        contractsMap.put(Constants.FP_GYM_12MTH_ID, Constants.FP_GYM_12MTH);

        contractsMap.put(Constants.FP_PLAY_1MTH_ID, Constants.FP_PLAY_1MTH);
//        contractsMap.put(Constants.FP_PLAY_2MTH_ID, Constants.FP_PLAY_2MTH);
        contractsMap.put(Constants.FP_PLAY_3MTH_ID, Constants.FP_PLAY_3MTH);
        contractsMap.put(Constants.FP_PLAY_6MTH_ID, Constants.FP_PLAY_6MTH);
        contractsMap.put(Constants.FP_PLAY_12MTH_ID, Constants.FP_PLAY_12MTH);
//        ____________________________________________________________________________________ Fitness Playground

//        ____________________________________________________________________________________ Newtown
        contractsMap.put(Constants.FP_NEWTOWN_GYM_ID, Constants.FP_NEWTOWN_GYM);
//        contractsMap.put(Constants.FP_NEWTOWN_PLAY_ID, Constants.FP_NEWTOWN_PLAY); // Play memberships are the same price

        contractsMap.put(Constants.FP_NEWTOWN_GYM_NOCOMMIT_ID, Constants.FP_NEWTOWN_GYM_NOCOMMIT);
//        contractsMap.put(Constants.FP_NEWTOWN_PLAY_NOCOMMIT_ID, Constants.FP_NEWTOWN_PLAY_NOCOMMIT);

//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_GYM_ID, Constants.CORPORATE_FP_SURRY_HILLS_GYM);
//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_PLAY_ID, Constants.CORPORATE_FP_SURRY_HILLS_PLAY);

//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_GYM_NOCOMMIT_ID, Constants.CORPORATE_FP_SURRY_HILLS_GYM_NOCOMMIT);
//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_PLAY_NOCOMMIT_ID, Constants.CORPORATE_FP_SURRY_HILLS_PLAY_NOCOMMIT);

        contractsMap.put(Constants.FP_NEWTOWN_GYM_1MTH_ID, Constants.FP_NEWTOWN_GYM_1MTH);
        contractsMap.put(Constants.FP_NEWTOWN_GYM_3MTH_ID, Constants.FP_NEWTOWN_GYM_3MTH);
        contractsMap.put(Constants.FP_NEWTOWN_GYM_6MTH_ID, Constants.FP_NEWTOWN_GYM_6MTH);
        contractsMap.put(Constants.FP_NEWTOWN_GYM_12MTH_ID, Constants.FP_NEWTOWN_GYM_12MTH);

//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_1MTH_ID, Constants.FP_SURRY_HILLS_PLAY_1MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_2MTH_ID, Constants.FP_SURRY_HILLS_PLAY_2MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_3MTH_ID, Constants.FP_SURRY_HILLS_PLAY_3MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_6MTH_ID, Constants.FP_SURRY_HILLS_PLAY_6MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_12MTH_ID, Constants.FP_SURRY_HILLS_PLAY_12MTH);
//        ____________________________________________________________________________________ Newtown

////        ____________________________________________________________________________________ Surry Hills
//        Made redundant as of 10 Feb 2021
//        contractsMap.put(Constants.FP_SURRY_HILLS_GYM_ID, Constants.FP_SURRY_HILLS_GYM);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_ID, Constants.FP_SURRY_HILLS_PLAY);
//
//        contractsMap.put(Constants.FP_SURRY_HILLS_GYM_NOCOMMIT_ID, Constants.FP_SURRY_HILLS_GYM_NOCOMMIT);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_NOCOMMIT_ID, Constants.FP_SURRY_HILLS_PLAY_NOCOMMIT);
//
//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_GYM_ID, Constants.CORPORATE_FP_SURRY_HILLS_GYM);
//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_PLAY_ID, Constants.CORPORATE_FP_SURRY_HILLS_PLAY);
//
//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_GYM_NOCOMMIT_ID, Constants.CORPORATE_FP_SURRY_HILLS_GYM_NOCOMMIT);
//        contractsMap.put(Constants.CORPORATE_FP_SURRY_HILLS_PLAY_NOCOMMIT_ID, Constants.CORPORATE_FP_SURRY_HILLS_PLAY_NOCOMMIT);
//
//        contractsMap.put(Constants.FP_SURRY_HILLS_GYM_1MTH_ID, Constants.FP_SURRY_HILLS_GYM_1MTH);
////        contractsMap.put(Constants.FP_SURRY_HILLS_GYM_2MTH_ID, Constants.FP_SURRY_HILLS_GYM_2MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_GYM_3MTH_ID, Constants.FP_SURRY_HILLS_GYM_3MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_GYM_6MTH_ID, Constants.FP_SURRY_HILLS_GYM_6MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_GYM_12MTH_ID, Constants.FP_SURRY_HILLS_GYM_12MTH);
//
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_1MTH_ID, Constants.FP_SURRY_HILLS_PLAY_1MTH);
////        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_2MTH_ID, Constants.FP_SURRY_HILLS_PLAY_2MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_3MTH_ID, Constants.FP_SURRY_HILLS_PLAY_3MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_6MTH_ID, Constants.FP_SURRY_HILLS_PLAY_6MTH);
//        contractsMap.put(Constants.FP_SURRY_HILLS_PLAY_12MTH_ID, Constants.FP_SURRY_HILLS_PLAY_12MTH);
////        ____________________________________________________________________________________ Surry Hills

//        ____________________________________________________________________________________ Bunker
        contractsMap.put(Constants.BUNKER_GYM_ID, Constants.BUNKER_GYM);
        contractsMap.put(Constants.BUNKER_PLAY_ID, Constants.BUNKER_PLAY);

        contractsMap.put(Constants.BUNKER_GYM_NOCOMMIT_ID, Constants.BUNKER_GYM_NOCOMMIT);
        contractsMap.put(Constants.BUNKER_PLAY_NOCOMMIT_ID, Constants.BUNKER_PLAY_NOCOMMIT);

        contractsMap.put(Constants.CORPORATE_BUNKER_GYM_ID, Constants.CORPORATE_BUNKER_GYM);
        contractsMap.put(Constants.CORPORATE_BUNKER_PLAY_ID, Constants.CORPORATE_BUNKER_PLAY);

        contractsMap.put(Constants.CORPORATE_BUNKER_GYM_NOCOMMIT_ID, Constants.CORPORATE_BUNKER_GYM_NOCOMMIT);
        contractsMap.put(Constants.CORPORATE_BUNKER_PLAY_NOCOMMIT_ID, Constants.CORPORATE_BUNKER_PLAY_NOCOMMIT);

        contractsMap.put(Constants.BUNKER_GYM_1MTH_ID, Constants.BUNKER_GYM_1MTH);
//        contractsMap.put(Constants.BUNKER_GYM_2MTH_ID, Constants.BUNKER_GYM_2MTH);
        contractsMap.put(Constants.BUNKER_GYM_3MTH_ID, Constants.BUNKER_GYM_3MTH);
        contractsMap.put(Constants.BUNKER_GYM_6MTH_ID, Constants.BUNKER_GYM_6MTH);
        contractsMap.put(Constants.BUNKER_GYM_12MTH_ID, Constants.BUNKER_GYM_12MTH);

        contractsMap.put(Constants.BUNKER_PLAY_1MTH_ID, Constants.BUNKER_PLAY_1MTH);
//        contractsMap.put(Constants.BUNKER_PLAY_2MTH_ID, Constants.BUNKER_PLAY_2MTH);
        contractsMap.put(Constants.BUNKER_PLAY_3MTH_ID, Constants.BUNKER_PLAY_3MTH);
        contractsMap.put(Constants.BUNKER_PLAY_6MTH_ID, Constants.BUNKER_PLAY_6MTH);
        contractsMap.put(Constants.BUNKER_PLAY_12MTH_ID, Constants.BUNKER_PLAY_12MTH);
//        ____________________________________________________________________________________ Bunker

        contractsMap.put(Constants.FP_PT_30_1FN_ID, Constants.FP_PT_30_1FN);
        contractsMap.put(Constants.FP_PT_30_1WK_ID, Constants.FP_PT_30_1WK);
        contractsMap.put(Constants.FP_PT_30_2WK_ID, Constants.FP_PT_30_2WK);
        contractsMap.put(Constants.FP_PT_30_3WK_ID, Constants.FP_PT_30_3WK);
        contractsMap.put(Constants.FP_PT_30_4WK_ID, Constants.FP_PT_30_4WK);

        contractsMap.put(Constants.FP_PT_40_1FN_ID, Constants.FP_PT_40_1FN);
        contractsMap.put(Constants.FP_PT_40_1WK_ID, Constants.FP_PT_40_1WK);
        contractsMap.put(Constants.FP_PT_40_2WK_ID, Constants.FP_PT_40_2WK);
        contractsMap.put(Constants.FP_PT_40_3WK_ID, Constants.FP_PT_40_3WK);
        contractsMap.put(Constants.FP_PT_40_4WK_ID, Constants.FP_PT_40_4WK);

        contractsMap.put(Constants.FP_PT_60_1FN_ID, Constants.FP_PT_60_1FN);
        contractsMap.put(Constants.FP_PT_60_1WK_ID, Constants.FP_PT_60_1WK);
        contractsMap.put(Constants.FP_PT_60_2WK_ID, Constants.FP_PT_60_2WK);
        contractsMap.put(Constants.FP_PT_60_3WK_ID, Constants.FP_PT_60_3WK);
        contractsMap.put(Constants.FP_PT_60_4WK_ID, Constants.FP_PT_60_4WK);

        contractsMap.put(Constants.BUNKER_PT_30_1FN_ID, Constants.BUNKER_PT_30_1FN);
        contractsMap.put(Constants.BUNKER_PT_30_1WK_ID, Constants.BUNKER_PT_30_1WK);
        contractsMap.put(Constants.BUNKER_PT_30_2WK_ID, Constants.BUNKER_PT_30_2WK);
        contractsMap.put(Constants.BUNKER_PT_30_3WK_ID, Constants.BUNKER_PT_30_3WK);
        contractsMap.put(Constants.BUNKER_PT_30_4WK_ID, Constants.BUNKER_PT_30_4WK);

        contractsMap.put(Constants.BUNKER_PT_40_1FN_ID, Constants.BUNKER_PT_40_1FN);
        contractsMap.put(Constants.BUNKER_PT_40_1WK_ID, Constants.BUNKER_PT_40_1WK);
        contractsMap.put(Constants.BUNKER_PT_40_2WK_ID, Constants.BUNKER_PT_40_2WK);
        contractsMap.put(Constants.BUNKER_PT_40_3WK_ID, Constants.BUNKER_PT_40_3WK);
        contractsMap.put(Constants.BUNKER_PT_40_4WK_ID, Constants.BUNKER_PT_40_4WK);

        contractsMap.put(Constants.BUNKER_PT_60_1FN_ID, Constants.BUNKER_PT_60_1FN);
        contractsMap.put(Constants.BUNKER_PT_60_1WK_ID, Constants.BUNKER_PT_60_1WK);
        contractsMap.put(Constants.BUNKER_PT_60_2WK_ID, Constants.BUNKER_PT_60_2WK);
        contractsMap.put(Constants.BUNKER_PT_60_3WK_ID, Constants.BUNKER_PT_60_3WK);
        contractsMap.put(Constants.BUNKER_PT_60_4WK_ID, Constants.BUNKER_PT_60_4WK);

        contractsMap.put(Constants.FP_LIFESTYLE_PT_40_1FN_ID, Constants.FP_LIFESTYLE_PT_40_1FN);
        contractsMap.put(Constants.FP_LIFESTYLE_PT_40_1WK_ID, Constants.FP_LIFESTYLE_PT_40_1WK);
        contractsMap.put(Constants.FP_LIFESTYLE_PT_40_2WK_ID, Constants.FP_LIFESTYLE_PT_40_2WK);
        contractsMap.put(Constants.FP_LIFESTYLE_PT_40_3WK_ID, Constants.FP_LIFESTYLE_PT_40_3WK);
        contractsMap.put(Constants.FP_LIFESTYLE_PT_40_4WK_ID, Constants.FP_LIFESTYLE_PT_40_4WK);

        contractsMap.put(Constants.BUNKER_LIFESTYLE_PT_40_1FN_ID, Constants.BUNKER_LIFESTYLE_PT_40_1FN);
        contractsMap.put(Constants.BUNKER_LIFESTYLE_PT_40_1WK_ID, Constants.BUNKER_LIFESTYLE_PT_40_1WK);
        contractsMap.put(Constants.BUNKER_LIFESTYLE_PT_40_2WK_ID, Constants.BUNKER_LIFESTYLE_PT_40_2WK);
        contractsMap.put(Constants.BUNKER_LIFESTYLE_PT_40_3WK_ID, Constants.BUNKER_LIFESTYLE_PT_40_3WK);
        contractsMap.put(Constants.BUNKER_LIFESTYLE_PT_40_4WK_ID, Constants.BUNKER_LIFESTYLE_PT_40_4WK);

        contractsMap.put(Constants.CRECHE_1_CHILD_ID, Constants.CRECHE_1_CHILD);
        contractsMap.put(Constants.CRECHE_N_CHILD_ID, Constants.CRECHE_N_CHILD);

        contractsMap.put(Constants.FP_EXTERNAL_PT_40_1FN_ID, Constants.FP_EXTERNAL_PT_40_1FN);
        contractsMap.put(Constants.FP_EXTERNAL_PT_40_1WK_ID, Constants.FP_EXTERNAL_PT_40_1WK);
        contractsMap.put(Constants.FP_EXTERNAL_PT_40_2WK_ID, Constants.FP_EXTERNAL_PT_40_2WK);
        contractsMap.put(Constants.FP_EXTERNAL_PT_40_3WK_ID, Constants.FP_EXTERNAL_PT_40_3WK);
        contractsMap.put(Constants.FP_EXTERNAL_PT_40_4WK_ID, Constants.FP_EXTERNAL_PT_40_4WK);

        contractsMap.put(Constants.FP_EXTERNAL_PT_60_1FN_ID, Constants.FP_EXTERNAL_PT_60_1FN);
        contractsMap.put(Constants.FP_EXTERNAL_PT_60_1WK_ID, Constants.FP_EXTERNAL_PT_60_1WK);
        contractsMap.put(Constants.FP_EXTERNAL_PT_60_2WK_ID, Constants.FP_EXTERNAL_PT_60_2WK);
        contractsMap.put(Constants.FP_EXTERNAL_PT_60_3WK_ID, Constants.FP_EXTERNAL_PT_60_3WK);
        contractsMap.put(Constants.FP_EXTERNAL_PT_60_4WK_ID, Constants.FP_EXTERNAL_PT_60_4WK);

        contractsMap.put(Constants.VIRTUAL_PLAYGROUND_ID, Constants.VIRTUAL_PLAYGROUND);

        return contractsMap;
    }

    public static HashMap<Integer, String> buildContractsMap_Darwin() {

        HashMap<Integer, String> contractsMap = new HashMap<>();

        contractsMap.put(Constants.GW_ACCESS_KEY_ID, Constants.ACCESS_KEY);

        contractsMap.put(Constants.GW_FP_GYM_ID, Constants.FP_GYM);
        contractsMap.put(Constants.GW_FP_PLAY_ID, Constants.FP_PLAY);

        contractsMap.put(Constants.GW_FP_GYM_NOCOMMIT_ID, Constants.FP_GYM_NOCOMMIT);
        contractsMap.put(Constants.GW_FP_PLAY_NOCOMMIT_ID, Constants.FP_PLAY_NOCOMMIT);

//        contractsMap.put(Constants.GW_CORPORATE_FP_GYM_ID, Constants.CORPORATE_FP_GYM);
//        contractsMap.put(Constants.GW_CORPORATE_FP_PLAY_ID, Constants.CORPORATE_FP_PLAY);

        contractsMap.put(Constants.GW_CORPORATE_FP_GYM_NOCOMMIT_ID, Constants.CORPORATE_FP_GYM_NOCOMMIT);
//        contractsMap.put(Constants.GW_CORPORATE_FP_PLAY_NOCOMMIT_ID, Constants.CORPORATE_FP_PLAY_NOCOMMIT);

//        contractsMap.put(Constants.GW_PGT_5PACK_ID, Constants.PGT_5PACK);
//        contractsMap.put(Constants.GW_PGT_10PACK_ID, Constants.PGT_10PACK);
//
        contractsMap.put(Constants.GW_STARTERPACK_PTPACK_ID, Constants.STARTERPACK_PTPACK);
        contractsMap.put(Constants.GW_STARTERPACK_TRANSFORMER_ID, Constants.STARTERPACK_TRANSFORMER);

        contractsMap.put(Constants.GW_FP_GYM_1MTH_ID, Constants.FP_GYM_1MTH);
        contractsMap.put(Constants.GW_FP_GYM_2MTH_ID, Constants.FP_GYM_2MTH);
        contractsMap.put(Constants.GW_FP_GYM_3MTH_ID, Constants.FP_GYM_3MTH);
        contractsMap.put(Constants.GW_FP_GYM_6MTH_ID, Constants.FP_GYM_6MTH);
        contractsMap.put(Constants.GW_FP_GYM_12MTH_ID, Constants.FP_GYM_12MTH);

        contractsMap.put(Constants.GW_FP_PLAY_1MTH_ID, Constants.FP_PLAY_1MTH);
//        contractsMap.put(Constants.GW_FP_PLAY_2MTH_ID, Constants.FP_PLAY_2MTH);
        contractsMap.put(Constants.GW_FP_PLAY_3MTH_ID, Constants.FP_PLAY_3MTH);
        contractsMap.put(Constants.GW_FP_PLAY_6MTH_ID, Constants.FP_PLAY_6MTH);
        contractsMap.put(Constants.GW_FP_PLAY_12MTH_ID, Constants.FP_PLAY_12MTH);

        contractsMap.put(Constants.GW_FP_PT_30_1FN_ID, Constants.FP_PT_30_1FN);
        contractsMap.put(Constants.GW_FP_PT_30_1WK_ID, Constants.FP_PT_30_1WK);
        contractsMap.put(Constants.GW_FP_PT_30_2WK_ID, Constants.FP_PT_30_2WK);
        contractsMap.put(Constants.GW_FP_PT_30_3WK_ID, Constants.FP_PT_30_3WK);
        contractsMap.put(Constants.GW_FP_PT_30_4WK_ID, Constants.FP_PT_30_4WK);

        contractsMap.put(Constants.GW_FP_PT_40_1FN_ID, Constants.FP_PT_40_1FN);
        contractsMap.put(Constants.GW_FP_PT_40_1WK_ID, Constants.FP_PT_40_1WK);
        contractsMap.put(Constants.GW_FP_PT_40_2WK_ID, Constants.FP_PT_40_2WK);
        contractsMap.put(Constants.GW_FP_PT_40_3WK_ID, Constants.FP_PT_40_3WK);
        contractsMap.put(Constants.GW_FP_PT_40_4WK_ID, Constants.FP_PT_40_4WK);

        contractsMap.put(Constants.GW_FP_PT_60_1FN_ID, Constants.FP_PT_60_1FN);
        contractsMap.put(Constants.GW_FP_PT_60_1WK_ID, Constants.FP_PT_60_1WK);
        contractsMap.put(Constants.GW_FP_PT_60_2WK_ID, Constants.FP_PT_60_2WK);
        contractsMap.put(Constants.GW_FP_PT_60_3WK_ID, Constants.FP_PT_60_3WK);
        contractsMap.put(Constants.GW_FP_PT_60_4WK_ID, Constants.FP_PT_60_4WK);

        contractsMap.put(Constants.GW_FP_LIFESTYLE_PT_40_1FN_ID, Constants.FP_LIFESTYLE_PT_40_1FN);
        contractsMap.put(Constants.GW_FP_LIFESTYLE_PT_40_1WK_ID, Constants.FP_LIFESTYLE_PT_40_1WK);
        contractsMap.put(Constants.GW_FP_LIFESTYLE_PT_40_2WK_ID, Constants.FP_LIFESTYLE_PT_40_2WK);
        contractsMap.put(Constants.GW_FP_LIFESTYLE_PT_40_3WK_ID, Constants.FP_LIFESTYLE_PT_40_3WK);
        contractsMap.put(Constants.GW_FP_LIFESTYLE_PT_40_4WK_ID, Constants.FP_LIFESTYLE_PT_40_4WK);

        return contractsMap;
    }
}

