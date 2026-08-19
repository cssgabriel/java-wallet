package css.gabriel.wallet.dto;

import java.util.List;

public record TickerPageResponse(
  List<TickerResponse> tickers,
  PageInfo page
) {}