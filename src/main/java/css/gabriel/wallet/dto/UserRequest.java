package css.gabriel.wallet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
  @NotNull @NotBlank String name,
  @NotNull @Email String email
) {

}
