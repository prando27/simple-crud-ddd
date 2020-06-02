package com.example.simplecrudddd.application.folder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.dto.CreateDocumentDto;
import com.example.simplecrudddd.application.dto.CreateFolderDto;
import com.example.simplecrudddd.application.dto.DocumentCopyDto;
import com.example.simplecrudddd.application.dto.DocumentDto;
import com.example.simplecrudddd.application.dto.FolderDto;
import com.example.simplecrudddd.application.dto.UpdateDocumentDto;
import com.example.simplecrudddd.application.folder.strategy.createdocument.CreateDocument;
import com.example.simplecrudddd.application.folder.strategy.createdocument.CreateDocumentFactory;
import com.example.simplecrudddd.common.Envelope;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.Folder;
import com.example.simplecrudddd.domain.folder.FolderRepository;
import com.example.simplecrudddd.domain.folder.document.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FolderController {

    private final FolderRepository folderRepository;

    private final DocumentCopyApplicationService documentCopyApplicationService;

    private final CreateDocumentFactory createDocumentFactory;

    @PostMapping("/folders")
    @Transactional
    public ResponseEntity<Envelope<FolderDto>> create(@RequestBody CreateFolderDto dto) {

        var folder = Folder.create(dto.getUserId());
        folderRepository.save(folder);

        return ResponseEntity.ok(Envelope.ok(new FolderDto(
                folder.getId(),
                folder.getUserId(),
                null)));
    }

    @GetMapping("/folders")
    public ResponseEntity<Envelope<List<FolderDto>>> listAll() {
        var folders = folderRepository.findAll();

        return ResponseEntity.ok(Envelope.ok(folders.stream().map(folder -> new FolderDto(
                folder.getId(),
                folder.getUserId(), null
        )).collect(Collectors.toList())));
    }

    @GetMapping("/folders/{folderId}")
    public ResponseEntity<Envelope<FolderDto>> findById(@PathVariable Long folderId) {
        var folderOptional = folderRepository.findById(folderId);

        if (folderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Envelope.ok(folderOptional.map(folder -> new FolderDto(
                folder.getId(),
                folder.getUserId(),
                folder.getDocuments().stream().map(DocumentDto::new).collect(Collectors.toList()))
        ).get()));
    }

    @PutMapping("/folders/{folderId}/documents")
    @Transactional
    public ResponseEntity<Envelope<DocumentDto>> createDocument(@PathVariable Long folderId,
                                                                @RequestBody CreateDocumentDto dto) {

        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
        }
        var folder = folderOptional.get();

        var createStrategyOptional = createDocumentFactory
                .findCreateStrategyByDocumentType(dto.getDocumentType());
        if (createStrategyOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Document Type not exists"));
        }

        var createStrategy = createStrategyOptional.get();
        var createDocumentResult = createStrategy.create(new CreateDocument(folder.getId(), dto));
        if (createDocumentResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(createDocumentResult.getError()));
        }

        Document document = createDocumentResult.getValue();
        boolean documentAdded = folder.addDocument(document);

        if (!documentAdded) {
            var existingDocumentOptional = folder.findDocumentByDocumentTypeQuantity(document);
            if (existingDocumentOptional.isEmpty()) {
                return ResponseEntity.unprocessableEntity().body(Envelope.error("Unexpected"));
            }

            return ResponseEntity.ok(Envelope.ok(existingDocumentOptional.map(DocumentDto::new).get()));
        }

        return ResponseEntity.ok(Envelope.ok(new DocumentDto(document)));
    }

    @PutMapping("/folders/{folderId}/documents/{documentId}")
    @Transactional
    public ResponseEntity<Envelope<DocumentDto>> updateDocument(@PathVariable Long folderId,
                                                                @PathVariable Long documentId,
                                                                @RequestBody UpdateDocumentDto dto) {
        return null;
    }

    @PutMapping("/folders/{folderId}/document-copies")
    public ResponseEntity<Envelope<DocumentCopyDto>> createDocumentCopy(@PathVariable Long folderId,
                                                                        @RequestParam("file") MultipartFile file) {
        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
        }

        Result<DocumentCopyDto> storeDocumentResult = documentCopyApplicationService
                .storeDocumentCopy(folderId, file.getOriginalFilename());

        if (storeDocumentResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(storeDocumentResult.getError()));
        }

        return ResponseEntity.ok(Envelope.ok(new DocumentCopyDto(file.getOriginalFilename())));
    }
}
