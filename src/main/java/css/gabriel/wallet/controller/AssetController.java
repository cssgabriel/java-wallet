package css.gabriel.wallet.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import css.gabriel.wallet.service.AssetService;

@RestController
@RequestMapping("/assets")
@Validated
public class AssetController {

  private final AssetService service;

  public AssetController(AssetService service) {
    this.service = service;
  }
}
