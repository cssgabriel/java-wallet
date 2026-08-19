package css.gabriel.wallet.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import css.gabriel.wallet.model.TransactionType;

public record TransactionResponse(
  Long id,
  WalletTransactionResponse wallet,
  AssetResponse asset,
  TransactionType type,
  BigDecimal quantity,
  BigDecimal unitPrice,
  LocalDateTime createdAt
) {}
