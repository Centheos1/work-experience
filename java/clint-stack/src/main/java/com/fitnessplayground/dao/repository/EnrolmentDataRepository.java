package com.fitnessplayground.dao.repository;

import com.fitnessplayground.dao.domain.EnrolmentData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;


public interface EnrolmentDataRepository extends CrudRepository<EnrolmentData, Long> {

    String FIND_100_BY_LOCATION = "SELECT * FROM EnrolmentData WHERE locationId = :location AND accessKeyNumber != '100042264' ORDER BY id DESC LIMIT 100";

    String FIND_100 = "SELECT * FROM EnrolmentData WHERE accessKeyNumber != '100042264' ORDER BY id DESC LIMIT 100";

    String FIND_ALL_WITH_LOCATION = "SELECT * FROM EnrolmentData WHERE locationId = :location";

    String FIND_SAVED = "SELECT * FROM EnrolmentData WHERE status = 'SAVED' LIMIT 10";

    String FIND_UNPROCESSED = "SELECT * FROM EnrolmentData WHERE status = 'PROCESSING' LIMIT 20";

    String FIND_SUCCESS = "SELECT * FROM EnrolmentData WHERE status = 'SUCCESS'";

    String FIND_EMAIL_CAMPAIGN_PENDING = "SELECT * FROM EnrolmentData WHERE communicationsStatus = 'EMAIL_CAMPAIGN_PENDING' LIMIT 2";

    String FIND_MC_NOTES_PENDING = "SELECT * FROM EnrolmentData WHERE communicationsStatus = 'MC_NOTES_PENDING' LIMIT 2";

    String FIND_ENROLMENTS = "SELECT * FROM EnrolmentData WHERE firstName = :firstName AND lastName = :lastName AND email = :email";

    String FIND_ENROLMENTS_BY_EMAIL = "SELECT * FROM EnrolmentData WHERE email = :email";

    String FIND_BY_FORMSTACK_IDS = "SELECT * FROM EnrolmentData WHERE fs_formId = :fsFormId AND fs_uniqueId = :fsUniqueId ORDER BY id DESC LIMIT 1";

    String FIND_BY_FORM_ID_AND_EMAIL = "SELECT * FROM EnrolmentData WHERE fs_formId = :fs_formId AND email = :email";

    String SEARCH_BY_PERS_DETAILS = "SELECT * FROM EnrolmentData WHERE (firstName = :firstName AND lastName = :lastName AND email = :email) OR (firstName = :firstName AND lastName = :lastName AND phone = :phone) OR (phone = :phone AND email = :email)";

    String GET_ENROLMENTS_FROM_DATE = "SELECT * FROM EnrolmentData WHERE createDate >= :fromDate";

    String UI_SEARCH_ENROLMENTS = "SELECT * FROM EnrolmentData WHERE firstName LIKE %:search% OR lastName LIKE %:search% or email LIKE %:search% OR accessKeyNumber LIKE %:search% ORDER BY id DESC LIMIT 20";

    String UI_SEARCH_ENROLMENTS_LOCATION = "SELECT * FROM EnrolmentData WHERE locationId = :location AND (firstName LIKE %:search% OR lastName LIKE %:search% or email LIKE %:search% OR accessKeyNumber LIKE %:search%) ORDER BY id DESC LIMIT 20";


//    if (e.getFirstName().contains(search)
//                    || e.getLastName().contains(search)
//                    || e.getEmail().contains(search)
//                    || e.getPhone().contains(search)
//                    || e.getAccessKeyNumber().contains(search)) {
//        returnData.add(e);
//    }

    @Query(value = FIND_100_BY_LOCATION, nativeQuery = true)
    List<EnrolmentData> find100(@Param("location") String location);

    @Query(value = FIND_100, nativeQuery = true)
    List<EnrolmentData> find100();

    @Query(value = FIND_ALL_WITH_LOCATION, nativeQuery = true)
    List<EnrolmentData> findAllByLocation(@Param("location") String location);

    @Query(value = FIND_UNPROCESSED, nativeQuery = true)
    List<EnrolmentData> getUnprocessed();

    @Query(value = FIND_SAVED, nativeQuery = true)
    List<EnrolmentData> getSaved();

    @Query(value = FIND_SUCCESS, nativeQuery = true)
    List<EnrolmentData> getSuccessEnrolments();

    @Query(value = FIND_EMAIL_CAMPAIGN_PENDING, nativeQuery = true)
    List<EnrolmentData> getEmailCampaingPendingEnrolments();

    @Query(value = FIND_MC_NOTES_PENDING, nativeQuery = true)
    List<EnrolmentData> getInternalCommsMCNotesEnrolments();

    @Query(value = FIND_ENROLMENTS, nativeQuery = true)
    List<EnrolmentData> findEnrolments(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email);

    @Query(value = FIND_BY_FORMSTACK_IDS, nativeQuery = true)
    EnrolmentData findByFormstackIds(@Param("fsFormId") String fsFormId, @Param("fsUniqueId") String fsUniqueId);

    @Query(value = FIND_BY_FORM_ID_AND_EMAIL, nativeQuery = true)
    List<EnrolmentData> findByFormIdAndEmail(@Param("fs_formId") String fs_formId, @Param("email") String email);

    @Query(value = FIND_ENROLMENTS_BY_EMAIL, nativeQuery = true)
    List<EnrolmentData> findEnrolmentsByEmail(@Param("email") String email);

    @Query(value = SEARCH_BY_PERS_DETAILS, nativeQuery = true)
    List<EnrolmentData> searchByPersDetails(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("email") String email, @Param("phone") String phone);

    @Query(value = GET_ENROLMENTS_FROM_DATE, nativeQuery = true)
    ArrayList<EnrolmentData> getEnrolmentsFromDate(@Param("fromDate") String fromDate);

    @Query(value = UI_SEARCH_ENROLMENTS, nativeQuery = true)
    ArrayList<EnrolmentData> uiSearchEnrolments(@Param("search") String search);

    @Query(value = UI_SEARCH_ENROLMENTS_LOCATION, nativeQuery = true)
    ArrayList<EnrolmentData> uiSearchEnrolmentsLocation(@Param("search") String search, @Param("location") String location);

}

