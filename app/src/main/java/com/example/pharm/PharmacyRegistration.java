package com.example.pharm;

public class PharmacyRegistration {
    String pharmacy, email, phone, address,twitter,facebook,whatsap, role;

    public PharmacyRegistration() {
    }

    public PharmacyRegistration(String pharmacy, String email, String phone, String address, String twitter, String facebook, String whatsap, String role) {
        this.pharmacy = pharmacy;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.twitter = twitter;
        this.facebook = facebook;
        this.whatsap = whatsap;
        this.role = role;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getWhatsap() {
        return whatsap;
    }

    public void setWhatsap(String whatsap) {
        this.whatsap = whatsap;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
