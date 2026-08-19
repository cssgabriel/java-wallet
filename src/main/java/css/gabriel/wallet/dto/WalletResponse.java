package css.gabriel.wallet.dto;

import java.util.List;

import css.gabriel.wallet.model.Transaction;

public record WalletResponse(
  Long id,
  String name,
  UserResponse user,
  List<Transaction> transactions
) {
  
}
