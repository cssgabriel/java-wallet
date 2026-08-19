package css.gabriel.wallet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import css.gabriel.wallet.dto.UserResponse;
import css.gabriel.wallet.dto.WalletRequest;
import css.gabriel.wallet.dto.WalletResponse;
import css.gabriel.wallet.model.User;
import css.gabriel.wallet.model.Wallet;
import css.gabriel.wallet.repository.UserRepository;
import css.gabriel.wallet.repository.WalletRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class WalletService {

  private final WalletRepository walletRepository;
  private final UserRepository userRepository;

  public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
    this.walletRepository = walletRepository;
    this.userRepository = userRepository;
  }

  public List<WalletResponse> getAll(Long userId) {
    List<Wallet> wallets = userId != null
      ? walletRepository.findByUserId(userId)
      : walletRepository.findAll();
    return wallets.stream().map(this::convertToDto).toList();
  }

  public WalletResponse getById(Long id) {
    Wallet wallet = walletRepository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    
    return convertToDto(wallet);
  }

  public WalletResponse create(WalletRequest dto) {
    User user = userRepository.findById(dto.userId())
      .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

    Wallet wallet = walletRepository.save(new Wallet(dto, user));
    return convertToDto(wallet);
  }

  public void delete(Long id) {
    if (!walletRepository.existsById(id)) {
      throw new EntityNotFoundException("Wallet não encontrada");
    }

    walletRepository.deleteById(id);
  }

  private WalletResponse convertToDto(Wallet wallet) {
    User user = wallet.getUser();
    UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail());

    return new WalletResponse(
      wallet.getId(),
      wallet.getName(),
      userResponse,
      wallet.getTransactions()
    );
  }

}
