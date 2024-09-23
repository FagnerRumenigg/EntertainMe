package entertain_me.app.controller;

import entertain_me.app.vo.ProblemVo;
import entertain_me.app.dto.user.AddressDto;
import entertain_me.app.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping(value = "address", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Address")
@CrossOrigin
@RestController
public class AddressController {

    @Autowired
    AddressService addressService;

    @Operation(summary = "Save the address user information", method = "POST", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address saved successfully",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = AddressDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveAddress(@RequestBody @Valid AddressDto addressDto){
        addressService.saveAddress(addressDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
