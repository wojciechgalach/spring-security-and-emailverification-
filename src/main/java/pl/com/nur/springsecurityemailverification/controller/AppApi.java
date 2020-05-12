package pl.com.nur.springsecurityemailverification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.nur.springsecurityemailverification.service.AuthenticationService;

import java.security.Principal;

@RestController
public class AppApi {

    public AppApi() {
    }

    @GetMapping("/forAll")
    public String forAllStart(Principal principal){
        return "Strona startowa dla wszystkich";
    }

    @GetMapping("/forAllUser")
    public String forAll(Principal principal){
        if (principal == null)
            return "Cześć nieznajomy";
        return "Cześć " + principal.getName();
    }

    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal){
        return "Cześć admin " + principal.getName();
    }

    @GetMapping("/forUser")
    public String forUser(Principal principal){
        return "Cześć user " + principal.getName();
    }

    @GetMapping("/papa")
    public String forAll(){
        return "Papa";
    }

}
