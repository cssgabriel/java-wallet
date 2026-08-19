package css.gabriel.wallet.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import css.gabriel.wallet.dto.AssetResponse;
import css.gabriel.wallet.dto.TickerPageResponse;
import css.gabriel.wallet.dto.brapi.BrapiQuoteResponse;
import css.gabriel.wallet.dto.brapi.BrapiTickersResponse;
import css.gabriel.wallet.exception.BrapiIntegrationException;
import css.gabriel.wallet.mapper.BrapiAssetMapper;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BrapiService implements ApiService {

  private final RestClient restClient;

  public BrapiService(@Value("${brapi.api.token}") String token) {
    this.restClient = RestClient.builder()
      .baseUrl("https://brapi.dev/api")
      .defaultHeader("Authorization", "Bearer " + token)
      .build();
  }

  @Override
  public TickerPageResponse getTickers(int page) {
    BrapiTickersResponse response = restClient.get()
      .uri(uriBuilder -> uriBuilder
        .path("/v2/tickers")
        .queryParam("page", page)
        .build())
      .retrieve()
      .onStatus(status -> status.is4xxClientError(), (req, res) -> {
        throw new EntityNotFoundException("Não foi possível obter a lista de tickers");
      })
      .onStatus(status -> status.is5xxServerError(), (req, res) -> {
        throw new BrapiIntegrationException("Brapi indisponível no momento");
      })
      .body(BrapiTickersResponse.class);

    if (response == null || response.results() == null) {
      return new TickerPageResponse(List.of(), null);
    }

    return BrapiAssetMapper.toTickerPageResponse(response);
  }

  @Override
  public AssetResponse getQuote(String ticker) {
    BrapiQuoteResponse response = restClient.get()
      .uri("/quote/{ticker}", ticker)
      .retrieve()
      .onStatus(status -> status.is4xxClientError(), (req, res) -> {
        throw new EntityNotFoundException("Ticker não encontrado na Brapi: " + ticker);
      })
      .onStatus(status -> status.is5xxServerError(), (req, res) -> {
        throw new BrapiIntegrationException("Brapi indisponível no momento");
      })
      .body(BrapiQuoteResponse.class);

    if (response == null || response.results() == null || response.results().isEmpty()) {
      throw new EntityNotFoundException("Ticker não encontrado na Brapi: " + ticker);
    }

    return BrapiAssetMapper.toAssetResponse(response.results().get(0));
  }
}
