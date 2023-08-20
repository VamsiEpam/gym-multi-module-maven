package com.epam.gymreportapplication.consumer;

import com.epam.gymapp.dto.report.GymReportDTO;
import com.epam.gymreportapplication.kafka.ReportConsumer;
import com.epam.gymreportapplication.service.GymReportServiceImpl;
import com.epam.gymreportapplication.service.GymReportServiceImplTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ReportConsumerTest {
    @Mock
    private GymReportServiceImpl gymReportService;

    @InjectMocks
    ReportConsumer reportConsumer;


    @Test
    void testConsumer(){
        Mockito.when(gymReportService.addReport(any())).thenReturn(new GymReportDTO());

        reportConsumer.getReportFromKafka(new GymReportDTO());
    }

}
