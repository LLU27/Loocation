package learn.loocation.controllers;

import learn.loocation.domain.AddressService;
import learn.loocation.domain.Result;
import learn.loocation.models.Address;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final AddressService service;

    public AddressController(AddressService service) {
        this.service = service;
    }

    @GetMapping("/{addressId}")
    public Address findAddressById(@PathVariable int addressId) {
        return service.findAddressById(addressId);
    }
    @GetMapping("/findByStreet/{street}")
    public Address findAddressByStreet(@PathVariable String street) {
        return service.findAddressByStreet(street);
    }
    @GetMapping("/lat/{latitude}/long/{longitude}")
    public ResponseEntity<Address> findByLatLong(@PathVariable double latitude, @PathVariable double longitude) {
        Address address = service.findByLatLong(latitude, longitude);
        if (address != null) {
            return ResponseEntity.ok(address);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addAddress(@RequestBody Address address) {
        Result<Address> result = service.addAddress(address);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<Object> updateAddress(@PathVariable int addressId, @RequestBody Address address) {
        if(addressId != address.getAddressId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Address> result = service.updateAddress(address);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddressById(@PathVariable int addressId) {
        Result<Boolean> result = service.deleteAddressById(addressId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}