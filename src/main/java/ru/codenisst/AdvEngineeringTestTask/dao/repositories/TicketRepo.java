package ru.codenisst.AdvEngineeringTestTask.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.codenisst.AdvEngineeringTestTask.models.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepo extends CrudRepository<Ticket, Integer> {

    List<Ticket> findAllByMainTicketIdAndDeleted(int mainTicketId, boolean deleted);

    List<Ticket> findAllByTypeAndMainTicketIdAndDeleted(String type, int mainTicketId, boolean deleted);

    List<Ticket> findAllByDeleted(boolean deleted);

    List<Ticket> findAllByUserIdAndDeleted(int userId, boolean deleted);

    @Override
    <S extends Ticket> S save(S entity);

    @Override
    Iterable<Ticket> findAll();

    @Override
    Optional<Ticket> findById(Integer integer);

    @Override
    void deleteById(Integer integer);

    @Override
    void deleteAllById(Iterable<? extends Integer> integers);
}
