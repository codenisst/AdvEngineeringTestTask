package ru.codenisst.AdvEngineeringTestTask.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.codenisst.AdvEngineeringTestTask.dao.repositories.UserRepo;
import ru.codenisst.AdvEngineeringTestTask.models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDao {

    private final UserRepo userRepo;

    @Autowired
    public UserDao(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public String getNameById(int id) {
        return userRepo.findById(id).getLogin();
    }

    public User getById(int id) {
        return userRepo.findById(id);
    }

    public boolean containsInTheDatabase(String login) {
        return userRepo.findByLogin(login) != null;
    }

    public Map<Integer, String> getAllUsernames() {
        ArrayList<User> users = (ArrayList<User>) userRepo.findAll();
        Map<Integer, String> idUsername = new HashMap<>();

        for (User user : users) {
            idUsername.put(user.getId(), user.getLogin());
        }
        return idUsername;
    }

    public List<User> getAllUsers() {
        return ((ArrayList<User>) userRepo.findAll()).stream().sorted(Comparator.comparing(User::getRole)).toList();
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
