package ai.clarity.springboot.companies.controller;

import ai.clarity.springboot.companies.model.Company;
import ai.clarity.springboot.companies.service.CompanyService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

  private final CompanyService companyService;

  public CompanyController(CompanyService companyService) {
    this.companyService = companyService;
  }

  @GetMapping(value = "/companies", produces = "application/json")
  public List<Company> companies() {
    return companyService.getAllCompanies();
  }

}
