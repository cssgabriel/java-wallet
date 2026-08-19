package css.gabriel.wallet.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import css.gabriel.wallet.dto.AssetResponse;
import css.gabriel.wallet.dto.TransactionRequest;
import css.gabriel.wallet.dto.TransactionResponse;
import css.gabriel.wallet.dto.WalletTransactionResponse;
import css.gabriel.wallet.model.Asset;
import css.gabriel.wallet.model.Transaction;
import css.gabriel.wallet.model.Wallet;
import css.gabriel.wallet.repository.TransactionRepository;
import css.gabriel.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class TransactionService {

  private final TransactionRepository repository;
  private final WalletRepository walletRepository;
  private final AssetService assetService;

  public TransactionService(
    TransactionRepository repository,
    WalletRepository walletRepository,
    AssetService assetService
  ) {
    this.repository = repository;
    this.walletRepository = walletRepository;
    this.assetService = assetService;
  }

  public List<TransactionResponse> getAll(Long walletId) {
    List<Transaction> transactions = repository.findByWalletId(walletId)
      .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));
  
    return transactions.stream()
      .map(this::convertToDto)
      .toList();
  }

  public TransactionResponse getById(Long walletId, Long id) {
    Transaction transaction = repository.findByIdAndWalletId(id, walletId)
      .orElseThrow(() -> new EntityNotFoundException("Transação não encontrada"));

    return convertToDto(transaction);
  }

  @Transactional
  public TransactionResponse create(Long walletId, TransactionRequest request) {
    Wallet wallet = walletRepository.findById(walletId)
      .orElseThrow(() -> new EntityNotFoundException("Carteira não encontrada"));

    Asset asset = assetService.findOrCreateByTicker(request.ticker());

    BigDecimal unitPrice = request.unitPrice() != null
      ? request.unitPrice()
      : asset.getPrice();

    Transaction transaction = new Transaction(
      wallet,
      asset,
      request.type(),
      request.quantity(),
      unitPrice
    );

    return convertToDto(repository.save(transaction));
  }

  private TransactionResponse convertToDto(Transaction transaction) {
    Wallet wallet = transaction.getWallet();
    Asset asset = transaction.getAsset();

    return new TransactionResponse(
      transaction.getId(),
      new WalletTransactionResponse(
        wallet.getId(),
        wallet.getName()
      ),
      new AssetResponse(
        asset.getName(),
        asset.getPrice(),
        asset.getTicker(),
        asset.getCurrency(),
        asset.getLogoUrl()
      ),
      transaction.getType(),
      transaction.getQuantity(),
      transaction.getUnitPrice(),
      transaction.getCreatedAt()
    );
  }
}
