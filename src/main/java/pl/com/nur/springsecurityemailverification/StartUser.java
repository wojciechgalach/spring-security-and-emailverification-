package pl.com.nur.springsecurityemailverification;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.com.nur.springsecurityemailverification.model.AppRole;
import pl.com.nur.springsecurityemailverification.model.AppUser;
import pl.com.nur.springsecurityemailverification.model.Car;
import pl.com.nur.springsecurityemailverification.repository.AppUserRepo;
import pl.com.nur.springsecurityemailverification.repository.CarDbRepo;

@Component
public class StartUser {

    private PasswordEncoder passwordEncoder;
    private AppUserRepo appUserRepo;
    private CarDbRepo carDbRepo;

    public StartUser(PasswordEncoder passwordEncoder, AppUserRepo appUserRepo, CarDbRepo carDbRepo) {
        this.passwordEncoder = passwordEncoder;
        this.appUserRepo = appUserRepo;
        this.carDbRepo = carDbRepo;

        AppUser appUser = new AppUser();
        appUser.setUsername("Wojtek");
        appUser.setPassword(passwordEncoder.encode("1234"));
        appUser.setRole(AppRole.ADMIN);
        appUser.setEnabled(true);
        appUserRepo.save(appUser);

        AppUser appUser1 = new AppUser();
        appUser1.setUsername("Iza");
        appUser1.setPassword(passwordEncoder.encode("1234"));
        appUser1.setRole(AppRole.USER);
        appUser1.setEnabled(true);
        appUserRepo.save(appUser1);

        carDbRepo.save(new Car("Fiat", "126p", "czerwony", 1980));
    }
}
