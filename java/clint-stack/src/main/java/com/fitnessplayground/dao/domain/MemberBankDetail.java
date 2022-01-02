package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.temp.BankDetailSubmission;
import com.fitnessplayground.dao.domain.temp.EnrolmentDataSubmission;
import com.fitnessplayground.dao.domain.temp.EnrolmentFormSubmission;
import com.fitnessplayground.dao.domain.temp.PaymentDetails;
import com.fitnessplayground.util.Helpers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

/**
 * Created by micheal on 25/02/2017.
 */
@Entity
public class MemberBankDetail {

    private static final Logger logger = LoggerFactory.getLogger(MemberBankDetail.class);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String accountHolderName;
    private String branch;
    private String financialInstitution;
    private String bsb;
    private String accountNumber;
    private String accountType;

    public MemberBankDetail() {
    }

    public MemberBankDetail(String accountHolderName, String branch, String financialInstitution, String bsb, String accountNumber, String accountType) {
        this.accountHolderName = accountHolderName;
        this.branch = branch;
        this.financialInstitution = financialInstitution;
        this.bsb = bsb;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public static MemberBankDetail create(BankDetailSubmission submission) {

        MemberBankDetail memberBankDetail = new MemberBankDetail();
        return build(submission, memberBankDetail);
    }

    public static MemberBankDetail update(BankDetailSubmission submission, MemberBankDetail memberBankDetail) {

        return build(submission, memberBankDetail);
    }

    private static MemberBankDetail build(BankDetailSubmission submission, MemberBankDetail memberBankDetail) {

        if (submission == null) return null;

        memberBankDetail.setAccountHolderName(submission.getAccountHolderName());
        memberBankDetail.setBranch(submission.getBranch());
        memberBankDetail.setFinancialInstitution(submission.getFinancialInstitution());
        memberBankDetail.setBsb(submission.getBsb());
        memberBankDetail.setAccountNumber(submission.getAccountNumber());
        memberBankDetail.setAccountType(submission.getAccountType());

        return memberBankDetail;

    }

//    public static MemberBankDetail from(BankDetailSubmission submission) {
//
//        if (submission == null) {
//            return null;
//        }
//
//        MemberBankDetail memberBankDetail = new MemberBankDetail();
//
//        memberBankDetail.setAccountHolderName(submission.getAccountHolderName());
//        memberBankDetail.setBranch(submission.getBranch());
//        memberBankDetail.setFinancialInstitution(submission.getFinancialInstitution());
//        memberBankDetail.setBsb(submission.getBsb());
//        memberBankDetail.setAccountNumber(submission.getAccountNumber());
//        memberBankDetail.setAccountType(submission.getAccountType());
//
//        return memberBankDetail;
//    }

    public static MemberBankDetail from(EnrolmentFormSubmission submission) {
        MemberBankDetail memberBankDetail = new MemberBankDetail();

        if (submission.getPaymentDetails().getUseExistingDetails().equals("yes")) {
            memberBankDetail.setAccountHolderName(Helpers.capitalise(submission.getPrimaryDetails().getFirstName()) +" "+ Helpers.capitalise(submission.getPrimaryDetails().getLastName()));
        } else {
            memberBankDetail.setAccountHolderName(Helpers.capitalise(submission.getPaymentDetails().getFirstName()) +" "+ Helpers.capitalise(submission.getPaymentDetails().getLastName()));
        }

        memberBankDetail.setFinancialInstitution(submission.getPaymentDetails().getFinancialInstitution());
        memberBankDetail.setBsb(submission.getPaymentDetails().getBsb());
        memberBankDetail.setAccountNumber(submission.getPaymentDetails().getAccountNumber());
        memberBankDetail.setAccountType(submission.getPaymentDetails().getAccountType());

        return memberBankDetail;
    }

    public static MemberBankDetail from(PaymentDetails details) {

        MemberBankDetail memberBankDetail = new MemberBankDetail();

        try {
            memberBankDetail.setAccountHolderName(Helpers.capitalise(details.getFirstName()) + " " + Helpers.capitalise(details.getLastName()));
        } catch (Exception e) {
            logger.error("Error Member Bank Details Name");
        }

        try {
            memberBankDetail.setFinancialInstitution(details.getFinancialInstitution());
        } catch (Exception e) {
            logger.error("Error Member Bank Details Institution");
        }

        try {
            memberBankDetail.setBsb(details.getBsb());
        } catch (Exception e) {
            logger.error("Error Member Bank Details BSB");
        }

        try {
            memberBankDetail.setAccountNumber(details.getAccountNumber());
        } catch (Exception e) {
            logger.error("Error Member Bank Details Account Number");
        }
        try {
            memberBankDetail.setAccountType(details.getAccountType());
        } catch (Exception e) {
            logger.error("Error Member Bank Details Account Type");
        }

        return memberBankDetail;

    }


    public long getId() {
        return id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public String getBsb() {
        return bsb;
    }

    public void setBsb(String bsb) {
        this.bsb = bsb;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberBankDetail that = (MemberBankDetail) o;

        if (id != that.id) return false;
        if (accountHolderName != null ? !accountHolderName.equals(that.accountHolderName) : that.accountHolderName != null)
            return false;
        if (branch != null ? !branch.equals(that.branch) : that.branch != null) return false;
        if (financialInstitution != null ? !financialInstitution.equals(that.financialInstitution) : that.financialInstitution != null)
            return false;
        if (bsb != null ? !bsb.equals(that.bsb) : that.bsb != null) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        return accountType != null ? accountType.equals(that.accountType) : that.accountType == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (accountHolderName != null ? accountHolderName.hashCode() : 0);
        result = 31 * result + (branch != null ? branch.hashCode() : 0);
        result = 31 * result + (financialInstitution != null ? financialInstitution.hashCode() : 0);
        result = 31 * result + (bsb != null ? bsb.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (accountType != null ? accountType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MemberBankDetail{" +
                "id=" + id +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", branch='" + branch + '\'' +
                ", financialInstitution='" + financialInstitution + '\'' +
                ", bsb='" + bsb + '\'' +
//                ", accountNumber='" + accountNumber + '\'' +
                ", accountType='" + accountType + '\'' +
                '}';
    }
}
