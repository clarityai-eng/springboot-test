package ai.clarity.springboot.controversies.service;

import ai.clarity.springboot.config.ControversyRepository;
import ai.clarity.springboot.controversies.model.Controversy;
import java.util.List;

public class ControversyService {

  private final ControversyRepository controversyRepository;

  public ControversyService(ControversyRepository controversyRepository) {
    this.controversyRepository = controversyRepository;
  }

  public List<Controversy> getControversiesByCompany(String companyId) {
    return controversyRepository.findControversiesByCompanyId(companyId);
  }
}
