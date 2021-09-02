package com.sutorimingu.no.sekai.repository;

import com.sutorimingu.no.sekai.model.FileDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ufhopla
 * on 07/08/2021.
 */

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {

}
