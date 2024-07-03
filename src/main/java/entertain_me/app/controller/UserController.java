package entertain_me.app.controller;

import entertain_me.app.dto.user.ChangeEmailPasswordDto;
import entertain_me.app.exception.AlreadyExistsException;
import entertain_me.app.exception.EmailNotValidException;
import entertain_me.app.exception.IncorrectPasswordException;
import entertain_me.app.service.UserService;
import entertain_me.app.vo.exception.ProblemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "User")
@CrossOrigin
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Change the user email", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email changed successfully"),
            @ApiResponse(responseCode = "403", description = "The user's email is not in the correct format",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))}),
            @ApiResponse(responseCode = "403", description = "The user's email is already registered",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})})
    @PostMapping(value = "/changeEmail", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeEmail(@RequestBody @Valid ChangeEmailPasswordDto userDto) throws AlreadyExistsException, EmailNotValidException, IncorrectPasswordException {
        userService.changeEmail(userDto);

        return ResponseEntity.ok().build();
    }

}
