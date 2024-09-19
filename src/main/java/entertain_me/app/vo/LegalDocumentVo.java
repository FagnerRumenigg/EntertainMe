package entertain_me.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LegalDocument")
public record LegalDocumentVo(
        @Schema(description = "Type of document",
                example = "policy privacy")
        String typeDocument,
        @Schema(description = "Content of the document",
                example = "html with the document")
        String textDocument
) {
}
