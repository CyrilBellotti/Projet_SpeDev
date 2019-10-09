package com.atlantis.atlantis.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CrossOrigin
    @GetMapping
    public List<User> findAll (){
        return userRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/id/{id}")
    public User findById(@PathVariable Long id) {
        if (!userRepository.findById(id).isPresent()) {
            throw new UserNotFoundException("User with id " + id + " not found.");
        }
        return userRepository.findById(id).get();
    }

    @CrossOrigin
    @GetMapping("/name/{name}")
    public User findByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    @CrossOrigin
    @GetMapping("/mail/{mail}")
    public User findByMail(@PathVariable String mail) {
        return userRepository.findByMail(mail);
    }

    @CrossOrigin
    @PostMapping(path = "/createUser")
    public void addMember(@RequestBody User user) {
        this.userRepository.save(user);
    }

}