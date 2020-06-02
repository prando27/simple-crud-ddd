package com.example.simplecrudddd.infrastructure.http;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.simplecrudddd.application.folder.FolderApplicationService;
import com.example.simplecrudddd.application.folder.dto.DocumentDto;
import com.example.simplecrudddd.application.folder.dto.FolderDto;
import com.example.simplecrudddd.application.folder.dto.create.CreateDocumentDto;
import com.example.simplecrudddd.application.folder.dto.create.CreateFolderDto;
import com.example.simplecrudddd.application.folder.dto.update.UpdateDocumentDto;
import com.example.simplecrudddd.common.Envelope;
import com.example.simplecrudddd.common.Result;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FolderController {

    private final FolderApplicationService folderApplicationService;

    @PostMapping("/folders")
    public ResponseEntity<Envelope<FolderDto>> create(@RequestBody CreateFolderDto dto) {

        Result<FolderDto> folderDtoResult = folderApplicationService.create(dto);

        if (folderDtoResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(folderDtoResult.getError()));
        }

        return ResponseEntity.ok(Envelope.ok(folderDtoResult.getValue()));
    }

    @GetMapping("/folders")
    public ResponseEntity<Envelope<List<FolderDto>>> listAll() {
        return ResponseEntity.ok(
                Envelope.ok(folderApplicationService.listAll()));
    }

    @GetMapping("/folders/{folderId}")
    public ResponseEntity<Envelope<FolderDto>> findById(@PathVariable Long folderId) {

        Optional<FolderDto> folderDtoOptional = folderApplicationService.findById(folderId);
        if (folderDtoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(Envelope.ok(folderDtoOptional.get()));
    }

    @PutMapping("/folders/{folderId}/documents")
    public ResponseEntity<Envelope<DocumentDto>> createDocument(@PathVariable Long folderId,
                                                                @RequestBody CreateDocumentDto dto) {

        Result<DocumentDto> createDocumentResult = folderApplicationService.createDocument(folderId, dto);

        if (createDocumentResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(createDocumentResult.getError()));
        }

        return ResponseEntity.ok(Envelope.ok(createDocumentResult.getValue()));
    }

    @PutMapping("/folders/{folderId}/documents/{documentId}")
    public ResponseEntity<Envelope<DocumentDto>> updateDocument(@PathVariable Long folderId,
                                                                @PathVariable Long documentId,
                                                                @RequestBody UpdateDocumentDto dto) {
        Result<DocumentDto> updateDocumentResult = folderApplicationService
                .updateDocument(folderId, documentId, dto);

        if (updateDocumentResult.isError()) {
            return ResponseEntity.badRequest().body(Envelope.error(updateDocumentResult.getError()));
        }

        return ResponseEntity.ok(Envelope.ok(updateDocumentResult.getValue()));
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
