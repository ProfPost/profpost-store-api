package com.Profpost.repository;

import com.Profpost.model.entity.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Integer> {

    boolean existsByName(String name);

    //Metodo para verificar si ya existe un lector con el mismo nombre y apellido, excepto el usuario actual
    boolean existsByNameAndUserIdNot(String name, Integer userId);
}
