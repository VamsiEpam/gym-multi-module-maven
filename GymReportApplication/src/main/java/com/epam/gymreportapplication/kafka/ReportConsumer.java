package com.epam.gymreportapplication.kafka;

import com.epam.gymapp.dto.report.GymReportDTO;
import com.epam.gymreportapplication.service.GymReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportConsumer {

    private final GymReportServiceImpl gymReportService;


    @KafkaListener(topics = "report", groupId = "report-service")
    public void getReportFromKafka(GymReportDTO gymReportDTO) {
        gymReportService.addReport(gymReportDTO);
    }
}

