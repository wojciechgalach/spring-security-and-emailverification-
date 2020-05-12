package pl.com.nur.springsecurityemailverification.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.nur.springsecurityemailverification.model.AppRole;
import pl.com.nur.springsecurityemailverification.model.AppUser;
import pl.com.nur.springsecurityemailverification.model.VerificationTokenUserAdmin;
import pl.com.nur.springsecurityemailverification.repository.AppUserRepo;
import pl.com.nur.springsecurityemailverification.repository.VerificationTokenUserAdminRepo;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserServiceNur {

    @Value("${superadmin}")
    private String SUPERADMIN;


    private AppUserRepo appUserRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenUserAdminRepo verificationTokenUserAdminRepo;
    private MailSenderService mailSenderService;


    public UserServiceNur(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, VerificationTokenUserAdminRepo verificationTokenUserAdminRepo, MailSenderService mailSenderService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenUserAdminRepo = verificationTokenUserAdminRepo;
        this.mailSenderService = mailSenderService;
    }

    public boolean addUser(AppUser appUser, HttpServletRequest request) {
        if (appUserRepo.findAllByUsername(appUser.getUsername()) == null) {
            boolean admin = false;
            VerificationTokenUserAdmin verificationTokenUser = new VerificationTokenUserAdmin();
            String valueUser = UUID.randomUUID().toString();
            verificationTokenUser.setValueUser(valueUser);

            String valueAdmin = UUID.randomUUID().toString();
            if (appUser.getRole() == AppRole.ADMIN) {
                admin = true;
                verificationTokenUser.setValueAdmin(valueAdmin);
            }
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            appUser.setRole(AppRole.USER);
            appUser.setEnabled(false);  // standardowo jest na false
            appUserRepo.save(appUser);

            verificationTokenUser.setAppUser(appUser);
            verificationTokenUserAdminRepo.save(verificationTokenUser);

            String url = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath()
                    + "/verifytoken?token=" + valueUser;
            try {
                mailSenderService.sendMail(appUser.getUsername(), "Verification Token", url, false);
            } catch (MessagingException e) {
                e.printStackTrace();
                return false;
            }

            if (admin) {
                String urlAdmin = "http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath()
                        + "/verifyadmin?token=" + valueAdmin;
                try {
                    mailSenderService.sendMail(SUPERADMIN, "Verification Admin", urlAdmin, false);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean verifytoken(String token) {

        VerificationTokenUserAdmin verification = new VerificationTokenUserAdmin();
        verification = verificationTokenUserAdminRepo.findByValueUser(token);
        if (verification != null) {
            AppUser user = verification.getAppUser();
            user.setEnabled(true);
            appUserRepo.save(user);
            String admin = verification.getValueAdmin();
            if (admin == null) {
                verificationTokenUserAdminRepo.deleteById(verification.getId());
            }
            return true;
        }
        return false;
    }

    public boolean verifytokenAdmin(String token) {
        VerificationTokenUserAdmin verification = new VerificationTokenUserAdmin();
        verification = verificationTokenUserAdminRepo.findByValueAdmin(token);
        if (verification != null) {
            AppUser user = verification.getAppUser();
            user.setRole(AppRole.ADMIN);
            user.setEnabled(true);
            appUserRepo.save(user);
            verificationTokenUserAdminRepo.deleteById(verification.getId());
            return true;
        }
        return false;
    }
}
