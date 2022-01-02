package com.fitnessplayground.dao.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class CloudSearch {

    @Id
    private Long mbo_unique_id;
    private String access_key_number;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;

    public CloudSearch() {
    }

    public Long getMbo_unique_id() {
        return mbo_unique_id;
    }

    public void setMbo_unique_id(Long mbo_unique_id) {
        this.mbo_unique_id = mbo_unique_id;
    }

    public String getAccess_key_number() {
        return access_key_number;
    }

    public void setAccess_key_number(String access_key_number) {
        this.access_key_number = access_key_number;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CloudSearch)) return false;
        CloudSearch that = (CloudSearch) o;
        return getMbo_unique_id().equals(that.getMbo_unique_id());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMbo_unique_id());
    }

    @Override
    public String toString() {
        return "CloudSearch{" +
                "mbo_unique_id=" + mbo_unique_id +
                ", access_key_number='" + access_key_number + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
