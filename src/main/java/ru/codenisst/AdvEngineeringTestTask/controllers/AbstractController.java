package ru.codenisst.AdvEngineeringTestTask.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;
import ru.codenisst.AdvEngineeringTestTask.models.Ticket;
import ru.codenisst.AdvEngineeringTestTask.models.User;
import ru.codenisst.AdvEngineeringTestTask.security.UserDetail;

public abstract class AbstractController {

    protected User getUserFromSession() {
        return ((UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
    }

    protected void checkAccess() {
        if (!getUserFromSession().getRole().equals("admin")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access is denied"
            );
        }
    }

    protected void checkAccessCreationTicket(Ticket ticket) {
        User user = getUserFromSession();
        if (ticket.getType().equals("Проект") & !user.getRole().equals("admin")) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access is denied"
            );
        }
    }

    protected void checkIsNull(Ticket ticket) {
        if (ticket == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    protected void checkIsNull(User user) {
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    protected void checkIsDeleted(Ticket ticket) {
        if (ticket.getDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
    }

    protected void checkAccessEditTicket(Ticket ticket) {
        User user = getUserFromSession();
        if (ticket == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found"
            );
        }
        if (!user.getRole().equals("admin") && user.getId() != ticket.getUserId()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access is denied"
            );
        }
    }

    protected void checkAccessDeleteTicket(Ticket ticket) {
        User user = getUserFromSession();
        if (!user.getRole().equals("admin") && user.getId() != ticket.getUserId()) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Access is denied"
            );
        }
    }
}
