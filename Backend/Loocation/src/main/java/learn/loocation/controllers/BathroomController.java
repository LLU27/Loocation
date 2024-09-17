package learn.loocation.controllers;

import learn.loocation.domain.BathroomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bathroom")
public class BathroomController {

    private final BathroomService service;

    public BathroomController(BathroomService service) {
        this.service = service;
    }

}