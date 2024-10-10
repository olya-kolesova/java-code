package com.javacode.jwt_security.service;

import com.javacode.jwt_security.model.Painting;
import com.javacode.jwt_security.repository.PaintingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;

    public PaintingService(PaintingRepository paintingRepository) {
        this.paintingRepository = paintingRepository;
    }

    public Painting save(Painting painting) {
        return paintingRepository.save(painting);
    }

    public List<Painting> findAll() {
        return paintingRepository.findAll();
    }

    public Painting findById(long id) throws NoSuchElementException {
        return paintingRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }


}
