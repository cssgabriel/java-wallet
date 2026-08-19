package css.gabriel.wallet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "wallet_id", nullable = false)
  private Wallet wallet;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "asset_id", nullable = false)
  private Asset asset;

  @Enumerated(EnumType.STRING)
  private TransactionType type;

  @Column(precision = 19, scale = 8)
  private BigDecimal quantity;

  @Column(precision = 19, scale = 8)
  private BigDecimal unitPrice;

  @CreationTimestamp
  private LocalDateTime createdAt;

  public Transaction() {}

  public Transaction(
    Wallet wallet,
    Asset asset,
    TransactionType type,
    BigDecimal quantity,
    BigDecimal unitPrice
  ) {
    this.wallet = wallet;
    this.asset = asset;
    this.type = type;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
  }

  public Long getId() {
    return id;
  }

  public Wallet getWallet() {
    return wallet;
  }

  public Asset getAsset() {
    return asset;
  }

  public TransactionType getType() {
    return type;
  }

  public BigDecimal getQuantity() {
    return quantity;
  }

  public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  
}
