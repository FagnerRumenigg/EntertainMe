package entertain_me.app.service;

import entertain_me.app.repository.LegalDocumentRepository;
import entertain_me.app.vo.LegalDocumentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LegalDocumentService {

    @Autowired
    LegalDocumentRepository legalDocumentRepository;

    public LegalDocumentVo getLegalDocumentRepository(String typeDocument) {

        return legalDocumentRepository.getLegalDocumentByType(typeDocument.toUpperCase());
    }
}
