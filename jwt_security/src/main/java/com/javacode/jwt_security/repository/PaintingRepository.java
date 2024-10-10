package com.javacode.jwt_security.repository;

import com.javacode.jwt_security.model.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
