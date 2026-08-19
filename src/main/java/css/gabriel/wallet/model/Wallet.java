package css.gabriel.wallet.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import css.gabriel.wallet.dto.WalletRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "wallets")
public class Wallet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
  private List<Transaction> transactions = new ArrayList<>();

  @CreationTimestamp
  private LocalDateTime createdAt;

  public Wallet() {}

  public Wallet(WalletRequest dto, User user) {
    this.name = dto.name();
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public User getUser() {
    return user;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  
}
