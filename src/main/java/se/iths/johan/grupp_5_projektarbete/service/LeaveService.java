package se.iths.johan.grupp_5_projektarbete.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.iths.johan.grupp_5_projektarbete.exception.LeaveNotFoundException;
import se.iths.johan.grupp_5_projektarbete.model.Leave;
import se.iths.johan.grupp_5_projektarbete.repository.LeaveRepository;
import se.iths.johan.grupp_5_projektarbete.validator.LeaveValidator;

import java.util.List;

@Service
public class LeaveService {

    private static final Logger log = LoggerFactory.getLogger(LeaveService.class);

    private final LeaveRepository leaveRepository;
    private final LeaveValidator leaveValidator;

    public LeaveService(LeaveRepository leaveRepository, LeaveValidator leaveValidator) {
        this.leaveRepository = leaveRepository;
        this.leaveValidator = leaveValidator;
    }

    public List<Leave> getAll() {
        log.info("Fetching all leaves");
        return leaveRepository.findAll();
    }

    public Leave getById(Long id) {
        log.info("Fetching leave with id={}", id);

        return leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Leave not found with id={}", id);
                    return new LeaveNotFoundException("Leave with id " + id + " was not found");
                });
    }

    public Leave create(Leave leave) {
        log.info("Creating leave for employeeName={}", leave != null ? leave.getEmployeeName() : null);

        validateLeave(leave);

        Leave saved = leaveRepository.save(leave);
        log.info("Created leave with id={}", saved.getId());
        return saved;
    }

    public Leave update(Long id, Leave updated) {
        log.info("Updating leave with id={}", id);

        Leave existing = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Leave not found with id={} (update)", id);
                    return new LeaveNotFoundException("Leave with id " + id + " was not found");
                });

        validateLeave(updated);

        existing.setEmployeeName(updated.getEmployeeName());
        existing.setStartDate(updated.getStartDate());
        existing.setEndDate(updated.getEndDate());
        existing.setApproved(updated.isApproved());

        Leave saved = leaveRepository.save(existing);
        log.info("Updated leave with id={}", saved.getId());
        return saved;
    }

    public void delete(Long id) {
        log.info("Deleting leave with id={}", id);

        Leave existing = leaveRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Leave not found with id={} (delete)", id);
                    return new LeaveNotFoundException("Leave with id " + id + " was not found");
                });

        leaveRepository.delete(existing);
        log.info("Deleted leave with id={}", id);
    }

    private void validateLeave(Leave leave) {
        // Om leave råkar vara null så får du tydligare logg + tydligt fel istället för NPE långt ner
        if (leave == null) {
            log.warn("Validation failed: Leave is null");
            throw new IllegalArgumentException("Leave cannot be null");
        }

        leaveValidator.validateEmployeeName(leave.getEmployeeName());
        leaveValidator.validateStartDate(leave.getStartDate());
        leaveValidator.validateEndDate(leave.getStartDate(), leave.getEndDate());
    }
}