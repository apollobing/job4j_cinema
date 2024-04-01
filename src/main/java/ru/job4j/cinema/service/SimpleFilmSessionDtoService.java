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
        FilmSessionDto filmSessionDto = new FilmSessionDto();
        Optional<FilmSession> filmSession = filmSessionRepository.findById(filmSessionId);
        Optional<Film> film = filmRepository.findById(filmSession.orElseThrow().getFilmId());
        Optional<Genre> genre = genreRepository.findById(film.orElseThrow().getGenreId());
        Optional<Hall> hall = hallRepository.findById(filmSession.orElseThrow().getHallId());
        filmSessionDto.setId(filmSession.orElseThrow().getId());
        filmSessionDto.setFilmName(film.orElseThrow().getName());
        filmSessionDto.setFilmDescription(film.orElseThrow().getDescription());
        filmSessionDto.setFilmYear(film.orElseThrow().getYear());
        filmSessionDto.setFilmGenre(genre.orElseThrow().getName());
        filmSessionDto.setFilmMinimalAge(film.orElseThrow().getMinimalAge());
        filmSessionDto.setFilmDurationInMinutes(film.orElseThrow().getDurationInMinutes());
        filmSessionDto.setFilmFileId(film.orElseThrow().getFileId());
        filmSessionDto.setHallName(hall.orElseThrow().getName());
        filmSessionDto.setHallRowCount(hall.orElseThrow().getRowCount());
        filmSessionDto.setHallPlaceCount(hall.orElseThrow().getPlaceCount());
        filmSessionDto.setHallDescription(hall.orElseThrow().getDescription());
        filmSessionDto.setStartTime(filmSession.orElseThrow().getStartTime());
        filmSessionDto.setEndTime(filmSession.orElseThrow().getEndTime());
        filmSessionDto.setPrice(filmSession.orElseThrow().getPrice());
        return Optional.of(filmSessionDto);
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
