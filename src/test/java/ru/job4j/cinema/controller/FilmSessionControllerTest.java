package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
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
        var filmSession1 = new FilmSessionDto.Builder()
                .buildId(1)
                .buildFilmName("FilmName1")
                .buildFilmDescription("FilmDescription1")
                .buildFilmYear(1999)
                .buildFilmGenre("FilmGenre1")
                .buildFilmMinimalAge(7)
                .buildFilmDurationInMinutes(90)
                .buildFilmFileId(1)
                .buildHallName("HallName1")
                .buildHallRowCount(5)
                .buildHallPlaceCount(10)
                .buildHallDescription("HallDescription1")
                .buildStartTime(LocalDateTime.now())
                .buildEndTime(LocalDateTime.now())
                .buildPrice(300)
                .build();
        var filmSession2 = new FilmSessionDto.Builder()
                .buildId(2)
                .buildFilmName("FilmName2")
                .buildFilmDescription("FilmDescription2")
                .buildFilmYear(2000)
                .buildFilmGenre("FilmGenre2")
                .buildFilmMinimalAge(17)
                .buildFilmDurationInMinutes(95)
                .buildFilmFileId(2)
                .buildHallName("HallName2")
                .buildHallRowCount(10)
                .buildHallPlaceCount(15)
                .buildHallDescription("HallDescription2")
                .buildStartTime(LocalDateTime.now())
                .buildEndTime(LocalDateTime.now())
                .buildPrice(400)
                .build();
        var expectedFilmSessions = List.of(filmSession1, filmSession2);
        when(filmSessionDtoService.findAll()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("filmSessions");

        assertThat(view).isEqualTo("film_sessions/list");
        assertThat(actualFilmSessions).isEqualTo(expectedFilmSessions);
    }
}