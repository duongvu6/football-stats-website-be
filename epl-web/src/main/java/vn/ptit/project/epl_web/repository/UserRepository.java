package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import vn.ptit.project.epl_web.domain.User;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

}
