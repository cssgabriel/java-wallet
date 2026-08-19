package css.gabriel.wallet.dto;

public record TickerResponse(
  String symbol,
  String name,
  String logoUrl
) {}