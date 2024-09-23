package entertain_me.app.controller;

import entertain_me.app.service.LegalDocumentService;
import entertain_me.app.vo.AllAnimeInfoVo;
import entertain_me.app.vo.LegalDocumentVo;
import entertain_me.app.vo.ProblemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "legalDocument",  produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Legal Document")
@CrossOrigin
@RestController
public class LegalDocumentController {

    @Autowired
    LegalDocumentService legalDocumentService;

    @Operation(summary = "Get legal document by type", method = "GET", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Document founded",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = LegalDocumentVo.class))}),
            @ApiResponse(responseCode = "204", description = "Document type not founded",
                    content = { @Content(mediaType =  "application/json")}),
            @ApiResponse(responseCode = "500", description = "Internal error",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ProblemVo.class))})
    })
    @GetMapping("/getByType/{typeDocument}")
    public ResponseEntity<LegalDocumentVo> getLegalDocumentByType(@Parameter(description = "Document Type", example = "Policy privacy") @PathVariable String typeDocument) {
        LegalDocumentVo legalDocumentVo = legalDocumentService.getLegalDocumentRepository(typeDocument);
        return legalDocumentVo == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(legalDocumentVo);
    }
}
