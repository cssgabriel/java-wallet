package css.gabriel.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import css.gabriel.wallet.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByEmail(String email);

  User findByEmail(String email);
}
