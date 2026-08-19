package css.gabriel.wallet.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import css.gabriel.wallet.dto.AssetRequest;
import css.gabriel.wallet.dto.AssetResponse;
import css.gabriel.wallet.model.Asset;
import css.gabriel.wallet.repository.AssetRepository;

@Service
public class AssetService {

  private final AssetRepository repository;
  private final ApiService apiService;

  public AssetService(AssetRepository repository, ApiService apiService) {
    this.repository = repository;
    this.apiService = apiService;
  }

  @Transactional
  public Asset findOrCreateByTicker(String ticker) {
    return repository.findByTicker(ticker)
      .orElseGet(() -> createFromBrapi(ticker));
  }

  private Asset createFromBrapi(String ticker) {
    AssetResponse quote = apiService.getQuote(ticker);

    Asset asset = new Asset(
      new AssetRequest(
        quote.name(),
        quote.price(),
        quote.ticker(),
        quote.currency(),
        quote.logoUrl()
      )
    );

    return repository.save(asset);
  }
}