package com.fitnessplayground.service;

import com.fitnessplayground.dao.domain.EnrolmentData;
import com.fitnessplayground.dao.domain.MemberBankDetail;
import com.fitnessplayground.dao.domain.MemberCreditCard;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface EncryptionService {
    String encrypt(String data);
    String decrypt(String encryptedText);
    String sign(PrivateKey privateKey);
    boolean verify(PublicKey publicKey, String checkPackage);
    void setKeys();
    EnrolmentData encryptAndClean(EnrolmentData enrolmentData);
    EnrolmentData encryptDetails(EnrolmentData enrolmentData);
    EnrolmentData decryptAndClean(EnrolmentData enrolmentData);
    EnrolmentData decryptDetails(EnrolmentData enrolmentData);

    MemberCreditCard encryptCMemberCreditCard(MemberCreditCard memberCreditCard);
    MemberCreditCard decryptCMemberCreditCard(MemberCreditCard memberCreditCard);
    MemberCreditCard decryptAndCleanMemberCreditCard(MemberCreditCard memberCreditCard);
    MemberBankDetail encryptMemberBankDetail(MemberBankDetail memberBankDetail);
    MemberBankDetail decryptMemberBankDetail(MemberBankDetail memberBankDetail);
    MemberBankDetail decryptAndCleanMemberBankDetail(MemberBankDetail memberBankDetail);

    MemberCreditCard cleanAndEncryptCreditCard(MemberCreditCard creditCard);
    MemberBankDetail cleanAndEncryptBankDetails(MemberBankDetail bankDetail);
}
