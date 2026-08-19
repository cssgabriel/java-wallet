package css.gabriel.wallet.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import css.gabriel.wallet.dto.UserRequest;
import css.gabriel.wallet.dto.UserResponse;
import css.gabriel.wallet.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService service;

  public UserController(UserService service) {
    this.service = service;
  }

  @GetMapping
  public ResponseEntity<List<UserResponse>> getAll() {
    return ResponseEntity.ok().body(service.getAll());
  }

  @PostMapping
  public ResponseEntity<Void> create(@RequestBody @Valid UserRequest dto) {
    try {
      service.create(dto);
      return ResponseEntity.status(HttpStatus.CREATED).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest dto) {
    try {
      return ResponseEntity.ok(service.update(id, dto));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping("/delete")
  public ResponseEntity<Void> delete() {
    service.delete();
    return ResponseEntity.noContent().build();
  }

}
