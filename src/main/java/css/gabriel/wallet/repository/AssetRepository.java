package css.gabriel.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import css.gabriel.wallet.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

  Optional<Asset> findByTicker(String ticker);
}
