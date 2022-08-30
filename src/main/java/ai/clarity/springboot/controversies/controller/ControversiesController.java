package ai.clarity.springboot.controversies.controller;

import ai.clarity.springboot.controversies.model.Controversy;
import ai.clarity.springboot.controversies.service.ControversyService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControversiesController {

  private final ControversyService controversyService;

  public ControversiesController(ControversyService controversyService) {
    this.controversyService = controversyService;
  }

  @GetMapping(value = "/companies/{companyId}/controversies", produces = "application/json")
  public List<Controversy> companyControversies(@PathVariable("companyId") String companyId) {
    return controversyService.getControversiesByCompany(companyId);
  }
}
