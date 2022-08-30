package ai.clarity.springboot.esg.controller;

import ai.clarity.springboot.esg.model.CompanyScore;
import ai.clarity.springboot.esg.service.ESGService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EsgController {

  private final ESGService esgService;

  public EsgController(ESGService esgService) {
    this.esgService = esgService;
  }

  @GetMapping(value = "/companies/{id}/esg", produces = "application/json")
  public CompanyScore companyEsg(@PathVariable("id") String id) {
    return esgService.getCompanyScore(id);
  }
}
