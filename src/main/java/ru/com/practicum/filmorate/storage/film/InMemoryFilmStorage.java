package ru.com.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.com.practicum.filmorate.exception.NotFoundException;
import ru.com.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("inMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    private Long currId = 0L;
    private final Map<Long, Film> films = new HashMap<>();

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film getById(Long id) throws NotFoundException {
        Film film = films.get(id);
        if (film == null) {
            throw new NotFoundException("Фильм с id=" + id + " несуществует");
        }
        return film;
    }

    @Override
    public Film add(Film film) {
        film.setId(++currId);
        films.put(currId, film);
        log.info("Фильм с id={} создан", film.getId());
        return film;
    }

    @Override
    public Film update(Film film) throws NotFoundException {
        if (films.containsKey(film.getId())) {
            films.put(film.getId(), film);
            log.info("Фильм с id={} обновлен", film.getId());
        } else {
            throw new NotFoundException("Фильм с id=" + film.getId() + " несуществует");
        }
        return film;
    }

    @Override
    public void delete(Film film) {
        films.remove(film.getId());
        log.info("Фильм с id={} удален", film.getId());
    }

    @Override
    public void addLike(Long id, Long userId) {

    }

    @Override
    public void removeLike(Long id, Long userId) {

    }

    @Override
    public boolean hasLikeFromUser(Long id, Long userId) {
        return false;
    }

    @Override
    public List<Film> getTop(Integer count) {
        return new ArrayList<>();
    }

}
