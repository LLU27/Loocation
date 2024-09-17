package learn.loocation.controllers;

import learn.loocation.domain.AddressService;
import learn.loocation.models.Address;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("{/addressId}")
    public Address findAddressById(int addressId) {
        return service.findAddressById(addressId);
    }


}