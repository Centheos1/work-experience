package com.fitnessplayground.dao;

import com.fitnessplayground.dao.domain.AcContact;
import com.fitnessplayground.dao.domain.AcCustomField;
import com.fitnessplayground.dao.domain.AcEmailTag;

import java.util.List;

public interface ActiveCampaignDao {

    AcEmailTag saveTag(AcEmailTag tag);
    Iterable<AcEmailTag> getAllTags();
    Iterable<AcCustomField> getAllCustomFields();
    List<AcContact> findAcContactByAcContactId(String acContactId);
    AcContact saveAcContact(AcContact acContact);
    Iterable<AcContact> findAllAcContacts();
    List<AcContact> findAcContactByEmail(String email);
    AcEmailTag getTagByTagId(String tagId);
    AcCustomField findByCustomFieldById(String fieldId);
    AcCustomField saveAcCustomField(AcCustomField acCustomField);

}
