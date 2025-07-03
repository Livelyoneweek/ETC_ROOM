package kr.co.choi.etc.company.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Profile("!production")
@Slf4j
public class CompanyServiceInterfaceImpl2 implements CompanyServiceInterface {

    @Override
    public String findCompany(UUID companyId) {
        log.info("### CompanyServiceInterfaceImpl2.findCompany");
        return "2";
    }
}
