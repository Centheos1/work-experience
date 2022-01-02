package com.fitnessplayground.dao.domain;

import com.fitnessplayground.dao.domain.ActiveCampaignDto.ACField;
import com.fitnessplayground.util.Helpers;

import javax.persistence.*;

@Entity
public class AcCustomField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    private String title;
    private String descript;
    private String type;
    private String perstag;
    private String cdate;
    private String udate;
    private String fieldId;
    
    public AcCustomField() {
    }
    
    public static AcCustomField create(ACField acField) {
        
        AcCustomField acCustomField = new AcCustomField();
        return build(acField, acCustomField);
    }
    
    public static AcCustomField update(ACField acField, AcCustomField acCustomField) {
        return build(acField, acCustomField);
    }
    
    private static AcCustomField build(ACField acField, AcCustomField acCustomField) {

        acCustomField.setTitle(acField.getTitle());
        acCustomField.setDescript(acField.getDescript());
        acCustomField.setType(acField.getType());
        acCustomField.setPerstag(acField.getPerstag());
        acCustomField.setCdate(Helpers.cleanDateTime(acField.getCdate()));
        acCustomField.setUdate(Helpers.cleanDateTime(acField.getUdate()));
        acCustomField.setFieldId(acField.getFieldId());
        
        return acCustomField;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPerstag() {
        return perstag;
    }

    public void setPerstag(String perstag) {
        this.perstag = perstag;
    }

    public String getCdate() {
        return cdate;
    }

    public void setCdate(String cdate) {
        this.cdate = cdate;
    }

    public String getUdate() {
        return udate;
    }

    public void setUdate(String udate) {
        this.udate = udate;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    @Override
    public String toString() {
        return "AcCustomField{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", descript='" + descript + '\'' +
                ", type='" + type + '\'' +
                ", perstag='" + perstag + '\'' +
                ", cdate='" + cdate + '\'' +
                ", udate='" + udate + '\'' +
                ", fieldId='" + fieldId + '\'' +
                '}';
    }
}
