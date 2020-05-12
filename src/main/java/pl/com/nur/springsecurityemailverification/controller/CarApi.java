package pl.com.nur.springsecurityemailverification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.com.nur.springsecurityemailverification.model.Car;
import pl.com.nur.springsecurityemailverification.repository.CarDbRepo;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
public class CarApi {

    private CarDbRepo carList;
    private String search;

    public CarApi(CarDbRepo carList) {
        this.carList = carList;
    }



    @GetMapping
    public String getVehicles(Model model){
        model.addAttribute("cars",   carList.findAll());
        model.addAttribute("newCar", new Car());
        model.addAttribute("delCar", new Car());
        model.addAttribute("modCar", new Car());
        model.addAttribute("searchCar", new Car());

        return "vehicle";
    }


    @PostMapping("/add")
    public String addVehicle(@ModelAttribute Car car){
       try {
           carList.save(car);
       } catch (Exception e){}
       return "redirect:/";
    }


    @PostMapping("/modification")
    public String modElementVehicle(@ModelAttribute Car car){
        try {
            carList.saveAndFlush(car);
        }  catch (Exception e){}
            return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id){
        try {
            carList.deleteById(id);
        }  catch (Exception e){}
            return "redirect:/";
    }

}
