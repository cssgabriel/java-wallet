package css.gabriel.wallet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import css.gabriel.wallet.dto.WalletRequest;
import css.gabriel.wallet.dto.WalletResponse;
import css.gabriel.wallet.service.WalletService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/wallets")
public class WalletController {

  private final WalletService service;

  public WalletController(WalletService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<WalletResponse>> getAll(@RequestParam(required = false) Long userId) {
    return ResponseEntity.ok().body(service.getAll(userId));
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<WalletResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.getById(id));
  }

  @GetMapping(value = "/{id}/transactions")
  public ResponseEntity<List<WalletResponse>> getTransactions(@PathVariable Long id) {
    return ResponseEntity.ok().body(service.getAll(id));
  }

  @PostMapping
  public ResponseEntity<WalletResponse> create(@RequestBody @Valid WalletRequest dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

}
