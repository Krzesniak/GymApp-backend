package pl.krzesniak.gymapp.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;
import pl.krzesniak.gymapp.dto.UserDTO;
import pl.krzesniak.gymapp.repositories.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers(){
        log.info("Find all usernames");
        userRepository.findAll();
        throw  new NotYetImplementedException("");
    }

    public UserDTO getUserByUsername(String username){
        log.info("Find user by username {}", username);
        userRepository.findByUsername(username);
        throw  new NotYetImplementedException("");
    }

}
