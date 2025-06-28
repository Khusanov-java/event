package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.dtos.res.HomeDTO;
import org.example.event.service.interfaces.EventService;
import org.example.event.service.interfaces.ReviewService;
import org.example.event.service.interfaces.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomeController {

    private final EventService eventService;
    private final UserService userService;
    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<HomeDTO> getHomeData() {
        HomeDTO homeResponse = new HomeDTO();
        homeResponse.setLatestEvents(eventService.getLatestEvents());
        homeResponse.setTopOrganizers(userService.getTopOrganizers());
        homeResponse.setRecentReviews(reviewService.getRecentReviews());
        return ResponseEntity.ok(homeResponse);
    }
}
