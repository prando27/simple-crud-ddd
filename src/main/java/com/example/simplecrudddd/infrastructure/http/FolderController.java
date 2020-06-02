package com.example.simplecrudddd.infrastructure.http;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplecrudddd.application.folder.FolderApplicationService;
import com.example.simplecrudddd.application.folder.dto.FolderDto;
import com.example.simplecrudddd.application.folder.dto.create.CreateFolderDto;
import com.example.simplecrudddd.common.Envelope;
import com.example.simplecrudddd.common.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FolderController {

    private final FolderApplicationService folderApplicationService;
//
    @PostMapping("/folders")
    public ResponseEntity<Envelope<FolderDto>> create(CreateFolderDto dto) {

        Result<FolderDto> folderDtoResult = folderApplicationService.create(dto);

        if (folderDtoResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(""));
        }

        return ResponseEntity.ok(Envelope.ok(folderDtoResult.getValue()));
    }
//
//    @GetMapping("/folders")
//    public ResponseEntity<Envelope<List<FolderDto>>> listAll() {
//        var folders = folderRepository.findAll();
//
//        return ResponseEntity.ok(Envelope.ok(folders.stream().map(folder -> new FolderDto(
//                folder.getId(),
//                folder.getUserId(), null
//        )).collect(Collectors.toList())));
//    }
//
//    @GetMapping("/folders/{folderId}")
//    public ResponseEntity<Envelope<FolderDto>> findById(@PathVariable Long folderId) {
//        var folderOptional = folderRepository.findById(folderId);
//
//        if (folderOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok(Envelope.ok(folderOptional.map(folder -> new FolderDto(
//                folder.getId(),
//                folder.getUserId(),
//                folder.getDocuments().stream().map(DocumentDto::new).collect(Collectors.toList()))
//        ).get()));
//    }
//
//    @PutMapping("/folders/{folderId}/documents")
//    @Transactional
//    public ResponseEntity<Envelope<DocumentDto>> createDocument(@PathVariable Long folderId,
//                                                                @RequestBody CreateDocumentDto dto) {
//
//        var folderOptional = folderRepository.findById(folderId);
//        if (folderOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
//        }
//        var folder = folderOptional.get();
//
//        var createStrategyOptional = createDocumentStrategyFactory
//                .findCreateStrategyByDocumentType(dto.getDocumentType());
//        if (createStrategyOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(Envelope.error("Document Type not exists"));
//        }
//
//        var createStrategy = createStrategyOptional.get();
//        var createDocumentResult = createStrategy.create(new CreateDocumentStrategyInput(folder.getId(), dto));
//        if (createDocumentResult.isError()) {
//            return ResponseEntity.badRequest().body(Envelope.error(createDocumentResult.getError()));
//        }
//
//        Document document = createDocumentResult.getValue();
//        boolean documentAdded = folder.addDocument(document);
//
//        if (!documentAdded) {
//            var existingDocumentOptional = folder.findDocumentByDocumentTypeLimitPerFolder(document);
//            if (existingDocumentOptional.isEmpty()) {
//                return ResponseEntity.unprocessableEntity().body(Envelope.error("Unexpected"));
//            }
//
//            return ResponseEntity.ok(Envelope.ok(existingDocumentOptional.map(DocumentDto::new).get()));
//        }
//
//        return ResponseEntity.ok(Envelope.ok(new DocumentDto(document)));
//    }
//
//    @PutMapping("/folders/{folderId}/documents/{documentId}")
//    @Transactional
//    public ResponseEntity<Envelope<DocumentDto>> updateDocument(@PathVariable Long folderId,
//                                                                @PathVariable Long documentId,
//                                                                @RequestBody UpdateDocumentDto dto) {
//
//        var folderOptional = folderRepository.findById(folderId);
//        if (folderOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
//        }
//        var folder = folderOptional.get();
//
//        var documentOptional = folder.findDocumentById(documentId);
//        if (documentOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(Envelope.error("Document not exists"));
//        }
//        var document = documentOptional.get();
//
//        var updateStrategyOptional = updateDocumentStrategyFactory
//                .findUpdateStrategyByDocumentType(document.getDocumentType());
//        if (updateStrategyOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(Envelope.error("Document Type not exists"));
//        }
//        var updateDocumentStrategy = updateStrategyOptional.get();
//
//        var updateDocumentResult =
//                updateDocumentStrategy.update(new UpdateDocumentStrategyInput(dto, document));
//        if (updateDocumentResult.isError()) {
//            return ResponseEntity.badRequest().body(Envelope.error(updateDocumentResult.getError()));
//        }
//
//        return ResponseEntity.ok(Envelope.ok(new DocumentDto(document)));
//    }
//
//    @PutMapping("/folders/{folderId}/document-copies")
//    public ResponseEntity<Envelope<DocumentCopyDto>> createDocumentCopy(@PathVariable Long folderId,
//                                                                        @RequestParam("file") MultipartFile file) {
//        var folderOptional = folderRepository.findById(folderId);
//        if (folderOptional.isEmpty()) {
//            return ResponseEntity.badRequest().body(Envelope.error("Folder not exists"));
//        }
//
//        var storeDocumentResult = documentCopyApplicationService
//                .storeDocumentCopy(folderId, file.getOriginalFilename());
//
//        if (storeDocumentResult.isError()) {
//            return ResponseEntity.badRequest().body(Envelope.error(storeDocumentResult.getError()));
//        }
//
//        return ResponseEntity.ok(Envelope.ok(new DocumentCopyDto(file.getOriginalFilename())));
//    }
}
