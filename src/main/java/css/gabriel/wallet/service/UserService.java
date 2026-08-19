package css.gabriel.wallet.service;

import java.util.List;

import org.springframework.stereotype.Service;

import css.gabriel.wallet.dto.UserRequest;
import css.gabriel.wallet.dto.UserResponse;
import css.gabriel.wallet.exception.EmailAlreadyExistsException;
import css.gabriel.wallet.model.User;
import css.gabriel.wallet.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public List<UserResponse> getAll() {
    return repository.findAll().stream().map(u -> convertToDto(u)).toList();
  }

  public void create(UserRequest dto) {
    boolean emailExists = repository.existsByEmail(dto.email());
    if (emailExists) throw new EmailAlreadyExistsException("E-mail já cadastrado");
    repository.save(new User(dto));
  }

  public UserResponse update(Long id, UserRequest dto) {
    User user = repository.findById(id)
     .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    
    if (dto.email() != null && !dto.email().equals(user.getEmail())) {
      boolean emailInUse = repository.existsByEmail(dto.email());
      if (emailInUse) {
        throw new EmailAlreadyExistsException("Email já está em uso");
      }
    }
    
    user.update(dto);

    return convertToDto(repository.save(user));
  }

  public void delete() {
    repository.deleteAll();
  }

  private UserResponse convertToDto(User user) {
    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }
}
