package com.javacode.jwt_security.controller;

import com.javacode.jwt_security.model.Painting;
import com.javacode.jwt_security.service.PaintingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @Secured({"ADMIN", "MODERATOR"})
    @PostMapping("/api/painting")
    public ResponseEntity<Object> addPainting(@RequestBody Painting painting) {
        return new ResponseEntity<>(paintingService.save(painting), HttpStatus.CREATED);
    }

    @GetMapping("/api/painting/{id}")
    public ResponseEntity<Object> getPaintingById(@PathVariable long id) {
        return new ResponseEntity<>(paintingService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/api/painting/list")
    public ResponseEntity<Object> getAllPaintings() {
        return new ResponseEntity<>(paintingService.findAll(), HttpStatus.OK);
    }

}
