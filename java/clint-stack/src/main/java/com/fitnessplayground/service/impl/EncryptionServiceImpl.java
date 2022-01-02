package com.fitnessplayground.service.impl;

import com.fitnessplayground.dao.SecurityDao;
import com.fitnessplayground.dao.domain.*;
import com.fitnessplayground.service.EncryptionService;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

//  source: https://github.com/1MansiS/java_crypto/blob/master/cipher/SecuredRSAUsage.java
//  source: https://www.novixys.com/blog/how-to-generate-rsa-keys-java/
@Service
public class EncryptionServiceImpl implements EncryptionService {

    private static final Logger logger = LoggerFactory.getLogger(EncryptionServiceImpl.class);

    private static KeyPairGenerator kg;
    private static KeyPair kp;
    private static Key pub;
    private static Key pvt;

    private static final int RSA_KEY_LENGTH = 4096;
//    private static final int RSA_KEY_LENGTH = 2048;
    private static final String ALGORITHM_NAME = "RSA" ;
    private static final String PADDING_SCHEME = "OAEPWITHSHA-512ANDMGF1PADDING" ;
    private static final String MODE_OF_OPERATION = "ECB" ; // This essentially means none behind the scene
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private SecurityDao securityDao;

    @Value("${encryption.key}")
    private String key;

    @Override
    public String encrypt(String data) {

        checkKeys();
        String encryptedText = null;
        try {
            encryptedText = rsaEncrypt(data, getPub());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return encryptedText;
        }
    }

    @Override
    public String decrypt(String encryptedText) {

        checkKeys();
        String data = null;
        try {
            data = rsaDecrypt(Base64.getDecoder().decode(encryptedText), getPvt());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return data;
        }
    }

    @Override
    public String sign(PrivateKey privateKey) {
        String signature = null;
        try {
            Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            sign.initSign(privateKey);
            signature = sign(privateKey);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } finally {
            return signature;
        }
    }

    @Override
    public boolean verify(PublicKey publicKey, String checkPackage) {
        boolean isVerified = false;
        byte[] checkByte = Base64.getDecoder().decode(checkPackage);
        try {
            Signature sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            sign.initVerify(publicKey);
            isVerified = sign.verify(checkByte);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } finally {
            return isVerified;
        }
    }

    @Override
    public void setKeys() {
        Iterable<Encrypt> encrypts = getSecurityDao().getEncryptionKeys();
        Encrypt encrypt = new Encrypt();
        for (Encrypt e : encrypts) {
            if (e.getCheckKey().equals(key)) {
                encrypt = e;
            }
        }
        try {
            setPub(decodePublicKey(encrypt.getPub()));
            setPvt(decodePrivateKey(encrypt.getPvt()));
        } catch (NullPointerException e) {
            logger.error("Error setting keys. {}",e.getMessage());
        }
    }

    @Override
    public EnrolmentData decryptAndClean(EnrolmentData enrolmentData) {
        String decryptedNumber;

        if (enrolmentData.getMemberCreditCard() != null) {
            decryptedNumber = decrypt(enrolmentData.getMemberCreditCard().getNumber());
            String decryptedCvc = decrypt(enrolmentData.getMemberCreditCard().getVerificationCode());
            enrolmentData.getMemberCreditCard().setNumber(decryptedNumber);
            enrolmentData.getMemberCreditCard().setVerificationCode(decryptedCvc);

            enrolmentData.setMemberCreditCard(Helpers.cleanPaymentDetails(enrolmentData.getMemberCreditCard()));
        }

        if (enrolmentData.getMemberBankDetail() != null) {
            decryptedNumber = decrypt(enrolmentData.getMemberBankDetail().getAccountNumber());
            decryptedNumber = Helpers.cleanCreditCard(decryptedNumber);
            enrolmentData.getMemberBankDetail().setAccountNumber(decryptedNumber);
        }

        return enrolmentData;
    }

    @Override
    public EnrolmentData encryptAndClean(EnrolmentData enrolmentData) {

        String tmp;

        if (enrolmentData.getMemberCreditCard() != null) {
            tmp = Helpers.cleanCreditCard(enrolmentData.getMemberCreditCard().getNumber());
            enrolmentData.getMemberCreditCard().setNumber(tmp);
            enrolmentData.getMemberCreditCard().setVerificationCode("***");
        }

        if (enrolmentData.getMemberBankDetail() != null) {
            tmp = Helpers.cleanCreditCard(enrolmentData.getMemberBankDetail().getAccountNumber());
            enrolmentData.getMemberBankDetail().setAccountNumber(tmp);
        }

        enrolmentData = encryptDetails(enrolmentData);
        return enrolmentData;
    }


