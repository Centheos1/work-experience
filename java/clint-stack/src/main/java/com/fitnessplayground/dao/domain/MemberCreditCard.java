package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.CreditCardSubmission;
import com.fitnessplayground.dao.domain.temp.EnrolmentDataSubmission;
import com.fitnessplayground.dao.domain.temp.EnrolmentFormSubmission;
import com.fitnessplayground.dao.domain.temp.PaymentDetails;
import com.fitnessplayground.service.EncryptionService;
import com.fitnessplayground.util.FitnessPlaygroundUtil;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

/**
 * Created by micheal on 25/02/2017.
 */
@Entity
public class MemberCreditCard {

    private static final Logger logger = LoggerFactory.getLogger(MemberCreditCard.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String number;
    private String holder;
    private String city;
    private String address;
    private String state;
    private String postcode;
    private String expMonth;
    private String expYear;
    private String type;
    private String verificationCode;

    public MemberCreditCard() {
    }

    public MemberCreditCard(String number, String holder, String city, String address, String state, String postcode, String expMonth, String expYear, String type, String verificationCode) {
        this.number = number;
        this.holder = holder;
        this.city = city;
        this.address = address;
        this.state = state;
        this.postcode = postcode;
        this.expMonth = expMonth;
        this.expYear = expYear;
        this.type = type;
        this.verificationCode = verificationCode;
    }

    public static MemberCreditCard create(CreditCardSubmission submission) {
        MemberCreditCard memberCreditCard = new MemberCreditCard();
        return build(submission, memberCreditCard);
    }

    public static MemberCreditCard update(CreditCardSubmission submission, MemberCreditCard memberCreditCard) {
        return build(submission, memberCreditCard);
    }

    private static MemberCreditCard build(CreditCardSubmission submission, MemberCreditCard memberCreditCard) {

        if (submission == null) return null;

        memberCreditCard.setNumber(submission.getNumber());
        memberCreditCard.setHolder(submission.getHolder());
        memberCreditCard.setCity(submission.getCity());
        memberCreditCard.setAddress(submission.getAddress());
        memberCreditCard.setState(submission.getState());
        memberCreditCard.setPostcode(submission.getPostcode());
        memberCreditCard.setExpMonth(submission.getExpMonth());
        memberCreditCard.setExpYear(submission.getExpYear());
        memberCreditCard.setType(submission.getType());
        memberCreditCard.setVerificationCode(submission.getVerificationCode());

        return memberCreditCard;
    }

//    public static MemberCreditCard from(CreditCardSubmission submission) {
//
//        if (submission == null) {
//            return null;
//        }
//
//        MemberCreditCard memberCreditCard = new MemberCreditCard();
//
//        memberCreditCard.setNumber(submission.getNumber());
//        memberCreditCard.setHolder(submission.getHolder());
//        memberCreditCard.setCity(submission.getCity());
//        memberCreditCard.setAddress(submission.getAddress());
//        memberCreditCard.setState(submission.getState());
//        memberCreditCard.setPostcode(submission.getPostcode());
//        memberCreditCard.setExpMonth(submission.getExpMonth());
//        memberCreditCard.setExpYear(submission.getExpYear());
//        memberCreditCard.setType(submission.getType());
//        memberCreditCard.setVerificationCode(submission.getVerificationCode());
//
//        return memberCreditCard;
//    }

    public static MemberCreditCard from(EnrolmentFormSubmission submission) {
        MemberCreditCard memberCreditCard = new MemberCreditCard();

        memberCreditCard.setNumber(submission.getPaymentDetails().getCreditCardNumber().replaceAll("\\s",""));
        memberCreditCard.setVerificationCode(submission.getPaymentDetails().getCardVerificationCode());
        if (submission.getPaymentDetails().getCreditCardExpiry() != null) {
            String[] expiry = submission.getPaymentDetails().getCreditCardExpiry().split("/");
            memberCreditCard.setExpMonth(expiry[0]);
            memberCreditCard.setExpYear("20"+expiry[1]);
        }

        if (submission.getPaymentDetails().getUseExistingDetails().equals("yes")) {
            memberCreditCard.setHolder(Helpers.capitalise(submission.getPrimaryDetails().getFirstName()) +" "+ Helpers.capitalise(submission.getPrimaryDetails().getLastName()));
            memberCreditCard.setAddress(Helpers.capitalise(submission.getMemberDetails().getAddress1().trim()) +" "+ Helpers.capitalise(submission.getMemberDetails().getAddress2()));
            memberCreditCard.setCity(Helpers.capitalise(submission.getMemberDetails().getCity()));
            memberCreditCard.setState(submission.getMemberDetails().getState());
            memberCreditCard.setPostcode(submission.getMemberDetails().getPostcode());
        } else {
            memberCreditCard.setHolder(Helpers.capitalise(submission.getPaymentDetails().getFirstName()) +" "+ Helpers.capitalise(submission.getPaymentDetails().getLastName()));
            memberCreditCard.setAddress(Helpers.capitalise(submission.getPaymentDetails().getAddress1().trim()) +" "+ Helpers.capitalise(submission.getPaymentDetails().getAddress2()));
            memberCreditCard.setCity(Helpers.capitalise(submission.getPaymentDetails().getCity()));
            memberCreditCard.setState(submission.getPaymentDetails().getState());
            memberCreditCard.setPostcode(submission.getPaymentDetails().getPostcode());
        }

        return memberCreditCard;
    }

    public static MemberCreditCard from(PaymentDetails details) {

        MemberCreditCard memberCreditCard = new MemberCreditCard();

        try {
            memberCreditCard.setNumber(details.getCreditCardNumber().replaceAll("\\s", ""));
            memberCreditCard.setVerificationCode(details.getCardVerificationCode());
            if (details.getCreditCardExpiry() != null) {
                String[] expiry = details.getCreditCardExpiry().split("/");
                memberCreditCard.setExpMonth(expiry[0]);
                memberCreditCard.setExpYear("20" + expiry[1]);
            }
        } catch (Exception e) {
            logger.error("Error setting credit card details");
            return null;
        }

        try {
            memberCreditCard.setHolder(Helpers.capitalise(details.getFirstName()) + " " + Helpers.capitalise(details.getLastName()));
        } catch (Exception e) {
            logger.error("Error Member Credit Card Name: {}",e.getMessage());
        }

        try {
            memberCreditCard.setAddress(Helpers.capitalise(details.getAddress1().trim()) +" "+ Helpers.capitalise(details.getAddress2()));
        } catch (Exception e) {
            logger.error("Error Member Credit Card Address: {}",e.getMessage());
        }

        try {
            memberCreditCard.setCity(Helpers.capitalise(details.getCity()));
        } catch (Exception e) {
            logger.error("Error Member Credit Card City: {}",e.getMessage());
        }

        try {
            memberCreditCard.setState(details.getState());
        } catch (Exception e) {
            logger.error("Error Member Credit Card State: {}",e.getMessage());
        }

        try {
            memberCreditCard.setPostcode(details.getPostcode());
        } catch (Exception e) {
            logger.error("Error Member Credit Card Post Code: {}",e.getMessage());
        }

        return memberCreditCard;

    }



    public static MemberCreditCard from(MboClientProfile mboClientProfile) {
        MemberCreditCard memberCreditCard = new MemberCreditCard();
        memberCreditCard.setHolder(mboClientProfile.getFirstName()+" "+mboClientProfile.getLastName());
        memberCreditCard.setCity(mboClientProfile.getCity());
        memberCreditCard.setAddress(mboClientProfile.getAddressLine1() + " " + mboClientProfile.getAddressLine2());
        memberCreditCard.setState(mboClientProfile.getState());
        memberCreditCard.setPostcode(mboClientProfile.getPostcode());
//        TODO: This need to be encrypted
        memberCreditCard.setNumber(mboClientProfile.getCardNumber());

        memberCreditCard.setExpMonth(mboClientProfile.getExpMonth());
        memberCreditCard.setExpYear(mboClientProfile.getExpYear());

        return memberCreditCard;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if(null != address) {
            this.address = address.trim();
        } else {
            this.address = address;
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        if(null != state) {
            this.state = state.trim();
        }
        else
            this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        if(null != postcode) {
            this.postcode = postcode.trim();
        } else {
            this.postcode = postcode;
        }
    }

    public String getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(String expMonth) {
        this.expMonth = expMonth;
    }

    public String getExpYear() {
        return expYear;
    }

    public void setExpYear(String expYear) {
        this.expYear = expYear;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberCreditCard that = (MemberCreditCard) o;

        if (id != that.id) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (holder != null ? !holder.equals(that.holder) : that.holder != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (postcode != null ? !postcode.equals(that.postcode) : that.postcode != null) return false;
        if (expMonth != null ? !expMonth.equals(that.expMonth) : that.expMonth != null) return false;
        if (expYear != null ? !expYear.equals(that.expYear) : that.expYear != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return verificationCode != null ? verificationCode.equals(that.verificationCode) : that.verificationCode == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (holder != null ? holder.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postcode != null ? postcode.hashCode() : 0);
        result = 31 * result + (expMonth != null ? expMonth.hashCode() : 0);
        result = 31 * result + (expYear != null ? expYear.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (verificationCode != null ? verificationCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberCreditCard{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", holder='" + holder + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", state='" + state + '\'' +
                ", postcode='" + postcode + '\'' +
                ", expMonth='" + expMonth + '\'' +
                ", expYear='" + expYear + '\'' +
                ", type='" + type + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                '}';
    }
}
