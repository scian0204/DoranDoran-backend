package com.daelim.dorandoranbackend.controller;

import com.daelim.dorandoranbackend.entity.Apart;
import com.daelim.dorandoranbackend.service.ApartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/apart")
public class ApartController {
    @Autowired
    ApartService apartService;

    @GetMapping("/apartName/{apartName}")
    public List<String> getApartList(@PathVariable String apartName) {
        return apartService.getApartList(apartName);
    }

    @GetMapping("/dong/{apartName}")
    public List<String> getDongList(@PathVariable String apartName) {
        return apartService.getDongList(apartName);
    }

    @GetMapping("/ho")
    public List<String> getHoList(@RequestParam(value = "apartName") String apartName, @RequestParam(value = "dong") String dong) {
        return apartService.getHoList(apartName, dong);
    }

}
