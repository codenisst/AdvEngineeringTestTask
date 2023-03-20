package ru.codenisst.AdvEngineeringTestTask.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.codenisst.AdvEngineeringTestTask.dao.UserDao;
import ru.codenisst.AdvEngineeringTestTask.models.User;
import ru.codenisst.AdvEngineeringTestTask.util.UserValidator;

@Controller
@RequestMapping("/users")
public class UserController extends AbstractController {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserDao userDao, PasswordEncoder passwordEncoder, UserValidator userValidator) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @GetMapping
    public String getUsersList(Model model) {
        checkAccess();

        model.addAttribute("users", userDao.getAllUsers());
        return "usersList";
    }

    @GetMapping("/{id}")
    public String getUserDetails(@PathVariable("id") int id, Model model) {
        checkAccess();

        User user = userDao.getById(id);
        checkIsNull(user);

        model.addAttribute("user", user);
        return "userDetails";
    }

    @GetMapping("/new")
    public String createNewUser(Model model) {
        checkAccess();

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new User());
        }

        return "createUser";
    }

    @PostMapping("/new")
    public String newUserCreationProcess(@ModelAttribute("user") @Valid User user,
                                         BindingResult bindingResult, Model model) {
        checkAccess();

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("user", user);
            return createNewUser(model);
        }

        userDao.save(user);
        return "redirect:/users";
    }

    @PostMapping("/{id}")
    public String bannedUser(@PathVariable("id") int id) {
        checkAccess();

        User user = userDao.getById(id);

        checkIsNull(user);

        user.setBanned(!user.getBanned());

        userDao.save(user);

        return "redirect:/users/{id}";
    }
}
