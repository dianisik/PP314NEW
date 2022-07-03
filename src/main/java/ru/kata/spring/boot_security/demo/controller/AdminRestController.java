package ru.kata.spring.boot_security.demo.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.MyUserDetailsService;
import ru.kata.spring.boot_security.demo.service.UpdateUserService;
import java.security.Principal;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class AdminRestController {
    private final MyUserDetailsService userService;
    private final UpdateUserService updateUserService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/api/principal")
    public ResponseEntity<User> getPrincipalInfo(Principal principal) {
        return ResponseEntity.ok().body(userService.findByUserName(principal.getName()));
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<User> findOneUser(@PathVariable long id) {

        return ResponseEntity.ok().body(userService.findUserById(id));
    }

    @PostMapping("/api")
    public void addNewUser(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.save(user);
    }

    @GetMapping("/api")
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @PutMapping("/api/{id}")
    public void updateUser(@RequestBody User user) {
        updateUserService.setPassword(user, user.getId());
        userService.saveAndFlush(user);
    }

    @DeleteMapping("/api/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }
}
