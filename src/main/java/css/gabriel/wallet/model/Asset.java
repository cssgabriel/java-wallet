package css.gabriel.wallet.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import css.gabriel.wallet.dto.AssetRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "assets")
public class Asset {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(precision = 19, scale = 8)
  private BigDecimal price;

  @Column(unique = true, nullable = false)
  private String ticker;

  private String currency;

  // @Enumerated(EnumType.STRING)
  // private AssetType type;

  private String logoUrl;

  @OneToMany(mappedBy = "asset")
  private List<Transaction> transactions = new ArrayList<>();

  public Asset() {}

  public Asset(AssetRequest dto) {
    this.name = dto.name();
    this.price = dto.price();
    this.ticker = dto.ticker();
    this.currency = dto.currency();
    this.logoUrl = dto.logoUrl();
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public String getTicker() {
    return ticker;
  }

  public String getCurrency() {
    return currency;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public String getLogoUrl() {
    return logoUrl;
  }
}
