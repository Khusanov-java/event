package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.req.ReviewDTO;
import org.example.event.dtos.res.ReviewResDTO;
import org.example.event.entity.Review;
import org.example.event.service.interfaces.ReviewService;
import org.example.event.utils.Util;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.sax.SAXResult;
import java.util.List;

@RestController
@RequestMapping(Util.REVIEW_PATH)
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> create(@RequestBody ReviewDTO dto) {
        return ResponseEntity.ok(reviewService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> update(@PathVariable Long id, @RequestBody ReviewDTO dto) {
        return ResponseEntity.ok(reviewService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(reviewService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAll() {
        return ResponseEntity.ok(reviewService.getAll());
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<Review>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(reviewService.getByEvent(eventId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Review>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(reviewService.getByUser(userId));
    }
}
