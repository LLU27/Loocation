package learn.loocation.controllers;

import learn.loocation.domain.BathroomService;
import learn.loocation.domain.Result;
import learn.loocation.models.Bathroom;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bathroom")
public class BathroomController {

    private final BathroomService service;

    public BathroomController(BathroomService service) {
        this.service = service;
    }

    @GetMapping
    public List<Bathroom> findAllBathrooms() {
        return service.findAllBathrooms();
    }

    @GetMapping("/{bathroomId}")
    public Bathroom findBathroomById(int bathroomId) {
        return service.findBathroomById(bathroomId);
    }

    @PostMapping
    public ResponseEntity<Object> addBathroom(Bathroom bathroom) {
        Result<Bathroom> result = service.addBathroom(bathroom);
        if(result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping
    public ResponseEntity<Object> updateBathroom(@PathVariable int bathroomId, @RequestBody Bathroom bathroom) {
        if(bathroomId != bathroom.getBathroomId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Bathroom> result = service.updateBathroom(bathroom);
        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{bathroomId}")
    public ResponseEntity<Void> deleteBathroomById(@PathVariable int bathroomId) {
        Result<Boolean> result = service.deleteBathroomById(bathroomId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}