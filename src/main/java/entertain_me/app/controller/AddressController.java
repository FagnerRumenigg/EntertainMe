package entertain_me.app.controller;

import entertain_me.app.dto.user.AddressDto;
import entertain_me.app.dto.user.LoginResponseDto;
import entertain_me.app.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping(value = "address", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class AddressController {










    @Autowired
    AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<?> saveAddress(@RequestBody @Valid AddressDto addressDto){
        addressService.saveAddress(addressDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
