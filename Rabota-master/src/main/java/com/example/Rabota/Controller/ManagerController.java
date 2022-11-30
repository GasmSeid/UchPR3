package com.example.Rabota.Controller;
import com.example.Rabota.Models.Cars;
import com.example.Rabota.Models.Manager;
import com.example.Rabota.repo.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ManagerController {
    @Autowired
    private ManagerRepository managerRepository;
    @GetMapping("/Manager")
    public String GetRab(Model model)
    {
        Iterable<Manager> manager = managerRepository.findAll();
        model.addAttribute("Manager", manager);
        return "MainManager";
    }
    @GetMapping("/Add/Manager")
    public String GetAddStudent(Model model)
    {
        return "Add-Manager";
    }
    @PostMapping("/Add/Manager")
    public String blogPostAdd(@RequestParam(value="surname") String surname,
                              @RequestParam(value ="name" ) String name,
                              @RequestParam(value = "middlename") String middlename,
                              @RequestParam(value ="bthday" ) String bthday,
                              @RequestParam(value = "SeriaPass") int SeriaPass,
                              @RequestParam(value = "NomPass") int NomPass,
                              @RequestParam(value = "AdresReg") String AdresReg,
                              @RequestParam(value = "Email") String Email,
                              Model model)
    {
        Manager manager = new Manager(surname, name, middlename, bthday, SeriaPass, NomPass, AdresReg, Email);
        managerRepository.save(manager);
        return "redirect:/Manager";
    }
    @GetMapping( path = "/Search/Manager")
    public String blogFilter(Model model)
    {
        return "Search-Manager";
    }

    @PostMapping("/Search/Manager-result")
    public String blogResult(@RequestParam String surname, Model model)
    {
        List<Manager> teach = managerRepository.findBySurname(surname);
        model.addAttribute("result", teach);
        return "Search-Manager";
    }
    @PostMapping("/Search/Manager")
    public String simpleSearch(@RequestParam String surname, Model model)
    {
        List<Manager> teach = managerRepository.findBySurnameContains(surname);
        model.addAttribute("result", teach);
        return "Search-Manager";
    }


    @GetMapping("/blog/Manager/{id}/Edit")
    public String blogEdit(@PathVariable(value = "id") Long id, Model model){
        if(!managerRepository.existsById(id)) {
            return "redirect:/Manager";
        }
        Optional<Manager> employee= managerRepository.findById(id);
        ArrayList<Manager> lis = new ArrayList<>();
        employee.ifPresent(lis::add);
        model.addAttribute("Manager", lis);
        return "Edit-manager";

    }
    @PostMapping("/blog/Manager/{id}/Edit")
    public String blogPosyUpdate(
            @PathVariable(value = "id") Long id,
            @RequestParam(value="surname") String surname,
            @RequestParam(value ="name" ) String name,
            @RequestParam(value = "middlename") String middlename,
            @RequestParam(value ="bthday" ) String bthday,
            @RequestParam(value = "SeriaPass") int SeriaPass,
            @RequestParam(value = "NomPass") int NomPass,
            @RequestParam(value = "AdresReg") String AdresReg,
            @RequestParam(value = "Email") String Email,
            Model model){
        Manager manager = managerRepository.findById(id).orElseThrow();
        manager.setSurname(surname);
        manager.setName(name);
        manager.setMiddlename(middlename);
        manager.setBthday(bthday);
        manager.setSeriaPass(SeriaPass);
        manager.setNomPass(NomPass);
        manager.setAdresReg(AdresReg);
        manager.setEmail(Email);


        managerRepository.save(manager);
        return "redirect:/Manager";

    }


    @PostMapping("/blog/Manager/{id}/Remove")
    public String blogManagerDelete(
            @PathVariable(value = "id") Long id,
            Model model) {
        Manager manager = managerRepository.findById(id).orElseThrow();
        managerRepository.delete(manager);
        return "redirect:/Manager";
    }
    @GetMapping("/blog/Manager/{id}")
    public String CarDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Manager> manager = managerRepository.findById(id);
        ArrayList<Manager> lis = new ArrayList<>();
        manager.ifPresent(lis::add);
        model.addAttribute("Manager", lis);
        return "blog-ManagerDetails";
    }
}
