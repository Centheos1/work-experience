package com.fitnessplayground.dao.domain.dto;

/**
 * Created by micheal on 22/07/2017.
 */
public class MemberCancelDto {
    private long id;
    private String email;
    private String cancelReason;

    public MemberCancelDto() {
    }

    public MemberCancelDto(long id, String email, String cancelReason) {
        this.id = id;
        this.email = email;
        this.cancelReason = cancelReason;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberCancelDto that = (MemberCancelDto) o;

        if (id != that.id) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return cancelReason != null ? cancelReason.equals(that.cancelReason) : that.cancelReason == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (cancelReason != null ? cancelReason.hashCode() : 0);
        return result;
    }
}
