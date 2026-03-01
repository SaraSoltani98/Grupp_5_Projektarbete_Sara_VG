package se.iths.johan.grupp_5_projektarbete.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.iths.johan.grupp_5_projektarbete.exception.LeaveValidationException;

import java.time.LocalDate;

@Component
public class LeaveValidator {

    private static final Logger log = LoggerFactory.getLogger(LeaveValidator.class);

    public void validateEmployeeName(String employeeName) {
        if (employeeName == null || employeeName.isBlank()) {
            log.warn("Validation failed: employeeName is null or blank");
            throw new LeaveValidationException("employeeName must not be blank");
        }

        if (employeeName.length() > 255) {
            log.warn("Validation failed: employeeName longer than 255 characters");
            throw new LeaveValidationException("employeeName must not be longer than 255 characters");
        }
    }

    public void validateStartDate(LocalDate startDate) {
        if (startDate == null) {
            log.warn("Validation failed: startDate is null");
            throw new LeaveValidationException("startDate must not be null");
        }
    }

    public void validateEndDate(LocalDate startDate, LocalDate endDate) {
        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            log.warn("Validation failed: endDate {} is before startDate {}", endDate, startDate);
            throw new LeaveValidationException("endDate must be after startDate");
        }
    }
}