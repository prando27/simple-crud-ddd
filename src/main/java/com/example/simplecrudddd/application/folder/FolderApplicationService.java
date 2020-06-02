package com.example.simplecrudddd.application.folder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.folder.dto.DocumentCopyDto;
import com.example.simplecrudddd.application.folder.dto.DocumentDto;
import com.example.simplecrudddd.application.folder.dto.FolderDto;
import com.example.simplecrudddd.application.folder.dto.create.CreateDocumentDto;
import com.example.simplecrudddd.application.folder.dto.create.CreateFolderDto;
import com.example.simplecrudddd.application.folder.dto.update.UpdateDocumentDto;
import com.example.simplecrudddd.application.folder.strategy.createdocument.CreateDocumentStrategyFactory;
import com.example.simplecrudddd.application.folder.strategy.createdocument.CreateDocumentStrategyInput;
import com.example.simplecrudddd.application.folder.strategy.updatedocument.UpdateDocumentStrategyFactory;
import com.example.simplecrudddd.application.folder.strategy.updatedocument.UpdateDocumentStrategyInput;
import com.example.simplecrudddd.common.Envelope;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.Folder;
import com.example.simplecrudddd.domain.folder.FolderRepository;
import com.example.simplecrudddd.domain.folder.document.Document;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FolderApplicationService {

    private final FolderRepository folderRepository;

    private final DocumentCopyApplicationService documentCopyApplicationService;

    private final CreateDocumentStrategyFactory createDocumentStrategyFactory;

    private final UpdateDocumentStrategyFactory updateDocumentStrategyFactory;

    @Transactional
    public Result<FolderDto> create(@RequestBody CreateFolderDto dto) {

        var folder = Folder.create(dto.getUserId());
        folderRepository.save(folder);

        return Result.ok(new FolderDto(
                folder.getId(),
                folder.getUserId(),
                null));
    }

//    @GetMapping("/folders")
    public ResponseEntity<Envelope<List<FolderDto>>> listAll() {
        var folders = folderRepository.findAll();

        return ResponseEntity.ok(Envelope.ok(folders.stream().map(folder -> new FolderDto(
                folder.getId(),
                folder.getUserId(), null
        )).collect(Collectors.toList())));
    }

//    @GetMapping("/folders/{folderId}")
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

//    @PutMapping("/folders/{folderId}/documents")
    @Transactional
    public ResponseEntity<Envelope<DocumentDto>> createDocument(@PathVariable Long folderId,
                                                                @RequestBody CreateDocumentDto dto) {

        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
        }
        var folder = folderOptional.get();

        var createStrategyOptional = createDocumentStrategyFactory
                .findCreateStrategyByDocumentType(dto.getDocumentType());
        if (createStrategyOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Document Type not exists"));
        }

        var createStrategy = createStrategyOptional.get();
        var createDocumentResult = createStrategy.create(new CreateDocumentStrategyInput(folder.getId(), dto));
        if (createDocumentResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(createDocumentResult.getError()));
        }

        Document document = createDocumentResult.getValue();
        boolean documentAdded = folder.addDocument(document);

        if (!documentAdded) {
            var existingDocumentOptional = folder.findDocumentByDocumentTypeLimitPerFolder(document);
            if (existingDocumentOptional.isEmpty()) {
                return ResponseEntity.unprocessableEntity().body(Envelope.error("Unexpected"));
            }

            return ResponseEntity.ok(Envelope.ok(existingDocumentOptional.map(DocumentDto::new).get()));
        }

        return ResponseEntity.ok(Envelope.ok(new DocumentDto(document)));
    }

//    @PutMapping("/folders/{folderId}/documents/{documentId}")
    @Transactional
    public ResponseEntity<Envelope<DocumentDto>> updateDocument(@PathVariable Long folderId,
                                                                @PathVariable Long documentId,
                                                                @RequestBody UpdateDocumentDto dto) {

        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
        }
        var folder = folderOptional.get();

        var documentOptional = folder.findDocumentById(documentId);
        if (documentOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Document not exists"));
        }
        var document = documentOptional.get();

        var updateStrategyOptional = updateDocumentStrategyFactory
                .findUpdateStrategyByDocumentType(document.getDocumentType());
        if (updateStrategyOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Document Type not exists"));
        }
        var updateDocumentStrategy = updateStrategyOptional.get();

        var updateDocumentResult =
                updateDocumentStrategy.update(new UpdateDocumentStrategyInput(dto, document));
        if (updateDocumentResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(updateDocumentResult.getError()));
        }

        return ResponseEntity.ok(Envelope.ok(new DocumentDto(document)));
    }

//    @PutMapping("/folders/{folderId}/document-copies")
    public ResponseEntity<Envelope<DocumentCopyDto>> createDocumentCopy(@PathVariable Long folderId,
                                                                        @RequestParam("file") MultipartFile file) {
        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
        }

        var storeDocumentResult = documentCopyApplicationService
                .storeDocumentCopy(folderId, file.getOriginalFilename());

        if (storeDocumentResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(storeDocumentResult.getError()));
        }

        return ResponseEntity.ok(Envelope.ok(new DocumentCopyDto(file.getOriginalFilename())));
    }
}
