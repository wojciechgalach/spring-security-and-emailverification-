package pl.com.nur.springsecurityemailverification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.com.nur.springsecurityemailverification.model.AppUser;
import pl.com.nur.springsecurityemailverification.repository.AppUserRepo;
import pl.com.nur.springsecurityemailverification.service.UserServiceNur;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityApi {

    private UserServiceNur userServiceNur;
    private AppUserRepo appUserRepo;

    public SecurityApi(UserServiceNur userServiceNur) {
        this.userServiceNur = userServiceNur;
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }


    @RequestMapping("/singup")
    public ModelAndView singup(){
        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView registration(AppUser appUser, HttpServletRequest httpServletRequest){
        if (userServiceNur.addUser(appUser, httpServletRequest)){
            return new ModelAndView("redirect:/login");
        }
        else {
            return new ModelAndView("registrationerror");
        }
    }

    @RequestMapping("/verifytoken")
    public ModelAndView registerUser(String token)
    {
        if(userServiceNur.verifytoken(token)) {
            return new ModelAndView("redirect:/login");
        }
        else {
            return new ModelAndView("redirect:/login");
        }
    }

    @RequestMapping("/verifyadmin")
    public ModelAndView registerAdmin(String token)
    {
        if(userServiceNur.verifytokenAdmin(token)) {
            return new ModelAndView("redirect:/login");
        }
        return new ModelAndView("redirect:/login");
    }

}
