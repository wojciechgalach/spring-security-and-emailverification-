package pl.com.nur.springsecurityemailverification.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "appusers")
public class AppUser  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    private Integer countLog;
    private AppRole role;
//    private String role;

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    private boolean isEnabled;

    public AppUser() {
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
        countLog = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        setRole(AppRole.USER);
//        setRole("USER");
        getRole();
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public String getPassword() {
        return password;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getCountLog() {
        return countLog;
    }

    public void setCountLog() {
        countLog++;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCountLog(Integer countLog) {
        this.countLog = countLog;
    }

    public AppRole getRole() {
        return role;
    }

    public void setRole(AppRole role) {
        this.role = role;
    }

//    public String getRole(){
//        return role;
//    }
//    public void setRole(String role){
//        this.role = role;
//    }



    @Override
    public String toString() {
        return "People{" +
                "nick='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", countLog=" + countLog +
                '}';
    }
}
