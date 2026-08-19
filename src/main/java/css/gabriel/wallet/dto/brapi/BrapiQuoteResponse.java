package css.gabriel.wallet.dto.brapi;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrapiQuoteResponse(List<BrapiQuote> results) {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record BrapiQuote(
    String symbol,
    String shortName,
    String longName,
    String currency,
    BigDecimal regularMarketPrice,
    String logourl
  ) {}
}
