package css.gabriel.wallet.dto;

import java.math.BigDecimal;

public record AssetResponse(
  String name,
  BigDecimal price,
  String ticker,
  String currency,
  String logoUrl
) {

}
