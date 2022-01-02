package com.fitnessplayground.dao.domain.temp;

public class SearchByPersDetails {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public SearchByPersDetails() {
    }

    public SearchByPersDetails(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        if (phone.substring(0,2).equals("61")) {
            phone = "+" + phone;
        }

        if (phone.charAt(0) == '0') {
            phone = "+61" + phone.substring(1);
        }

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

        if (phone.substring(0,1) == "61") {
            phone = "+" + phone;
        }

        if (phone.charAt(0) == '0') {
            phone = "+61" + phone.substring(1);
        }

        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SearchByPersDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
