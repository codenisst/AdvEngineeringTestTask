package ru.codenisst.AdvEngineeringTestTask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codenisst.AdvEngineeringTestTask.dao.repositories.TicketRepo;
import ru.codenisst.AdvEngineeringTestTask.models.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TicketDao {

    private final TicketRepo ticketRepo;

    @Autowired
    public TicketDao(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public List<Ticket> getAllTickets() {
        return ((ArrayList<Ticket>) ticketRepo.findAll())
                .stream()
                .sorted((o1, o2) -> Boolean.compare(o1.getDeleted(), o2.getDeleted()))
                .toList();
    }

    public ArrayList<Ticket> getAllUndeletedTickets() {
        ArrayList<Ticket> resultList = (ArrayList<Ticket>) ticketRepo.findAllByDeleted(false);
        resultList.removeIf(ticket -> ticket.getType().equals("Задача") & ticket.getMainTicketId() == 0);
        return resultList;
    }

    public ArrayList<Ticket> getAllUndeletedProjectByMainTicketId(int mainTicketId) {
        return (ArrayList<Ticket>) ticketRepo.findAllByTypeAndMainTicketIdAndDeleted("Проект", mainTicketId, false);
    }

    public ArrayList<Ticket> getAllUndeletedTicketsByMainTicketId(int mainTicketId) {
        return (ArrayList<Ticket>) ticketRepo.findAllByMainTicketIdAndDeleted(mainTicketId, false);
    }

    public ArrayList<Ticket> getAllUndeletedTicketsByUserId(int userId) {
        return (ArrayList<Ticket>) ticketRepo.findAllByUserIdAndDeleted(userId, false);
    }

    public void save(Ticket ticket) {
        ticketRepo.save(ticket);
    }

    public Ticket getById(int id) {
        Optional<Ticket> result = ticketRepo.findById(id);
        return result.orElse(null);
    }
}
