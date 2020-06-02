package com.example.simplecrudddd.domain.folder;

import java.util.List;
import java.util.Optional;

public interface FolderRepository {

    void save(Folder folder);

    List<Folder> findAll();

    Optional<Folder> findById(Long id);
}