    @Override
    public MemberCreditCard cleanAndEncryptCreditCard(MemberCreditCard creditCard) {
        String tmp;

        tmp = Helpers.cleanCreditCard(creditCard.getNumber());
        creditCard.setNumber(tmp);
        creditCard.setVerificationCode("***");

        creditCard = encryptCMemberCreditCard(creditCard);

        return creditCard;
    }

    @Override
    public MemberBankDetail cleanAndEncryptBankDetails(MemberBankDetail bankDetail) {
        String tmp;
        tmp = Helpers.cleanCreditCard(bankDetail.getAccountNumber());
        bankDetail.setAccountNumber(tmp);

        bankDetail = encryptMemberBankDetail(bankDetail);
        return bankDetail;
    }

    @Override
    public EnrolmentData decryptDetails(EnrolmentData enrolmentData) {

        try {
            if (enrolmentData.getMemberCreditCard() != null) {
                enrolmentData.getMemberCreditCard().setNumber(decrypt(enrolmentData.getMemberCreditCard().getNumber()));
                enrolmentData.getMemberCreditCard().setVerificationCode(decrypt(enrolmentData.getMemberCreditCard().getVerificationCode()));
            }
            if (enrolmentData.getMemberBankDetail() != null) {
                enrolmentData.getMemberBankDetail().setAccountNumber(decrypt(enrolmentData.getMemberBankDetail().getAccountNumber()));
            }
        } catch (Exception e) {
            logger.error("Error decrypting Data {}",e.getMessage());
        }

        return enrolmentData;

    }

    @Override
    public EnrolmentData encryptDetails(EnrolmentData enrolmentData) {

        if (enrolmentData.getMemberCreditCard() != null) {
            try {
                String encryptedNumber = encrypt(enrolmentData.getMemberCreditCard().getNumber());
                String encryptedCvc = encrypt(enrolmentData.getMemberCreditCard().getVerificationCode());

                if (encryptedNumber != null) {
                    enrolmentData.getMemberCreditCard().setNumber(encryptedNumber);
                }
                if (encryptedCvc != null) {
                    enrolmentData.getMemberCreditCard().setVerificationCode(encryptedCvc);
                }
            } catch (Exception e) {
                logger.error("Error Encrypting Data {}", e.getMessage());
            }
        }

        if (enrolmentData.getMemberBankDetail() != null) {
            try {
                String encryptedNumber = encrypt(enrolmentData.getMemberBankDetail().getAccountNumber());
                if (encryptedNumber != null) {
                    enrolmentData.getMemberBankDetail().setAccountNumber(encryptedNumber);
                }
            }  catch (Exception e) {
                logger.error("Error Encrypting Data {}", e.getMessage());
            }
        }

        return enrolmentData;
    }


    @Override
    public MemberCreditCard encryptCMemberCreditCard(MemberCreditCard memberCreditCard) {

        if (memberCreditCard != null) {
            try {
                String encryptedNumber = encrypt(memberCreditCard.getNumber());
                String encryptedCvc = encrypt(memberCreditCard.getVerificationCode());

                if (encryptedNumber != null) {
                    memberCreditCard.setNumber(encryptedNumber);
                }
                if (encryptedCvc != null) {
                    memberCreditCard.setVerificationCode(encryptedCvc);
                }
            } catch (Exception e) {
                logger.error("Error Encrypting Member Credit Card Data {}", e.getMessage());
            }
        }
        return memberCreditCard;
    }

    @Override
    public MemberCreditCard decryptCMemberCreditCard(MemberCreditCard memberCreditCard) {
        try {
            if (memberCreditCard != null) {
                if (memberCreditCard.getNumber() != null) {
                    memberCreditCard.setNumber(decrypt(memberCreditCard.getNumber()));
                }
                if (memberCreditCard.getVerificationCode() != null) {
                    memberCreditCard.setVerificationCode(decrypt(memberCreditCard.getVerificationCode()));
                }
            }
        } catch (Exception e) {
            logger.error("Error decrypting Credit Card Data {}",e.getMessage());
        }
        return memberCreditCard;
    }

    @Override
    public MemberCreditCard decryptAndCleanMemberCreditCard(MemberCreditCard memberCreditCard) {

        try {
            memberCreditCard = decryptCMemberCreditCard(memberCreditCard);

            if (memberCreditCard != null) {
                String tmp;
                tmp = Helpers.cleanCreditCard(memberCreditCard.getNumber());
                memberCreditCard.setNumber(tmp);
                memberCreditCard.setVerificationCode("***");
            }

        } catch (Exception e) {
            logger.error("Error decrypting and Cleaning Credit Card Data: {}",e.getMessage());
        }

        return memberCreditCard;
    }

