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
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public boolean isEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public ResponseCreateUserDTO convertUserToResponseCreateUserDTO(User user) {
        return this.modelMapper.map(user, ResponseCreateUserDTO.class);
    }

    public User convertRequestRegisterUserDTOtoUser(RequestRegisterUserDTO userDTO) {
        return this.modelMapper.map(userDTO, User.class);
    }

    public User handleSaveUser(User user) {
        return this.userRepository.save(user);
    }
}