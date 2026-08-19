package css.gabriel.wallet.service;

import org.springframework.stereotype.Service;

import css.gabriel.wallet.dto.AssetResponse;
import css.gabriel.wallet.dto.TickerPageResponse;

@Service
public class AssetService {

  private final ApiService apiService;

  public AssetService(ApiService apiService) {
    this.apiService = apiService;
  }

  public TickerPageResponse getTickers(int page) {
    return apiService.getTickers(page);
  }

  public AssetResponse getQuote(String ticker) {
    return apiService.getQuote(ticker);
  }
}
