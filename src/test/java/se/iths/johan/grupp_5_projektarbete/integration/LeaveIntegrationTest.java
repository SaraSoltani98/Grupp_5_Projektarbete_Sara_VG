package se.iths.johan.grupp_5_projektarbete.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.johan.grupp_5_projektarbete.model.Leave;
import se.iths.johan.grupp_5_projektarbete.repository.LeaveRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LeaveIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LeaveRepository leaveRepository;

    @BeforeEach
    void setUp() {
        leaveRepository.deleteAll();
    }

    @Test
    void contextLoads() {
        // bara för att se att testet kör
    }

    @Test
    void shouldCreateLeave() throws Exception {
        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post("/leaves")
                                .param("employeeName", "Anna")
                                .param("startDate", "2026-03-01")
                                .param("endDate", "2026-03-05")
                                .param("approved", "true")
                )
                .andExpect(status().is3xxRedirection());

        assertEquals(1, leaveRepository.count());
    }

    @Test
    void shouldGetAllLeavesAndVerifyCount() throws Exception {
        Leave l1 = new Leave();
        l1.setEmployeeName("A");
        l1.setStartDate(LocalDate.of(2026, 3, 1));
        l1.setEndDate(LocalDate.of(2026, 3, 2));
        l1.setApproved(false);

        Leave l2 = new Leave();
        l2.setEmployeeName("B");
        l2.setStartDate(LocalDate.of(2026, 3, 3));
        l2.setEndDate(LocalDate.of(2026, 3, 4));
        l2.setApproved(true);

        leaveRepository.save(l1);
        leaveRepository.save(l2);

        mockMvc.perform(
                        org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get("/leaves")
                )
                .andExpect(status().isOk());

        assertEquals(2, leaveRepository.count());
    }
}