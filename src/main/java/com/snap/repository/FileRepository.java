package com.snap.repository;

import com.snap.domain.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<BoardFile,Long> {
}
