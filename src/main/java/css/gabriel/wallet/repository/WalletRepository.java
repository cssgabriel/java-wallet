package css.gabriel.wallet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import css.gabriel.wallet.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

  List<Wallet> findByUserId(Long userId);
}
