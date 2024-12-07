package com.ukg.hr_service.util;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer {

    private final JdbcTemplate jdbcTemplate;

    public DataInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initializeData() {
        if (isHrTableEmpty()) {
            insertHrData();
        }
    }

    private boolean isHrTableEmpty() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM hr", Integer.class);
        return count != null && count == 0;
    }

    private void insertHrData() {
        String sql = "INSERT INTO hr (first_name, last_name, employee_id, email, password) VALUES (?, ?, ?, ?, ?)";
        
        jdbcTemplate.batchUpdate(sql,
            List.of(
                new Object[]{"John", "Doe", 100L, "john.doe@ukg.com", "$2a$12$A.eCfLMgdWzdw4.7pqPoS.SYqagrVkhoxAM3tUSggjI.RBEY3CJhm"},
                new Object[]{"Jane", "Smith", 101L, "jane.smith@ukg.com", "$2a$12$nqwT6IUpUD9rk1psApA/oeO.bcG9uy4/794co2jgUAcQLSpDKFz96"}
            )
        );
    }
}
