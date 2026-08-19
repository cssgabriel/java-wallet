package css.gabriel.wallet.dto;

public record PageInfo(
  int page,
  int limit,
  int totalItems,
  int totalPages,
  boolean hasNextPage
) {}