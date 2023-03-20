package ru.codenisst.AdvEngineeringTestTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.codenisst.AdvEngineeringTestTask.dao.TicketDao;

import java.util.concurrent.CompletableFuture;

@Controller
public class MainController extends AbstractController {

    private final TicketDao ticketDao;

    @Autowired
    public MainController(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Async
    @GetMapping("/")
    public CompletableFuture<String> getStartPage(Model model) {
        model.addAttribute("ticketList", ticketDao.getAllUndeletedProjectByMainTicketId(0));
        model.addAttribute("role", getUserFromSession().getRole());
        return CompletableFuture.completedFuture("ticketsList");
    }

    @Async
    @GetMapping("/logout")
    public CompletableFuture<String> logout() {
        return CompletableFuture.completedFuture("redirect:/");
    }
}
