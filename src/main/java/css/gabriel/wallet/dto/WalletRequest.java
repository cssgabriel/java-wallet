package css.gabriel.wallet.dto;

import jakarta.validation.constraints.NotNull;

public record WalletRequest(String name, @NotNull Long userId) {

}
