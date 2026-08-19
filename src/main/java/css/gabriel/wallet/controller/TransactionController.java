package css.gabriel.wallet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import css.gabriel.wallet.dto.TransactionRequest;
import css.gabriel.wallet.dto.TransactionResponse;
import css.gabriel.wallet.service.TransactionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RestController
@RequestMapping("/wallets/{walletId}/transactions")
@Validated
public class TransactionController {

  private final TransactionService service;

  public TransactionController(TransactionService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<TransactionResponse>> getAll(@Min(1) @PathVariable Long walletId) {
    return ResponseEntity.ok().body(service.getAll(walletId));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionResponse> getById(
    @Min(1) @PathVariable Long walletId,
    @PathVariable Long id
  ) {
    return ResponseEntity.ok().body(service.getById(walletId, id));
  }

  @PostMapping
  public ResponseEntity<TransactionResponse> create(
    @Min(1) @PathVariable Long walletId,
    @RequestBody @Valid TransactionRequest request
  ) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(walletId, request));
  }
}
