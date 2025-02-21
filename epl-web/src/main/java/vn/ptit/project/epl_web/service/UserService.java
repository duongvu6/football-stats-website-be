package vn.ptit.project.epl_web.service;

import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.User;
import vn.ptit.project.epl_web.dto.request.auth.RequestRegisterUserDTO;
import vn.ptit.project.epl_web.dto.response.user.ResponseCreateUserDTO;
import vn.ptit.project.epl_web.mapper.user.UserMapper;
import vn.ptit.project.epl_web.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public boolean isEmailExists(String email) {
        return this.userRepository.existsByEmail(email);
    }


    public User handleSaveUser(RequestRegisterUserDTO userDTO) {
        return this.userRepository.save(this.userMapper.toUser(userDTO));
    }
    public ResponseCreateUserDTO toResponseCreateUserDTO(User user) {
        ResponseCreateUserDTO userDTO = new ResponseCreateUserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }
}