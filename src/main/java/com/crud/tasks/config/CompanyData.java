package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyData {
    @Value("${company.data.name}")
    private String companyName;

    @Value("${company.data.goal}")
    private String companyGoal;

    @Value("${company.data.email}")
    private String companyEmail;

    @Value("${company.data.phone}")
    private String companyPhone;
}
