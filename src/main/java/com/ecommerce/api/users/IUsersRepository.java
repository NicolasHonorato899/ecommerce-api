package com.ecommerce.api.users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IUsersRepository extends JpaRepository<UsersModel, String>{
    Optional<UsersModel> findByEmail(String email);
}
