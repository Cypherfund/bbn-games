package com.cypherfund.bbn.controller;

/**
 * Author: E.Ngai
 * Date: 6/19/2024
 * Time: 1:23 PM
 **/
import com.cypherfund.bbn.dao.entity.Game;
import com.cypherfund.bbn.dto.CategoryDto;
import com.cypherfund.bbn.dto.EventDto;
import com.cypherfund.bbn.dto.OutcomeDto;
import com.cypherfund.bbn.dto.TournamentDto;
import com.cypherfund.bbn.models.CreateOutcomeRequest;
import com.cypherfund.bbn.services.impl.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    // Game endpoints
    @PostMapping("/games")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        return ResponseEntity.ok(adminService.createGame(game));
    }

    @PutMapping("/games/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        return ResponseEntity.ok(adminService.updateGame(id, gameDetails));
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGames(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(adminService.getGames());
    }
    @DeleteMapping("/games/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        adminService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/games/{gameId}/categories")
    public ResponseEntity<List<CategoryDto>> getCategoriesByGame(@PathVariable Long gameId) {
        return ResponseEntity.ok(adminService.gameCategories(gameId));
    }

    // Category endpoints
    @PostMapping("/categories")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto category) {
        return ResponseEntity.ok(adminService.createCategory(category));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryDto categoryDetails) {
        return ResponseEntity.ok(adminService.updateCategory(id, categoryDetails));
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        adminService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    // Tournament endpoints
    @PostMapping("/tournaments")
    public ResponseEntity<TournamentDto> createTournament(@RequestBody @Valid TournamentDto tournament) {
        return ResponseEntity.ok(adminService.createTournament(tournament));
    }

    @PutMapping("/tournaments/{id}")
    public ResponseEntity<TournamentDto> updateTournament(@PathVariable Long id, @RequestBody @Valid TournamentDto tournamentDetails) {
        return ResponseEntity.ok(adminService.updateTournament(id, tournamentDetails));
    }

    @DeleteMapping("/tournaments/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        adminService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }

    // Event endpoints
    @PostMapping("/events")
    public ResponseEntity<EventDto> createEvent(@RequestBody EventDto event) {
        return ResponseEntity.ok(adminService.createEvent(event));
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Integer id, @RequestBody EventDto eventDetails) {
        return ResponseEntity.ok(adminService.updateEvent(id, eventDetails));
    }

    @DeleteMapping("/events/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Integer id) {
        adminService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    // Outcome endpoints
    @PostMapping("/outcomes")
    public ResponseEntity<OutcomeDto> createOutcome(@RequestBody @Valid CreateOutcomeRequest outcomeRequest) {
        return ResponseEntity.ok(adminService.createOutcome(outcomeRequest));
    }

    @PutMapping("/outcomes/{id}")
    public ResponseEntity<OutcomeDto> updateOutcome(@PathVariable Long id, @RequestBody @Valid OutcomeDto outcomeDetails) {
        return ResponseEntity.ok(adminService.updateOutcome(id, outcomeDetails));
    }

    @DeleteMapping("/outcomes/{id}")
    public ResponseEntity<Void> deleteOutcome(@PathVariable Long id) {
        adminService.deleteOutcome(id);
        return ResponseEntity.noContent().build();
    }

    // Get events and outcomes
    @GetMapping("/tournaments/{tournamentId}/events")
    public ResponseEntity<List<EventDto>> getEventsByTournament(@PathVariable Long tournamentId) {
        return ResponseEntity.ok(adminService.getEventsByTournament(tournamentId));
    }

    @GetMapping("/events/{eventId}/outcomes")
    public ResponseEntity<List<OutcomeDto>> getOutcomesByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(adminService.getOutcomesByEvent(eventId));
    }
}
