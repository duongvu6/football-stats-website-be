package vn.ptit.project.epl_web.mapper.user;

import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.User;
import vn.ptit.project.epl_web.dto.request.auth.RequestRegisterUserDTO;
import vn.ptit.project.epl_web.dto.response.user.ResponseCreateUserDTO;

@Service
public class UserMapper {
    public User toUser(RequestRegisterUserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setRole(("USER"));
        return user;
    }
}
