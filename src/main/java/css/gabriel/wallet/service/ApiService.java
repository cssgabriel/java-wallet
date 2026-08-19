package css.gabriel.wallet.service;

import css.gabriel.wallet.dto.AssetResponse;
import css.gabriel.wallet.dto.TickerPageResponse;

public interface ApiService {
  AssetResponse getQuote(String ticker);
  TickerPageResponse getTickers(int page);
}
