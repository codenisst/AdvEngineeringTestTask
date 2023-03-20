package ru.codenisst.AdvEngineeringTestTask.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.codenisst.AdvEngineeringTestTask.dao.UserDao;
import ru.codenisst.AdvEngineeringTestTask.models.User;

import java.util.ArrayList;

@Component
public class UserValidator implements Validator {

    private final ApplicationContext applicationContext;

    @Autowired
    public UserValidator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ArrayList<String> roles = new ArrayList<>();
        roles.add("admin");
        roles.add("user");
        User user = (User) target;
        UserDao userDao = applicationContext.getBean(UserDao.class);

        if (user.getLogin().equals("")) {
            errors.rejectValue("login", "", "Введи логин!");
        }
        if (user.getPassword().equals("")) {
            errors.rejectValue("password", "", "Введи пароль!");
        }
        if (!roles.contains(user.getRole())) {
            errors.rejectValue("role", "", "Введи корректную роль!");
        }
        if(userDao.containsInTheDatabase(user.getLogin())) {
            errors.rejectValue("login", "", "Такой логин уже есть!");
        }
    }
}
