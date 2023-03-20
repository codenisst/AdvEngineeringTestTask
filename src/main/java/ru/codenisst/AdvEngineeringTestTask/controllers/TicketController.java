package ru.codenisst.AdvEngineeringTestTask.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    @Async
    @GetMapping("/{id}")
    public CompletableFuture<String> getTicketDetailsById(@PathVariable("id") int id, Model model) {
        Ticket result = ticketDao.getById(id);

        checkIsNull(result);
        checkIsDeleted(result);

        ArrayList<Ticket> nestedTickets = ticketDao.getAllUndeletedTicketsByMainTicketId(result.getId());

        model.addAttribute("ticket", result);
        model.addAttribute("author", userDao.getNameById(result.getUserId()));
        model.addAttribute("nested", nestedTickets);
        model.addAttribute("user", getUserFromSession());
        return CompletableFuture.completedFuture("ticketDetails");
    }

    @Async
    @GetMapping("/new")
    public CompletableFuture<String> createTicket(Model model) {
        if (!model.containsAttribute("ticket")) {
            model.addAttribute("ticket", new Ticket());
        }

        ArrayList<Ticket> ticketList = ticketDao.getAllUndeletedTickets();
        ticketList.add(0, new Ticket());
        model.addAttribute("ticketFromDB", ticketList);

        return CompletableFuture.completedFuture("createTicket");
    }

    @Async
    @PostMapping("/new")
    public CompletableFuture<String> ticketCreationProcess(@ModelAttribute("ticket") @Valid Ticket ticket,
                                                           BindingResult bindingResult, Model model) throws ExecutionException, InterruptedException {
        checkAccessCreationTicket(ticket);

        ticketValidator.validate(ticket, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", ticket);
            return CompletableFuture.completedFuture(createTicket(model).get());
        }

        ticket.setUserId(getUserFromSession().getId());
        ticketDao.save(ticket);
        return CompletableFuture.completedFuture("redirect:/");
    }

    @Async
    @GetMapping("/{id}/edit")
    public CompletableFuture<String> editTicket(@PathVariable("id") int id, Model model) {
        Ticket ticketFromDB = ticketDao.getById(id);

        checkAccessEditTicket(ticketFromDB);

        if (!model.containsAttribute("ticket")) {
            model.addAttribute("ticket", ticketFromDB);
        }

        return CompletableFuture.completedFuture("editTicket");
    }

    @Async
    @PostMapping("/{id}/edit")
    public CompletableFuture<String> ticketChangeProcess(@PathVariable("id") int id,
                                                         @ModelAttribute("ticket") @Valid Ticket ticket,
                                                         BindingResult bindingResult, Model model) throws ExecutionException, InterruptedException {
        Ticket updatedTicket = ticketDao.getById(id);

        checkAccessEditTicket(updatedTicket);

        if (!ticket.getStatus().equals(updatedTicket.getStatus())) {
            updatedTicket.setStatus(ticket.getStatus());
            updatedTicket.updateStatusChangeDate();
        }

        if (!ticket.getName().equals(updatedTicket.getName())) {
            updatedTicket.setName(ticket.getName());
        }

        if (!ticket.getDescription().equals(updatedTicket.getDescription())) {
            updatedTicket.setDescription(ticket.getDescription());
        }

        ticketValidator.validate(updatedTicket, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("ticket", ticket);
            return CompletableFuture.completedFuture(editTicket(id, model).get());
        }

        ticketDao.save(updatedTicket);

        return CompletableFuture.completedFuture("redirect:/{id}");
    }

    @Async
    @PostMapping("/{id}")
    public CompletableFuture<String> deleteTicket(@PathVariable("id") int id) {
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

        return CompletableFuture.completedFuture("redirect:/");
    }

    @Async
    @GetMapping("/all")
    public CompletableFuture<String> getAllTickets(Model model) {
        checkAccess();

        model.addAttribute("tickets", ticketDao.getAllTickets());
        model.addAttribute("authors", userDao.getAllUsernames());

        return CompletableFuture.completedFuture("allTickets");
    }

    @Async
    @GetMapping("/my-tickets")
    public CompletableFuture<String> getAllMyTickets(Model model) {

        model.addAttribute("tickets", ticketDao.getAllUndeletedTicketsByUserId(getUserFromSession().getId()));

        return CompletableFuture.completedFuture("personalTicket");
    }
}
