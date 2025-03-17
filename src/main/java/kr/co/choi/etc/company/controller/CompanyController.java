package kr.co.choi.etc.company.controller;

import kr.co.choi.etc.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping
    public String findCompanyName(@RequestParam(value = "id") UUID companyId) {
        log.info("### CompanyController.findCompanyName");
        return companyService.findCompany(companyId);
    }

    @GetMapping("/id")
    public List<UUID> findCompanyIdList() {
        log.info("### CompanyController.findCompanyIdList");
        return companyService.findCompanyIdList();
    }
}
