package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACContact;
import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACWebhookContact;
import com.fitnessplayground.util.Helpers;

import javax.persistence.*;

@Entity
public class AcContact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String cdate;
    private String email;
    private String phone;
    private String firstName;
    private String lastName;
    private String acContactId;

    public AcContact() {
    }

    public static AcContact from(ACContact c) {
        AcContact acContact = new AcContact();
        return build(c, acContact);
    }

    public static AcContact update(ACContact c, AcContact acContact) {
        return build(c, acContact);
    }

    private static AcContact build(ACContact c, AcContact acContact) {
        acContact.setCdate(Helpers.cleanDateTime(c.getCdate()));
        acContact.setEmail(c.getEmail());
        acContact.setPhone(c.getPhone());
        acContact.setFirstName(c.getFirstName());
        acContact.setLastName(c.getLastName());
        acContact.setAcContactId(c.getId());

        return acContact;
    }

    public static AcContact from(ACWebhookContact c) {
        AcContact acContact = new AcContact();
        return build(c, acContact);
    }

    public static AcContact update(ACWebhookContact c, AcContact acContact) {
        return build(c, acContact);
    }

    private static AcContact build(ACWebhookContact c, AcContact acContact) {
        acContact.setEmail(c.getEmail());
        acContact.setPhone(c.getPhone());
        acContact.setFirstName(c.getFirst_name());
        acContact.setLastName(c.getLast_name());
        acContact.setAcContactId(c.getId());

        return acContact;
    }

    public long getId() {
        return id;
    }
    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getAcContactId() {
        return acContactId;
    }

    public void setAcContactId(String acContactId) {
        this.acContactId = acContactId;
    }

    @Override
    public String toString() {
        return "AcContact{" +
                "id=" + id +
                ", cdate='" + cdate + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", acContactId='" + acContactId + '\'' +
                '}';
    }
}
