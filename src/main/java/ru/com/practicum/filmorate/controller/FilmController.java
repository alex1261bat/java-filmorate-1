package ru.com.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.com.practicum.filmorate.exception.NotFoundException;
import ru.com.practicum.filmorate.exception.ValidationException;
import ru.com.practicum.filmorate.model.*;
import ru.com.practicum.filmorate.service.FeedService;
import ru.com.practicum.filmorate.service.FilmService;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmService filmService;
    private final FeedService feedService;

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmService.getAll();
    }

    @GetMapping("/films/{id}")
    public Film findById(@PathVariable Long id) {
        return filmService.getById(id);
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) throws ValidationException {
        return filmService.add(film);
    }

    @PutMapping(value = "/films")
    public Film update(@RequestBody Film film) throws ValidationException, NotFoundException {
        return filmService.update(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
        Event event = Event.builder()
                .timestamp(System.currentTimeMillis())
                .userId(userId)
                .eventType(EventTypes.LIKE)
                .operation(OperationTypes.ADD)
                .entityId(id)
                .eventId(0L)
                .build();
        feedService.addEvent(event);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void removeLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.removeLike(id, userId);
        Event event = Event.builder()
                .timestamp(System.currentTimeMillis())
                .userId(userId)
                .eventType(EventTypes.LIKE)
                .operation(OperationTypes.REMOVE)
                .entityId(id)
                .eventId(0L)
                .build();
        feedService.addEvent(event);
    }

    @GetMapping(value = "/films/popular")
    public List<Film> getTop(@RequestParam(defaultValue = "10", required = false) Integer count,
                             @RequestParam(required = false) Long genreId,
                             @RequestParam(required = false) Integer year) {
        return filmService.getTop(count, genreId, year);
    }

    @GetMapping(value = "/films/common")
    public List<Film> getCommonFilms(
            @RequestParam long userId,
            @RequestParam long friendId){
        return filmService.getCommonFilms(userId, friendId);
    }

    @DeleteMapping(value = "/films/{filmId}")
    public void deleteFilm(@PathVariable Long filmId) {
        filmService.deleteFilm(filmId);
    }

    @GetMapping(value = "/films/director/{directorId}")
    public List<Film> getFilmsByDirectorId(@PathVariable Long directorId,
                                           @RequestParam String sortBy) throws NotFoundException {
        return filmService.getFilmsByDirectorId(directorId, sortBy);
    }

    @GetMapping(value = "/films/search")
    public List<Film> searchFilms(@RequestParam(name = "query") String query, @RequestParam(name = "by") String by) {
       return filmService.searchFilms(query, by);
    }

}
