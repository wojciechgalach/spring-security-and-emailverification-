package pl.com.nur.springsecurityemailverification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.nur.springsecurityemailverification.model.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    AppUser findAllByUsername(String username);
}
