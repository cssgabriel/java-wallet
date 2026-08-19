package css.gabriel.wallet.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AssetRequest(
  @NotNull @NotBlank String name,
  @NotNull @Min(0) BigDecimal price,
  @NotNull @NotBlank String ticker,
  @NotNull @NotBlank String currency,
  @NotNull @NotBlank String logoUrl
) {

}
