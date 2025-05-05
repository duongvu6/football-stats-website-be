package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.User;
import vn.ptit.project.epl_web.dto.request.auth.RequestRegisterUserDTO;
import vn.ptit.project.epl_web.dto.response.user.ResponseCreateUserDTO;
import vn.ptit.project.epl_web.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;
    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public boolean isEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }


    public User handleSaveUser(RequestRegisterUserDTO userDTO) {
        User user = this.mapper.map(userDTO,User.class);
        user.setRole("USER");
        return this.userRepository.save(user);
    }
    public ResponseCreateUserDTO toResponseCreateUserDTO(User user) {
        return this.mapper.map(user, ResponseCreateUserDTO.class);
    }
    public User getUserByUsername(String email) {
        return this.userRepository.findByEmail(email).get();
    }
    public void updateUserToken(String token, String email) {
        User currentUser = this.getUserByUsername(email);
        if (currentUser != null) {
            currentUser.setRefreshtoken(token);
            this.userRepository.save(currentUser);
        }
    }


    public User getUserByRefreshTokenAndEmail(String refreshToken, String email) {
        return this.userRepository.findByRefreshtokenAndEmail(refreshToken, email).get();
    }

}