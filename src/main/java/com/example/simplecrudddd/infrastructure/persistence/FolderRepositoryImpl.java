package com.example.simplecrudddd.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.simplecrudddd.domain.folder.entity.Folder;
import com.example.simplecrudddd.domain.folder.repository.FolderRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository
class FolderRepositoryImpl implements FolderRepository {

    private final FolderJpaRepository folderJpaRepository;

    @Override
    public void save(Folder folder) {
        folderJpaRepository.save(folder);
    }

    @Override
    public List<Folder> findAll() {
        return folderJpaRepository.findAll();
    }

    @Override
    public Optional<Folder> findById(Long id) {
        return folderJpaRepository.findById(id);
    }
}

interface FolderJpaRepository extends JpaRepository<Folder, Long> {
}
