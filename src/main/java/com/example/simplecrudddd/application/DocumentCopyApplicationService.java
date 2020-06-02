package com.example.simplecrudddd.application;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

import com.example.simplecrudddd.application.folder.dto.DocumentCopyDto;
import com.example.simplecrudddd.common.Result;

@Service
public class DocumentCopyApplicationService {

    private final ConcurrentMap<Long, Set<String>> S3 = new ConcurrentHashMap<>();

    public Result<DocumentCopyDto> storeDocumentCopy(Long folderId,
                                                     String fileName) {
        Set<String> documentCopies = S3.getOrDefault(folderId, new HashSet<>());

        if (!documentCopies.add(fileName)) {
            return Result.error("File already exists");
        }

        S3.put(folderId, documentCopies);

        return Result.ok(new DocumentCopyDto(fileName));
    }

    public Optional<DocumentCopyDto> findDocumentCopy(Long folderId, String fileName) {
        Set<String> strings = S3.get(folderId);

        return strings.stream().filter(fileName::equals).findFirst().map(DocumentCopyDto::new);

    }
}
