package css.gabriel.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import css.gabriel.wallet.model.Asset;

public interface AssetRepository extends JpaRepository<Asset, Long> {

}
