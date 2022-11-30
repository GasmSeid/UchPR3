package com.example.Rabota.Controller;
import com.example.Rabota.Models.Cars;
import com.example.Rabota.repo.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CarsController {
    @Autowired
    private CarsRepository carsRepository;
    @GetMapping("/")
    public String GetRab(Model model)
    {
        Iterable<Cars> cars = carsRepository.findAll();
        model.addAttribute("Cars",cars);
        return "Main";
    }
    @GetMapping("/Add/Cars")
    public String GetAddStudent(Model model)
    {
        return "Add-Cars";
    }
    @PostMapping("/Add/Cars")
    public String blogPostAdd(@RequestParam(value="CarBrand") String CarBrand,
                              @RequestParam(value ="carsmodel" ) String carsmodel,
                              @RequestParam(value = "year_release") String year_release,
                              @RequestParam(value ="color" ) String color,
                              @RequestParam(value = "VIN") String VIN,
                              @RequestParam(value = "horsepower") int horsepower,
                              @RequestParam(value = "engine_capacity") String engine_capacity,
                              Model model)
    {
        Cars cars = new Cars(CarBrand,carsmodel,year_release,color,VIN,horsepower,engine_capacity);
        carsRepository.save(cars);
        return "redirect:/";
    }
    @GetMapping( path = "/Search/Cars")
    public String blogFilter(Model model)
    {
        return "Search-Cars";
    }

    @PostMapping("/Search/Cars-result")
    public String blogResult(@RequestParam String carsmodel, Model model)
    {
        List<Cars> cars = carsRepository.findByCarsmodel(carsmodel);
        model.addAttribute("result", cars);
        return "Search-Cars";
    }
    @PostMapping("/Search/Cars")
    public String simpleSearch(@RequestParam String carsmodel, Model model)
    {
        List<Cars> cars = carsRepository.findByCarsmodelContains(carsmodel);
        model.addAttribute("result", cars);
        return "Search-Cars";
    }

    @GetMapping("/blog/Cars/{id}/Edit")
    public String CarEdit(@PathVariable(value = "id") Long id, Model model) {
        if (!carsRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Cars> car = carsRepository.findById(id);
        ArrayList<Cars> lis = new ArrayList<>();
        car.ifPresent(lis::add);
        model.addAttribute("Cars", lis);
        return "Edit-cars";
    }
    @PostMapping("/blog/Cars/{id}/Edit")
    public String CarUpdate(
            @PathVariable(value = "id") Long id,
            @RequestParam(value="CarBrand") String CarBrand,
            @RequestParam(value ="carsmodel" ) String carsmodel,
            @RequestParam(value = "year_release") String year_release,
            @RequestParam(value ="color" ) String color,
            @RequestParam(value = "VIN") String VIN,
            @RequestParam(value = "horsepower") int horsepower,
            @RequestParam(value = "engine_capacity") String engine_capacity,
            Model model){
        Cars cars = carsRepository.findById(id).orElseThrow();
        cars.setCarBrand(CarBrand);
        cars.setCarsmodel(carsmodel);
        cars.setYear_release(year_release);
        cars.setColor(color);
        cars.setVIN(VIN);
        cars.setHorsepower(horsepower);
        cars.setEngine_capacity(engine_capacity);
        carsRepository.save(cars);
        return "redirect:/";
    }

    @PostMapping("/blog/Cars/{id}/Remove")
    public String blogCarDelete(
            @PathVariable(value = "id") Long id,
            Model model) {
        Cars cars = carsRepository.findById(id).orElseThrow();
        carsRepository.delete(cars);
        return "redirect:/";
    }
    @GetMapping("/blog/Cars/{id}")
    public String CarDetails(@PathVariable(value = "id") Long id, Model model) {
        Optional<Cars> car = carsRepository.findById(id);
        ArrayList<Cars> lis = new ArrayList<>();
        car.ifPresent(lis::add);
        model.addAttribute("Cars", lis);
        return "blog-CarsDetails";
    }
}
