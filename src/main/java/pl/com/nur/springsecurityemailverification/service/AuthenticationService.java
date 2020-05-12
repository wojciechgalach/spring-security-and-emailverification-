package pl.com.nur.springsecurityemailverification.service;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class AuthenticationService {

    public AuthenticationService() {

    }

    @EventListener
    public void authenticationSuccessEvent(AuthenticationSuccessEvent event) {
//        String user = ((User)event.getAuthentication().getPrincipal()).getUsername();
//        for (int i = 0; i< appUserList.getAppUserList().size(); i++) {
//            if (appUserList.getAppUserList().get(i).getNick().equals(user)) {
//                appUserList.getAppUserList().get(i).setCountLog();
//            }
//        }
    }

    public String authenticationCount(Principal principal){
//        for (int i = 0; i< appUserList.getAppUserList().size(); i++){
//            if(appUserList.getAppUserList().get(i).getNick().equals(principal.getName())){
//                return " ilosc logowań = " + appUserList.getAppUserList().get(i).getCountLog();
//            }
//        }
        return "nie ma Cię, coś poszło nie tak";
    }
}
