package vn.ptit.project.epl_web.service;

import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.repository.UserRepository;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
