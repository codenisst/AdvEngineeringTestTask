package ru.codenisst.AdvEngineeringTestTask.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.codenisst.AdvEngineeringTestTask.models.Ticket;

import java.util.ArrayList;

@Component
public class TicketValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Ticket.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArrayList<String> status = new ArrayList<>();
        status.add("new");
        status.add("progress");
        status.add("done");
        Ticket ticket = (Ticket) target;

        if (ticket.getType().equals("Задача") && ticket.getMainTicketId() == 0) {
            errors.rejectValue("type", "", "Задача должна быть привязана к проекту/задаче!");
        }
        if (ticket.getName().equals("")) {
            errors.rejectValue("name", "", "Введи название!");
        }
        if (ticket.getDescription().equals("")) {
            errors.rejectValue("description", "", "Введи описание!");
        }
        if (!status.contains(ticket.getStatus())) {
            errors.rejectValue("status", "", "Введи корректный статус!");
        }
    }
}
