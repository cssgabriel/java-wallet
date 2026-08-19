package css.gabriel.wallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import css.gabriel.wallet.dto.AssetResponse;
import css.gabriel.wallet.dto.TickerPageResponse;
import css.gabriel.wallet.service.ApiService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/market")
@Validated
public class MarketDataController {

  private final ApiService service;

  public MarketDataController(ApiService service) {
    this.service = service;
  }

  @GetMapping("/tickers")
  public ResponseEntity<TickerPageResponse> getTickers(
    @RequestParam(defaultValue = "1") @Min(1) int page
  ) {
    return ResponseEntity.ok().body(service.getTickers(page));
  }

  @GetMapping("/quote/{ticker}")
  public ResponseEntity<AssetResponse> getQuote(
    @PathVariable @NotBlank String ticker
  ) {
    return ResponseEntity.ok().body(service.getQuote(ticker));
  }
}
