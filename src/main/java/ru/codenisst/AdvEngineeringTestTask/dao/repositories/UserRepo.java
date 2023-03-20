package ru.codenisst.AdvEngineeringTestTask.dao.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.codenisst.AdvEngineeringTestTask.models.User;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {

    User findByLogin(String login);

    User findById(int id);

    @Override
    <S extends User> S save(S entity);

    @Override
    Iterable<User> findAll();
}
