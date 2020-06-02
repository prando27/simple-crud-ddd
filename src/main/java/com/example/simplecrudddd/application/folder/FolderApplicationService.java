package com.example.simplecrudddd.application.folder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.simplecrudddd.application.DocumentCopyApplicationService;
import com.example.simplecrudddd.application.folder.dto.DocumentDto;
import com.example.simplecrudddd.application.folder.dto.FolderDto;
import com.example.simplecrudddd.application.folder.dto.create.CreateDocumentDto;
import com.example.simplecrudddd.application.folder.dto.create.CreateFolderDto;
import com.example.simplecrudddd.application.folder.dto.update.UpdateDocumentDto;
import com.example.simplecrudddd.application.folder.strategy.createdocument.CreateDocumentStrategyFactory;
import com.example.simplecrudddd.application.folder.strategy.createdocument.CreateDocumentStrategyInput;
import com.example.simplecrudddd.application.folder.strategy.updatedocument.UpdateDocumentStrategyFactory;
import com.example.simplecrudddd.application.folder.strategy.updatedocument.UpdateDocumentStrategyInput;
import com.example.simplecrudddd.common.Result;
import com.example.simplecrudddd.domain.folder.Folder;
import com.example.simplecrudddd.domain.folder.FolderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FolderApplicationService {

    private final FolderRepository folderRepository;

    private final DocumentCopyApplicationService documentCopyApplicationService;

    private final CreateDocumentStrategyFactory createDocumentStrategyFactory;

    private final UpdateDocumentStrategyFactory updateDocumentStrategyFactory;

    @Transactional
    public Result<FolderDto> create(CreateFolderDto dto) {

        var folder = Folder.create(dto.getUserId());
        folderRepository.save(folder);

        return Result.ok(new FolderDto(
                folder.getId(),
                folder.getUserId(),
                null));
    }

    public List<FolderDto> listAll() {
        var folders = folderRepository.findAll();

        return folders.stream().map(folder -> new FolderDto(
                folder.getId(),
                folder.getUserId(), null
        )).collect(Collectors.toList());
    }

    public Optional<FolderDto> findById(Long folderId) {
        var folderOptional = folderRepository.findById(folderId);

        return folderOptional.map(folder -> new FolderDto(
                folder.getId(),
                folder.getUserId(),
                folder.getDocuments()
                        .stream()
                        .map(DocumentDto::new)
                        .collect(Collectors.toList()))
        );
    }

    @Transactional
    public Result<DocumentDto> createDocument(Long folderId,
                                              CreateDocumentDto dto) {

        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return Result.error("Folder not exists");
        }
        var folder = folderOptional.get();

        var createStrategyOptional = createDocumentStrategyFactory
                .findCreateStrategyByDocumentType(dto.getDocumentType());
        if (createStrategyOptional.isEmpty()) {
            return Result.error("Document Type not exists");
        }

        var createStrategy = createStrategyOptional.get();
        var createDocumentResult = createStrategy.create(new CreateDocumentStrategyInput(folder.getId(), dto));
        if (createDocumentResult.isError()) {
            return Result.error(createDocumentResult.getError());
        }

        var document = createDocumentResult.getValue();
        var documentAdded = folder.addDocument(document);

        if (!documentAdded) {
            var existingDocumentOptional = folder.findDocumentByDocumentTypeLimitPerFolder(document);
            if (existingDocumentOptional.isEmpty()) {
                return Result.error("Document not found");
            }

            return Result.ok(existingDocumentOptional.map(DocumentDto::new).get());
        }

        return Result.ok(new DocumentDto(document));
    }

    @Transactional
    public Result<DocumentDto> updateDocument(Long folderId,
                                              Long documentId,
                                              UpdateDocumentDto dto) {

        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return Result.error("Folder not exists");
        }
        var folder = folderOptional.get();

        var documentOptional = folder.findDocumentById(documentId);
        if (documentOptional.isEmpty()) {
            return Result.error("Document not exists");
        }
        var document = documentOptional.get();

        var updateStrategyOptional = updateDocumentStrategyFactory
                .findUpdateStrategyByDocumentType(document.getDocumentType());
        if (updateStrategyOptional.isEmpty()) {
            return Result.error("Document Type not exists");
        }
        var updateDocumentStrategy = updateStrategyOptional.get();

        var updateDocumentResult =
                updateDocumentStrategy.update(new UpdateDocumentStrategyInput(dto, document));
        if (updateDocumentResult.isError()) {
            return Result.error(updateDocumentResult.getError());
        }

        return Result.ok(new DocumentDto(document));
    }

    @Transactional
    public Result<Boolean> removeDocument(Long folderId,
                                          Long documentId) {

        var folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isEmpty()) {
            return Result.error("Folder not exists");
        }
        var folder = folderOptional.get();

        boolean isRemoved = folder.removeDocument(documentId);

        return Result.ok(isRemoved);
    }

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
