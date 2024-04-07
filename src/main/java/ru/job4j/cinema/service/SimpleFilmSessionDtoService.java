package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.FilmSessionRepository;
import ru.job4j.cinema.repository.GenreRepository;
import ru.job4j.cinema.repository.HallRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmSessionDtoService implements FilmSessionDtoService {

    private final FilmSessionRepository filmSessionRepository;

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    private final HallRepository hallRepository;

    public SimpleFilmSessionDtoService(FilmSessionRepository filmSessionRepository, FilmRepository filmRepository,
                                       GenreRepository genreRepository, HallRepository hallRepository) {
        this.filmSessionRepository = filmSessionRepository;
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
        this.hallRepository = hallRepository;
    }

    @Override
    public Optional<FilmSessionDto> create(int filmSessionId) {
        Optional<FilmSessionDto> filmSessionDto = Optional.empty();
        Optional<FilmSession> filmSession = filmSessionRepository.findById(filmSessionId);
        if (filmSession.isPresent()) {
            filmSessionDto = Optional.of(new FilmSessionDto());
            Optional<Film> film = filmRepository.findById(filmSession.orElseThrow().getFilmId());
            Optional<Genre> genre = genreRepository.findById(film.orElseThrow().getGenreId());
            Optional<Hall> hall = hallRepository.findById(filmSession.orElseThrow().getHallId());
            filmSessionDto.get().setId(filmSession.get().getId());
            filmSessionDto.get().setFilmName(film.get().getName());
            filmSessionDto.get().setFilmDescription(film.get().getDescription());
            filmSessionDto.get().setFilmYear(film.get().getYear());
            filmSessionDto.get().setFilmGenre(genre.orElseThrow().getName());
            filmSessionDto.get().setFilmMinimalAge(film.get().getMinimalAge());
            filmSessionDto.get().setFilmDurationInMinutes(film.get().getDurationInMinutes());
            filmSessionDto.get().setFilmFileId(film.get().getFileId());
            filmSessionDto.get().setHallName(hall.orElseThrow().getName());
            filmSessionDto.get().setHallRowCount(hall.get().getRowCount());
            filmSessionDto.get().setHallPlaceCount(hall.get().getPlaceCount());
            filmSessionDto.get().setHallDescription(hall.get().getDescription());
            filmSessionDto.get().setStartTime(filmSession.get().getStartTime());
            filmSessionDto.get().setEndTime(filmSession.get().getEndTime());
            filmSessionDto.get().setPrice(filmSession.get().getPrice());
        }
        return filmSessionDto;
    }

    @Override
    public Collection<FilmSessionDto> findAll() {
        Collection<FilmSession> filmSessions = filmSessionRepository.findAll();
        Collection<FilmSessionDto> filmSessionsDto = new ArrayList<>();
        for (FilmSession filmSession : filmSessions) {
            filmSessionsDto.add(create(filmSession.getId()).orElseThrow());
        }
        return filmSessionsDto;
    }
}
