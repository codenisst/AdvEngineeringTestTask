package ru.codenisst.AdvEngineeringTestTask.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.codenisst.AdvEngineeringTestTask.dao.TicketDao;
import ru.codenisst.AdvEngineeringTestTask.dao.UserDao;
import ru.codenisst.AdvEngineeringTestTask.models.Ticket;
import ru.codenisst.AdvEngineeringTestTask.util.TicketValidator;

import java.util.ArrayList;

@Controller
public class TicketController extends AbstractController {

    private final TicketValidator ticketValidator;
    private final TicketDao ticketDao;
    private final UserDao userDao;

    @Autowired
    public TicketController(TicketValidator ticketValidator, TicketDao ticketDao, UserDao userDao) {
        this.ticketValidator = ticketValidator;
        this.ticketDao = ticketDao;
        this.userDao = userDao;
    }

    @GetMapping("/{id}")
    public String getTicketDetailsById(@PathVariable("id") int id, Model model) {
        Ticket result = ticketDao.getById(id);

        checkIsNull(result);
        checkIsDeleted(result);

        ArrayList<Ticket> nestedTickets = ticketDao.getAllUndeletedTicketsByMainTicketId(result.getId());

        model.addAttribute("ticket", result);
        model.addAttribute("author", userDao.getNameById(result.getUserId()));
        model.addAttribute("nested", nestedTickets);
        model.addAttribute("user", getUserFromSession());
        return "ticketDetails";
    }

    @GetMapping("/new")
    public String createTicket(Model model) {
        if (!model.containsAttribute("ticket")) {
            model.addAttribute("ticket", new Ticket());
        }

        ArrayList<Ticket> ticketList = ticketDao.getAllUndeletedTickets();
        ticketList.add(0, new Ticket());
        model.addAttribute("ticketFromDB", ticketList);

        return "createTicket";
    }

    @PostMapping("/new")
    public String ticketCreationProcess(@ModelAttribute("ticket") @Valid Ticket ticket,
                                        BindingResult bindingResult, Model model) {
        checkAccessCreationTicket(ticket);

        ticketValidator.validate(ticket, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", ticket);
            return createTicket(model);
        }

        ticket.setUserId(getUserFromSession().getId());
        ticketDao.save(ticket);
        return "redirect:/";
    }

    @GetMapping("/{id}/edit")
    public String editTicket(@PathVariable("id") int id, Model model) {
        Ticket ticketFromDB = ticketDao.getById(id);

        checkAccessEditTicket(ticketFromDB);

        if (!model.containsAttribute("ticket")) {
            model.addAttribute("ticket", ticketFromDB);
        }

        return "editTicket";
    }

    @PostMapping("/{id}/edit")
    public String ticketChangeProcess(@PathVariable("id") int id,
                                      @ModelAttribute("ticket") @Valid Ticket ticket,
                                      BindingResult bindingResult, Model model) {
        Ticket updatedTicket = ticketDao.getById(id);

        checkAccessEditTicket(updatedTicket);

        if (!ticket.getStatus().equals(updatedTicket.getStatus())) {
            updatedTicket.setStatus(ticket.getStatus());
            updatedTicket.updateStatusChangeDate();
        }

        if (!ticket.getName().equals(updatedTicket.getName())) {
            updatedTicket.setName(ticket.getName());
        }

        if (!ticket.getSubscription().equals(updatedTicket.getSubscription())) {
            updatedTicket.setSubscription(ticket.getSubscription());
        }

        ticketValidator.validate(updatedTicket, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", ticket);
            return editTicket(id, model);
        }

        ticketDao.save(updatedTicket);

        return "redirect:/{id}";
    }

    @PostMapping("/{id}")
    public String deleteTicket(@PathVariable("id") int id) {
        Ticket deletedTicket = ticketDao.getById(id);

        checkAccessDeleteTicket(deletedTicket);

        int idTicketParent = deletedTicket.getMainTicketId();
        ArrayList<Ticket> listTicketDescendants = ticketDao.getAllUndeletedTicketsByMainTicketId(id);

        deletedTicket.setDeleted(true);
        ticketDao.save(deletedTicket);

        for (Ticket ticket : listTicketDescendants) {
            ticket.setMainTicketId(idTicketParent);
            ticketDao.save(ticket);
        }

        return "redirect:/";
    }

    @GetMapping("/all")
    public String getAllTickets(Model model) {
        checkAccess();

        model.addAttribute("tickets", ticketDao.getAllTickets());
        model.addAttribute("authors", userDao.getAllUsernames());

        return "allTickets";
    }

    @GetMapping("/my-tickets")
    public String getAllMyTickets(Model model) {

        model.addAttribute("tickets", ticketDao.getAllUndeletedTicketsByUserId(getUserFromSession().getId()));

        return "personalTicket";
    }
}
