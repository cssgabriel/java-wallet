package css.gabriel.wallet.dto.brapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BrapiTickersResponse(
  List<BrapiTickerItem> results,
  BrapiPagination pagination
) {

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record BrapiTickerItem(
    String symbol,
    String name,
    String logoUrl
  ) {}

  @JsonIgnoreProperties(ignoreUnknown = true)
  public record BrapiPagination(
    int page,
    int limit,
    int totalItems,
    int totalPages,
    boolean hasNextPage
  ) {}
}