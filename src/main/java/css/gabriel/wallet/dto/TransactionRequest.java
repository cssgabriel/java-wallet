package css.gabriel.wallet.dto;

import java.math.BigDecimal;

import css.gabriel.wallet.model.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
  @NotBlank String ticker,
  @NotNull TransactionType type,
  @NotNull @Positive BigDecimal quantity,
  @DecimalMin(value = "0", inclusive = false) BigDecimal unitPrice
) {}