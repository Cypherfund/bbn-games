package com.cypherfund.bbn.services.impl;

import com.cypherfund.bbn.dao.entity.*;
import com.cypherfund.bbn.dao.repository.*;
import com.cypherfund.bbn.dto.CategoryDto;
import com.cypherfund.bbn.dto.EventDto;
import com.cypherfund.bbn.dto.OutcomeDto;
import com.cypherfund.bbn.dto.TournamentDto;
import com.cypherfund.bbn.utils.Enumerations;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {
    private final GameRepository gameRepository;
    private final CategoryRepository categoryRepository;
    private final TournamentRepository tournamentRepository;
    private final EventRepository eventRepository;
    private final OutcomeRepository outcomeRepository;
    private final ModelMapper modelMapper;


    // Game management
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Game updateGame(Long id, Game gameDetails) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        game.setName(gameDetails.getName());
        return gameRepository.save(game);
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    // Category management
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setGame(gameRepository.findById(categoryDto.getGameId()).orElseThrow(() -> new RuntimeException("Game not found")));
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDetails) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(categoryDetails.getName());
        category.setGame(gameRepository.findById(categoryDetails.getGameId()).orElseThrow(() -> new RuntimeException("Game not found")));
        return modelMapper.map(categoryRepository.save(category), CategoryDto.class);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    // Tournament management
    public TournamentDto createTournament(TournamentDto tournamentDto) {
        Tournament tournament = new Tournament();
        tournament.setName(tournamentDto.getName());
        tournament.setStartDate(tournamentDto.getStartDate());
        tournament.setEndDate(tournamentDto.getEndDate());
        tournament.setCategory(categoryRepository.findById(tournamentDto.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
        return modelMapper.map(tournamentRepository.save(tournament), TournamentDto.class);
    }

    public TournamentDto updateTournament(Long id, TournamentDto tournamentDetails) {
        Tournament tournament = tournamentRepository.findById(id).orElseThrow(() -> new RuntimeException("Tournament not found"));
        tournament.setName(tournamentDetails.getName());
        tournament.setStartDate(tournamentDetails.getStartDate());
        tournament.setEndDate(tournamentDetails.getEndDate());
        tournament.setCategory(categoryRepository.findById(tournamentDetails.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found")));
        return modelMapper.map(tournamentRepository.save(tournament), TournamentDto.class);
    }

    public void deleteTournament(Long id) {
        tournamentRepository.deleteById(id);
    }

    // Event management
    public EventDto createEvent(EventDto eventDto) {
        Event event = new Event();
        event.setName(eventDto.getName());
        event.setEventDate(eventDto.getEventDate());
        event.setStatus(Enumerations.Event_Status.PENDING);
        event.setType(eventDto.getType());
        event.setTournament(tournamentRepository.findById(eventDto.getTournamentId()).orElseThrow(() -> new RuntimeException("Tournament not found")));
        return modelMapper.map(eventRepository.save(event), EventDto.class);
    }

    public EventDto updateEvent(Integer id, EventDto eventDetails) {
        Event event = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        event.setName(eventDetails.getName());
        event.setEventDate(eventDetails.getEventDate());
        event.setStatus(Enumerations.Event_Status.PENDING);
        event.setType(eventDetails.getType());
        event.setTournament(tournamentRepository.findById(eventDetails.getTournamentId()).orElseThrow(() -> new RuntimeException("Tournament not found")));
        return modelMapper.map(eventRepository.save(event), EventDto.class);
    }

    public void deleteEvent(Integer id) {
        eventRepository.deleteById(id);
    }

    // Outcome management
    public OutcomeDto createOutcome(OutcomeDto outcomeDto) {
        Outcome outcome = new Outcome();
        outcome.setDescription(outcomeDto.getDescription());
        outcome.setOdds(outcomeDto.getOdds());
        outcome.setEvent(eventRepository.findById(outcomeDto.getEventId()).orElseThrow(() -> new RuntimeException("Event not found")));
        return modelMapper.map(outcomeRepository.save(outcome), OutcomeDto.class);
    }

    public OutcomeDto updateOutcome(Long id, OutcomeDto outcomeDetails) {
        Outcome outcome = outcomeRepository.findById(id).orElseThrow(() -> new RuntimeException("Outcome not found"));
        outcome.setDescription(outcomeDetails.getDescription());
        outcome.setOdds(outcomeDetails.getOdds());
        return modelMapper.map(outcomeRepository.save(outcome), OutcomeDto.class);
    }

    public void deleteOutcome(Long id) {
        outcomeRepository.deleteById(id);
    }

    // Get events and outcomes by tournament
    public List<EventDto> getEventsByTournament(Long tournamentId) {
        return eventRepository.findByTournamentId(tournamentId).stream()
                .map((element) -> modelMapper.map(element, EventDto.class))
                .toList();
    }

    public List<OutcomeDto> getOutcomesByEvent(Long eventId) {
        return outcomeRepository.findByEventId(eventId).stream()
                .map((element) -> modelMapper.map(element, OutcomeDto.class))
                .toList();
    }

    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    public List<CategoryDto> gameCategories(long gameId) {
        return categoryRepository.findByGameId(gameId).stream()
                .map((element) -> modelMapper.map(element, CategoryDto.class))
                .toList();
    }
}

