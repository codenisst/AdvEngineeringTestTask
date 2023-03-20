package ru.codenisst.AdvEngineeringTestTask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.codenisst.AdvEngineeringTestTask.dao.TicketDao;

@Controller
public class MainController extends AbstractController {

    private final TicketDao ticketDao;

    @Autowired
    public MainController(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    @GetMapping("/")
    public String getStartPage(Model model) {
        model.addAttribute("ticketList", ticketDao.getAllUndeletedProjectByMainTicketId(0));
        model.addAttribute("role", getUserFromSession().getRole());
        return "ticketsList";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }
}
