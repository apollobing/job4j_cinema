package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FilmRepository;
import ru.job4j.cinema.repository.GenreRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class SimpleFilmDtoService implements FilmDtoService {

    private final FilmRepository filmRepository;

    private final GenreRepository genreRepository;

    public SimpleFilmDtoService(FilmRepository filmRepository, GenreRepository genreRepository) {
        this.filmRepository = filmRepository;
        this.genreRepository = genreRepository;
    }

    @Override
    public Optional<FilmDto> create(int filmId) {
        FilmDto filmDto = new FilmDto();
        Optional<Film> film = filmRepository.findById(filmId);
        Optional<Genre> genre = genreRepository.findById(film.orElseThrow().getGenreId());
        filmDto.setId(film.orElseThrow().getId());
        filmDto.setName(film.orElseThrow().getName());
        filmDto.setDescription(film.orElseThrow().getDescription());
        filmDto.setYear(film.orElseThrow().getYear());
        filmDto.setGenre(genre.orElseThrow().getName());
        filmDto.setMinimalAge(film.orElseThrow().getMinimalAge());
        filmDto.setDurationInMinutes(film.orElseThrow().getDurationInMinutes());
        filmDto.setFileId(film.orElseThrow().getFileId());
        return Optional.of(filmDto);
    }

    @Override
    public Collection<FilmDto> findAll() {
        Collection<Film> films = filmRepository.findAll();
        Collection<FilmDto> filmsDto = new ArrayList<>();
        for (Film film : films) {
            filmsDto.add(create(film.getId()).orElseThrow());
        }
        return filmsDto;
    }
}
