package com.epam.gymreportapplication.service;

import com.epam.gymapp.dto.report.GymReportDTO;
import com.epam.gymreportapplication.model.TrainingReport;
import com.epam.gymreportapplication.model.StringConstants;
import com.epam.gymreportapplication.repository.GymRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class GymReportServiceImpl implements GymReportService{

    private final GymRepository gymRepository;

    @Override
    public GymReportDTO addReport(GymReportDTO gymReportDTO) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"addReport",this.getClass().getName(), gymReportDTO);
        TrainingReport trainingReport = gymRepository.findById(gymReportDTO.getTrainerUserName())
                .orElseGet(() -> new TrainingReport(
                        gymReportDTO.getTrainerUserName(),
                        gymReportDTO.getTrainerFirstName(),
                        gymReportDTO.getTrainerLastName(),
                        gymReportDTO.isActive(),
                        new HashMap<>()));

        LocalDate trainingDate = gymReportDTO.getDate();

        long year = trainingDate.getYear();
        long month = trainingDate.getMonthValue();
        long day = trainingDate.getDayOfMonth();
        long trainingDuration = gymReportDTO.getDuration();

        trainingReport.getDurationSummary()
                .computeIfAbsent(year, k -> new HashMap<>())
                .computeIfAbsent(month, k -> new HashMap<>())
                .computeIfAbsent(day, k -> new HashMap<>())
                .put(trainingDate.toString(), trainingDuration);

        TrainingReport reportDetails = gymRepository.save(trainingReport);
        GymReportDTO updatedGymReportDTO = GymReportDTO.builder()
                .trainerUserName(reportDetails.getTrainerUsername())
                        .trainerFirstName(reportDetails.getTrainerFirstName())
                                .trainerLastName(reportDetails.getTrainerLastName())
                                        .isActive(reportDetails.isTrainerIsActive())
                                            .build();
       log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(), "addReport");
        return updatedGymReportDTO;
    }
}
