package css.gabriel.wallet.mapper;

import java.util.List;

import css.gabriel.wallet.dto.AssetResponse;
import css.gabriel.wallet.dto.PageInfo;
import css.gabriel.wallet.dto.TickerPageResponse;
import css.gabriel.wallet.dto.TickerResponse;
import css.gabriel.wallet.dto.brapi.BrapiQuoteResponse.BrapiQuote;
import css.gabriel.wallet.dto.brapi.BrapiTickersResponse;

public final class BrapiAssetMapper {

  private BrapiAssetMapper() {}

  public static AssetResponse toAssetResponse(BrapiQuote quote) {
    String name = (quote.longName() == null || quote.longName().isBlank())
      ? quote.shortName()
      : quote.longName();

    return new AssetResponse(
      name,
      quote.regularMarketPrice(),
      quote.symbol(),
      quote.currency(),
      quote.logourl()
    );
  }

  public static TickerPageResponse toTickerPageResponse(BrapiTickersResponse response) {
    List<TickerResponse> tickers = response.results().stream()
      .map(item -> new TickerResponse(item.symbol(), item.name(), item.logoUrl()))
      .toList();

    PageInfo page = new PageInfo(
      response.pagination().page(),
      response.pagination().limit(),
      response.pagination().totalItems(),
      response.pagination().totalPages(),
      response.pagination().hasNextPage()
    );

    return new TickerPageResponse(tickers, page);
  }
}