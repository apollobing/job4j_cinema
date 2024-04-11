package ru.job4j.cinema.controller;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmSessionDtoService;
import ru.job4j.cinema.service.TicketService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class TicketController {

    private final FilmSessionDtoService filmSessionDtoService;

    private final TicketService ticketService;

    public TicketController(FilmSessionDtoService filmSessionDtoService, TicketService ticketService) {
        this.filmSessionDtoService = filmSessionDtoService;
        this.ticketService = ticketService;
    }

    @GetMapping({"/film-session/{id}/ticket/buy", "/film-session/{id}/ticket/buy/success"})
    public String buy(Model model, @PathVariable int id, HttpServletRequest request, HttpSession session) {
        var filmSessionDtoOptional = filmSessionDtoService.create(id);
        if (filmSessionDtoOptional.isEmpty()) {
            model.addAttribute("message", "Film session with this id not found");
            return "errors/404";
        }
        model.addAttribute("filmSession", filmSessionDtoOptional.get());
        Ticket ticket = (Ticket) session.getAttribute("ticket");
        session.removeAttribute("ticket");
        model.addAttribute("ticket", ticket != null ? ticket : new Ticket());
        return request.getRequestURI().contains("success") ? "tickets/success" : "tickets/buy";
    }

    @PostMapping("/film-session/{id}/ticket/buy")
    public String create(@ModelAttribute Ticket ticket, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        ticket.setUserId(user != null ? user.getId() : 0);
        boolean success = ticketService.save(ticket).isPresent();
        if (!success) {
            model.addAttribute("message", "Ticket to the same row and place has been bought."
                    + " Please choose another place.");
            return "errors/404";
        }
        session.setAttribute("ticket", ticket);
        return "redirect:/film-session/{id}/ticket/buy/success";
    }
}
