package com.webdev.dateikomprimierung.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webdev.dateikomprimierung.model.User;
import com.webdev.dateikomprimierung.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Benutzer erstellen
    public User createUser(User user) {
        // Speichern des Benutzers in der Datenbank, um die ID zu setzen
        User createdUser = userRepository.save(user);

        // Generiere den Zugriffslink basierend auf der ID des gespeicherten Nutzers
        String zugriffslink = "http://localhost:8080/api/image-compression/upload?userId=" + createdUser.getId();
        
        // Setze den Zugriffslink für den Benutzer
        createdUser.setZugriffslink(zugriffslink);
        
        // Speichern des Benutzers mit dem neuen Zugriffslink
        userRepository.save(createdUser);

        return createdUser;
    }

    // Benutzer anhand der ID abrufen
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // Zugriffslink für den Benutzer setzen (falls du diesen später nochmal ändern möchtest)
    public void setZugriffslink(User user, String zugriffslink) {
        user.setZugriffslink(zugriffslink);
        userRepository.save(user);  // Benutzer mit dem neuen Zugriffslink speichern
    }
}
