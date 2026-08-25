package com.ecommerce.api.users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<UsersModel, String>{
    UsersModel findByEmail(String email);
}
