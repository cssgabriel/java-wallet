package css.gabriel.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import css.gabriel.wallet.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  Optional<List<Transaction>> findByWalletId(Long id);

  Optional<Transaction> findByIdAndWalletId(Long id, Long walletId);
}