    @Override
    public MemberBankDetail encryptMemberBankDetail(MemberBankDetail memberBankDetail) {

        if (memberBankDetail != null) {
            try {
                String encryptedNumber = encrypt(memberBankDetail.getAccountNumber());
                if (encryptedNumber != null) {
                    memberBankDetail.setAccountNumber(encryptedNumber);
                }
            }  catch (Exception e) {
                logger.error("Error Encrypting Member Bank Detail Data {}", e.getMessage());
            }
        }
        return memberBankDetail;
    }

    @Override
    public MemberBankDetail decryptMemberBankDetail(MemberBankDetail memberBankDetail) {
        try {
            if (memberBankDetail != null) {
                memberBankDetail.setAccountNumber(decrypt(memberBankDetail.getAccountNumber()));
            }
        } catch (Exception e) {
            logger.error("Error decrypting Member Bank Details Data {}",e.getMessage());
        }
        return memberBankDetail;
    }

    @Override
    public MemberBankDetail decryptAndCleanMemberBankDetail(MemberBankDetail memberBankDetail) {

        try {

            memberBankDetail = decryptMemberBankDetail(memberBankDetail);

            if (memberBankDetail != null) {
                String tmp;
                tmp = Helpers.cleanCreditCard(memberBankDetail.getAccountNumber());
                memberBankDetail.setAccountNumber(tmp);
            }

        } catch (Exception e) {
            logger.error("Error decrypting and Cleaning Member Bank Details Data {}",e.getMessage());
        }

        return memberBankDetail;
    }


    private String rsaEncrypt(String message, Key publicKey) throws Exception {

        Cipher c = Cipher.getInstance(ALGORITHM_NAME + "/" + MODE_OF_OPERATION + "/" + PADDING_SCHEME) ;
        c.init(Cipher.ENCRYPT_MODE, publicKey) ;
        byte[] cipherTextArray = c.doFinal(message.getBytes()) ;
        return Base64.getEncoder().encodeToString(cipherTextArray) ;
    }


    private String rsaDecrypt(byte[] encryptedMessage, Key privateKey) throws Exception {
        Cipher c = Cipher.getInstance(ALGORITHM_NAME + "/" + MODE_OF_OPERATION + "/" + PADDING_SCHEME) ;
        c.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] plainText = c.doFinal(encryptedMessage);

        return new String(plainText) ;
    }

    private boolean generator() {
        try {
            kg = KeyPairGenerator.getInstance(ALGORITHM_NAME);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Error generating key pair {}");
            e.printStackTrace();
        }
        kg.initialize(RSA_KEY_LENGTH);
        kp = kg.generateKeyPair();
        setPub(kp.getPublic());
        setPvt(kp.getPrivate());
//        pub = kp.getPublic();
//        pvt = kp.getPrivate();

        String pvtKey = Base64.getEncoder().encodeToString(pvt.getEncoded());
        String pubKey = Base64.getEncoder().encodeToString(pub.getEncoded());
        String key = Long.toString(Instant.now().getEpochSecond());
//        logger.info("PrivateKey = {} | PublicKey = {}",pvtKey, pubKey);
//        logger.info("PublicKey Format = {} | PrivateKey Format = {}",pvt, pub);
        Encrypt encrypt = new Encrypt(key,pubKey,pvtKey);
        return getSecurityDao().save(encrypt);
    }

    private PrivateKey decodePrivateKey(String pvtKey) {
        byte[] pvtByte = Base64.getDecoder().decode(pvtKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pvtByte);
        KeyFactory keyFactory;
        PrivateKey privateKey = null;
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
            privateKey = keyFactory.generatePrivate(keySpec);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            return privateKey;
        }
    }

    private PublicKey decodePublicKey(String pubKey) {
        byte[] pubByte = Base64.getDecoder().decode(pubKey);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pubByte);
        KeyFactory keyFactory;
        PublicKey publicKey = null;
        try {
            keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);
            publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } finally {
            return publicKey;
        }
    }

    private void checkKeys() {
        if (getPub() == null || getPvt() == null) {
            setKeys();
        }
    }

    private static Key getPub() {
        return pub;
    }

    private static void setPub(Key pub) {
        EncryptionServiceImpl.pub = pub;
    }

    private static Key getPvt() {
        return pvt;
    }

    private static void setPvt(Key pvt) {
        EncryptionServiceImpl.pvt = pvt;
    }

    public SecurityDao getSecurityDao() {
        return securityDao;
    }

    @Autowired
    public void setSecurityDao(SecurityDao securityDao) {
        this.securityDao = securityDao;
    }
}
