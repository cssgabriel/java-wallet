package css.gabriel.wallet.model;

import java.util.List;

import css.gabriel.wallet.dto.UserRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String email;

  private String name;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Wallet> wallets;

  public User() {}

  public User(UserRequest dto) {
    this.email = dto.email();
    this.name = dto.name();
  }

  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  public List<Wallet> getWallet() {
    return wallets;
  }

  public void update(UserRequest dto) {
    if (dto.name() != null && !dto.name().isBlank()) {
      this.name = dto.name();
    }
    if (dto.email() != null && !dto.email().isBlank()) {
      this.email = dto.email();
    }
  }
}
