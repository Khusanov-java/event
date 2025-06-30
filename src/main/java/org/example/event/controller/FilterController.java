package org.example.event.controller;

import lombok.RequiredArgsConstructor;
import org.example.event.entity.Event;
import org.example.event.service.interfaces.EventService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.event.utils.Util.FILTER_PATH;

@RestController
@RequestMapping(FILTER_PATH)
@RequiredArgsConstructor
public class FilterController {
    private final EventService eventService;

    @GetMapping
    public List<Event> filterEvents(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate filterDate,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        return eventService.filterEvents(categoryId, filterDate, minPrice, maxPrice);
    }



}