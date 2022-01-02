package com.fitnessplayground.dao.impl;

import com.fitnessplayground.dao.ActiveCampaignDao;
import com.fitnessplayground.dao.domain.AcContact;
import com.fitnessplayground.dao.domain.AcCustomField;
import com.fitnessplayground.dao.domain.AcEmailTag;
import com.fitnessplayground.dao.repository.AcContactRepository;
import com.fitnessplayground.dao.repository.AcCustomFieldRepository;
import com.fitnessplayground.dao.repository.AcEmailTagRepository;
import com.fitnessplayground.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActiveCampaignDaoImpl implements ActiveCampaignDao {

    private static final Logger logger = LoggerFactory.getLogger(ActiveCampaignDaoImpl.class);

    @Autowired
    private AcEmailTagRepository acEmailTagRepository;

    @Autowired
    private AcContactRepository acContactRepository;

    @Autowired
    private AcCustomFieldRepository acCustomFieldRepository;

    @Override
    public AcEmailTag saveTag(AcEmailTag tag) {

        try {
            acEmailTagRepository.save(tag);
            logger.info("Saved AC Tag {}",tag.getTag());
        } catch (Exception e) {
            String errorMsg = "Exception saving Client: "+tag.getTagId();
            logger.error("Exception saving Ac Tag: [{}] Error: [{}]", tag.getTag(), e.getMessage());
            throw new DatabaseException(errorMsg, e);
        }
        return tag;
    }

    @Override
    public Iterable<AcEmailTag> getAllTags() {
        return acEmailTagRepository.findAll();
    }

    @Override
    public Iterable<AcCustomField> getAllCustomFields() {
        return acCustomFieldRepository.findAll();
    }

    @Override
    public List<AcContact> findAcContactByAcContactId(String acContactId) {
        return acContactRepository.findAcContactByAcContactId(acContactId);
    }

    @Override
    public AcContact saveAcContact(AcContact acContact) {

        try {
            acContactRepository.save(acContact);
            logger.info("Saved AC Contact {}",acContact.toString());
        } catch (Exception e) {
            String errorMsg = "Exception saving Ac Contact: "+acContact.getEmail();
            logger.error("Exception saving Ac Contact: [{}] Error: [{}]", acContact.getEmail(), e.getMessage());
            throw new DatabaseException(errorMsg, e);
        }
        return acContact;
    }

    @Override
    public AcCustomField saveAcCustomField(AcCustomField acCustomField) {
        try {
            acCustomFieldRepository.save(acCustomField);
            logger.info("Saved AC Field {}",acCustomField.getTitle());
        } catch (Exception e) {
            String errorMsg = "Exception saving AC Field: "+acCustomField.getTitle();
            logger.error("Error saving AC Field {}", acCustomField.getTitle());
            throw new DatabaseException(errorMsg, e);
        }
        return acCustomField;
    }

    @Override
    public Iterable<AcContact> findAllAcContacts() {
        return acContactRepository.findAll();
    }

    @Override
    public List<AcContact> findAcContactByEmail(String email) {
        return acContactRepository.findByEmail(email);
    }

    @Override
    public AcEmailTag getTagByTagId(String tagId) {
        return acEmailTagRepository.findByTagId(tagId);
    }

    @Override
    public AcCustomField findByCustomFieldById(String fieldId) {
        return acCustomFieldRepository.findByCustomFieldById(fieldId);
    }


}
