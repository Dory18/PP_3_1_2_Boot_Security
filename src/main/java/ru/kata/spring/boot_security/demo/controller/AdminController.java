package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping()
    public String getAllUser(ModelMap model) {
        model.addAttribute("userList", userService.findAll());
        return "user_page/users";
    }

    @GetMapping("/user")
    public String getUserById(@RequestParam(value = "id") Long id,
                              ModelMap model) {

        model.addAttribute("user", userService.findById(id));
        return "user_page/user";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam(value = "id") Long id,
                           ModelMap model) {
        model.addAttribute("user", userService.findById(id));
        return "user_page/edit";
    }

    @PostMapping("/edit")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "user_page/edit";
        }
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/new")
    public String createUser(@ModelAttribute("user") User user,
                             Model model) {
        model.addAttribute("allRoles", roleService.findAll());
        return "user_page/create";
    }

    @PostMapping()
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, Model model) {
        model.addAttribute("allRoles", roleService.findAll());
        if (bindingResult.hasErrors()) {
            return "user_page/create";
        }
        if (userService.findByUserName(user.getUsername()) != null) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "public/registration";
        }
        userService.save(user);
        return "redirect:/admin/users";
    }
}
