package ru.codenisst.AdvEngineeringTestTask.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "TICKETS")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private String type;                // проект или задача
    private int mainTicketId;
    private String forType;            // тип - для менеджера или технического специалиста
    private String name;
    private String status;              // new, progress, done
    private String description;
    private String createDate;
    private String statusChangeDate;
    private boolean deleted;

    public Ticket() {
        this.status = "new";
        this.createDate = new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date());
        this.statusChangeDate = this.createDate;
        deleted = false;
    }

    public Ticket(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ticket(int id, int userId, String type, int mainTicket, String forType, String name, String status, String description, String createDate, String statusChangeDate, boolean isDeleted) {
        this.id = id;
        this.userId = userId;
        this.type = type;
        this.mainTicketId = mainTicket;
        this.forType = forType;
        this.name = name;
        this.status = status;
        this.description = description;
        this.createDate = createDate;
        this.statusChangeDate = statusChangeDate;
        this.deleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMainTicketId() {
        return mainTicketId;
    }

    public void setMainTicketId(int mainTicket) {
        this.mainTicketId = mainTicket;
    }

    public String getForType() {
        return forType;
    }

    public void setForType(String forType) {
        this.forType = forType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatusChangeDate() {
        return statusChangeDate;
    }

    public void setStatusChangeDate(String statusChangeDate) {
        this.statusChangeDate = statusChangeDate;
    }

    public void updateStatusChangeDate() {
        this.setStatusChangeDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").format(new Date()));
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && userId == ticket.userId && mainTicketId == ticket.mainTicketId && deleted == ticket.deleted && Objects.equals(type, ticket.type) && Objects.equals(forType, ticket.forType) && Objects.equals(name, ticket.name) && Objects.equals(status, ticket.status) && Objects.equals(description, ticket.description) && Objects.equals(createDate, ticket.createDate) && Objects.equals(statusChangeDate, ticket.statusChangeDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, type, mainTicketId, forType, name, status, description, createDate, statusChangeDate, deleted);
    }

    @Override
    public String toString() {
        return "Ticket{" + "\n" +
                "id=" + id + "\n" +
                ", userId=" + userId + "\n" +
                ", type='" + type + '\'' + "\n" +
                ", mainTicket=" + mainTicketId + "\n" +
                ", forType='" + forType + '\'' + "\n" +
                ", name='" + name + '\'' + "\n" +
                ", status='" + status + '\'' + "\n" +
                ", description='" + description + '\'' + "\n" +
                ", createDate=" + createDate + "\n" +
                ", statusChangeDate=" + statusChangeDate + "\n" +
                ", isDeleted=" + deleted + "\n" +
                '}';
    }
}
