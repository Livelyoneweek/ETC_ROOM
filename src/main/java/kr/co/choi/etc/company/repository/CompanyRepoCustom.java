package kr.co.choi.etc.company.repository;

import kr.co.choi.etc.company.entity.Company;

import java.util.List;

public interface CompanyRepoCustom {

    List<Company> useQueryDslForList();
}
