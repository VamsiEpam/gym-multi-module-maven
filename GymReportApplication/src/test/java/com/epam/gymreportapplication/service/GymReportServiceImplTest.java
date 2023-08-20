package com.epam.gymreportapplication.service;

import com.epam.gymapp.dto.report.GymReportDTO;
import com.epam.gymreportapplication.model.TrainingReport;
import com.epam.gymreportapplication.repository.GymRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GymReportServiceImplTest {

    @Mock
    private GymRepository gymRepository;

    @InjectMocks
    private GymReportServiceImpl gymReportService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddReport_Success() {
        GymReportDTO inputDTO = new GymReportDTO();
        inputDTO.setTrainerUserName("trainerUser");
        inputDTO.setTrainerFirstName("John");
        inputDTO.setTrainerLastName("Doe");
        inputDTO.setActive(true);
        inputDTO.setDate(LocalDate.of(2023, 8, 11));
        inputDTO.setDuration(60);

        TrainingReport existingReport = new TrainingReport();
        existingReport.setTrainerUsername("trainerUser");
        existingReport.setTrainerFirstName("John");
        existingReport.setTrainerLastName("Doe");
        existingReport.setTrainerIsActive(true);
        existingReport.setDurationSummary(new HashMap<>());

        Mockito.when(gymRepository.findById("trainerUser")).thenReturn(Optional.of(existingReport));

        when(gymRepository.save(Mockito.<TrainingReport>any()))
                .thenReturn(existingReport);


        GymReportDTO resultDTO = gymReportService.addReport(inputDTO);

        assertEquals("trainerUser", resultDTO.getTrainerUserName());
        assertEquals("John", resultDTO.getTrainerFirstName());
        assertEquals("Doe", resultDTO.getTrainerLastName());
        assertEquals(true, resultDTO.isActive());

        Mockito.verify(gymRepository).findById("trainerUser");
        Mockito.verify(gymRepository).save(existingReport);
    }

    @Test
    public void testAddReport_newRecord() {
        GymReportDTO inputDTO = new GymReportDTO();
        inputDTO.setTrainerUserName("trainerUser");
        inputDTO.setTrainerFirstName("John");
        inputDTO.setTrainerLastName("Doe");
        inputDTO.setActive(true);
        inputDTO.setDate(LocalDate.of(2023, 8, 11));
        inputDTO.setDuration(60);

        TrainingReport existingReport = new TrainingReport();
        existingReport.setTrainerUsername("trainerUser");
        existingReport.setTrainerFirstName("John");
        existingReport.setTrainerLastName("Doe");
        existingReport.setTrainerIsActive(true);
        existingReport.setDurationSummary(new HashMap<>());

        Mockito.when(gymRepository.findById("trainerUser")).thenReturn(Optional.ofNullable(null));

        when(gymRepository.save(Mockito.<TrainingReport>any()))
                .thenReturn(existingReport);


        GymReportDTO resultDTO = gymReportService.addReport(inputDTO);

        assertEquals("trainerUser", resultDTO.getTrainerUserName());
        assertEquals("John", resultDTO.getTrainerFirstName());
        assertEquals("Doe", resultDTO.getTrainerLastName());
        assertEquals(true, resultDTO.isActive());

        Mockito.verify(gymRepository).findById("trainerUser");
        Mockito.verify(gymRepository).save(Mockito.<TrainingReport>any());
    }

    // Add more test cases to cover different scenarios and edge cases
}


