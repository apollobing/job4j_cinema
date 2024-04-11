package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.dto.HallDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.FilmSessionDtoService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilmSessionControllerTest {

    private FilmSessionDtoService filmSessionDtoService;

    private FilmSessionController filmSessionController;

    @BeforeEach
    public void initServices() {
        filmSessionDtoService = mock(FilmSessionDtoService.class);
        filmSessionController = new FilmSessionController(filmSessionDtoService);
    }

    @Test
    public void whenRequestFilmSessionsListPageThenGetPageWithFilmSessions() {
        var film1 = new Film.Builder()
                .buildName("FilmName1")
                .buildDescription("FilmDescription1")
                .buildYear(1999)
                .buildGenreId(1)
                .buildMinimalAge(7)
                .buildDurationInMinutes(90)
                .buildFileId(1)
                .build();
        var genre1 = new Genre(1, "FilmGenre1");
        var hall1 = new Hall(1, "HallName1", 5, 10, "HallDescription1");
        var filmSession1 = new FilmSessionDto(1, new FilmDto(film1, genre1), new HallDto(hall1),
                LocalDateTime.now(), LocalDateTime.now(), 300);

        var film2 = new Film.Builder()
                .buildName("FilmName2")
                .buildDescription("FilmDescription2")
                .buildYear(2000)
                .buildGenreId(2)
                .buildMinimalAge(17)
                .buildDurationInMinutes(95)
                .buildFileId(2)
                .build();
        var genre2 = new Genre(2, "FilmGenre2");
        var hall2 = new Hall(2, "HallName2", 10, 15, "HallDescription2");
        var filmSession2 = new FilmSessionDto(1, new FilmDto(film2, genre2), new HallDto(hall2),
                LocalDateTime.now(), LocalDateTime.now(), 400);

        var expectedFilmSessions = List.of(filmSession1, filmSession2);
        when(filmSessionDtoService.findAll()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("film_sessions/list");
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessions);
    }
}