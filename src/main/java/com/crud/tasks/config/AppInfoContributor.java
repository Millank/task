package com.crud.tasks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.boot.actuate.info.Info.Builder;
import static java.time.DayOfWeek.*;

@Component
public class AppInfoContributor implements InfoContributor {
    @Value("${dynamic-info.company.name}")
    private String companyName;

    @Value("${dynamic-info.company.goal}")
    private String companyGoal;

    @Value("${dynamic-info.company.email}")
    private String companyEmail;

    @Value("${dynamic-info.company.phone}")
    private String companyPhone;

    @Override
    public void contribute(Builder builder) {
        builder.withDetails(Collections.singletonMap("company", buildCompanyInfoMap()));
    }

    private Map<String, Object> buildCompanyInfoMap() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("name", companyName);
        map.put("goal", companyGoal);
        map.put("email", companyEmail);
        if (isWorkTimeNow()) {
            map.put("phone", companyPhone);
        }
        return map;
    }

    private boolean isWorkTimeNow() {
        // todo: work time data should be stored in database or *.properties file
        return isDefaultWorkTimeNow();
    }

    // from Monday to Friday 08:00em - 16:00pm
    private boolean isDefaultWorkTimeNow() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DayOfWeek currentDay = currentDateTime.getDayOfWeek();
        LocalTime curentTime = currentDateTime.toLocalTime();

        return currentDay.compareTo(MONDAY) >= 0 && currentDay.compareTo(SATURDAY) < 0 &&
               curentTime.isAfter(LocalTime.of(8,0)) && curentTime.isBefore(LocalTime.of(16,0));
    }
}
