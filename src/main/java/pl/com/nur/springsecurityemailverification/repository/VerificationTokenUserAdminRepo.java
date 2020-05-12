package pl.com.nur.springsecurityemailverification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.nur.springsecurityemailverification.model.VerificationTokenUserAdmin;

@Repository
public interface VerificationTokenUserAdminRepo extends JpaRepository<VerificationTokenUserAdmin, Long> {

    VerificationTokenUserAdmin findByValueUser(String value);
    VerificationTokenUserAdmin findByValueAdmin(String value);


}
