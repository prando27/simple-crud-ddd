package com.example.simplecrudddd.domain.folder.repository;

import java.util.List;
import java.util.Optional;

import com.example.simplecrudddd.domain.folder.entity.Folder;

public interface FolderRepository {

    void save(Folder folder);

    List<Folder> findAll();

    Optional<Folder> findById(Long id);
}
