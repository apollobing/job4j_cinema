package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionDtoService;
import ru.job4j.cinema.service.TicketService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TicketControllerTest {

    private FilmSessionDtoService filmSessionDtoService;

    private TicketService ticketService;

    private TicketController ticketController;

    @BeforeEach
    public void initServices() {
        filmSessionDtoService = mock(FilmSessionDtoService.class);
        ticketService = mock(TicketService.class);
        ticketController = new TicketController(filmSessionDtoService, ticketService);
    }

    @Test
    public void whenRequestBuyTicketPageThenGetPageWithFilmSessionDto() {
        var filmSession = new FilmSessionDto.Builder()
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
        when(filmSessionDtoService.create(filmSession.getId())).thenReturn(Optional.of(filmSession));

        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        var session = new MockHttpSession();
        var view = ticketController.buy(model, filmSession.getId(), request, session);
        var actualFilmSession = model.getAttribute("filmSession");

        assertThat(view).isEqualTo("tickets/buy");
        assertThat(actualFilmSession).isEqualTo(filmSession);
    }

    @Test
    public void whenRequestBuyPageAfterCreateTicketThenGetSuccessPageWithFilmSessionDtoAndTicketData() {
        var filmSession = new FilmSessionDto.Builder()
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
        var ticket = new Ticket(1, 3, 5, 7, 9);
        var user = new User(1, "Name", "email@email.com", "pass");
        when(filmSessionDtoService.create(filmSession.getId())).thenReturn(Optional.of(filmSession));

        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        request.setRequestURI("/film-session/" + filmSession.getId() + "/ticket/buy/success");
        var session = new MockHttpSession();
        session.setAttribute("user", user);
        session.setAttribute("ticket", ticket);
        var view = ticketController.buy(model, filmSession.getId(), request, session);
        var actualFilmSession = model.getAttribute("filmSession");
        var actualTicket = model.getAttribute("ticket");

        assertThat(view).isEqualTo("tickets/success");
        assertThat(actualFilmSession).isEqualTo(filmSession);
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenRequestBuyTicketPageWithWrongFilmSessionIdThenGet404Page() {
        var model = new ConcurrentModel();
        var request = new MockHttpServletRequest();
        var session = new MockHttpSession();
        var view = ticketController.buy(model, 5, request, session);
        var message = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(message).isEqualTo("Film session with this id not found");
    }

    @Test
    public void whenBuyTicketThenRedirectToSuccessPage() {
        var ticket = new Ticket(1, 3, 5, 7, 9);
        var user = new User(1, "Name", "email@email.com", "pass");
        when(ticketService.save(ticket)).thenReturn(Optional.of(ticket));

        var model = new ConcurrentModel();
        var session = new MockHttpSession();
        session.setAttribute("user", user);
        var view = ticketController.create(ticket, model, session);
        var actualTicket = session.getAttribute("ticket");

        assertThat(view).isEqualTo("redirect:/film-session/{id}/ticket/buy/success");
        assertThat(actualTicket).isEqualTo(ticket);
    }

    @Test
    public void whenBuySameTicketThenGet404Page() {
        var ticket = new Ticket(1, 3, 5, 7, 9);
        var user = new User(1, "Name", "email@email.com", "pass");
        when(ticketService.save(ticket)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var session = new MockHttpSession();
        session.setAttribute("user", user);
        var view = ticketController.create(ticket, model, session);
        var actualTicket = session.getAttribute("ticket");
        var message = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualTicket).isEqualTo(null);
        assertThat(message).isEqualTo("Ticket to the same row and place has been bought."
                + " Please choose another place.");
    }

    @Test
    public void whenSomeExceptionThrownThenGetErrorPageWithMessage() {
        var ticket = new Ticket(1, 3, 5, 7, 9);
        var user = new User(1, "Name", "email@email.com", "pass");
        var expectedException = new RuntimeException("Some exception");
        when(ticketService.save(any())).thenThrow(expectedException);

        var model = new ConcurrentModel();
        var session = new MockHttpSession();
        session.setAttribute("user", user);
        var view = ticketController.create(ticket, model, session);
        var actualExceptionMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualExceptionMessage).isEqualTo(expectedException.getMessage());
    }
}