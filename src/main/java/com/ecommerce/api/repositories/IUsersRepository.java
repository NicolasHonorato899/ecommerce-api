package com.ecommerce.api.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.ecommerce.api.models.UsersModel;

public interface IUsersRepository extends JpaRepository<UsersModel, String>{
    Optional<UsersModel> findByEmail(String email);
}
