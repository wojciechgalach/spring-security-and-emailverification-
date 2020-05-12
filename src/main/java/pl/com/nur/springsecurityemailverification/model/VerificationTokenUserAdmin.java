package pl.com.nur.springsecurityemailverification.model;

import javax.persistence.*;

@Entity
public class VerificationTokenUserAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String valueUser;
    private String valueAdmin;

    @OneToOne
    private AppUser appUser;

    public VerificationTokenUserAdmin() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValueUser() {
        return valueUser;
    }

    public void setValueUser(String valueUser) {
        this.valueUser = valueUser;
    }

    public String getValueAdmin() {
        return valueAdmin;
    }

    public void setValueAdmin(String valueAdmin) {
        this.valueAdmin = valueAdmin;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
