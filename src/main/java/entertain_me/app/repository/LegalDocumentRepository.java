package entertain_me.app.repository;

import entertain_me.app.model.LegalDocument;
import entertain_me.app.vo.LegalDocumentVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LegalDocumentRepository extends JpaRepository <LegalDocument, Long> {

    @Query("SELECT new entertain_me.app.vo.LegalDocumentVo(ld.documentType, ld.documentText) FROM LegalDocument ld" +
            " WHERE UPPER(ld.documentType) = :typeDocument")
    LegalDocumentVo getLegalDocumentByType (String typeDocument);

}
