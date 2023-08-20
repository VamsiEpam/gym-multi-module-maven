package com.epam.gymapp.controller;

import com.epam.gymapp.dto.TrainingOverallDTO;
import com.epam.gymapp.dto.request.TrainingRequest;
import com.epam.gymapp.kafka.NotificationProducer;
import com.epam.gymapp.kafka.ReportProducer;
import com.epam.gymapp.model.StringConstants;
import com.epam.gymapp.service.TrainingServiceImpl;
import com.epam.gymapp.helper.NotificationMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/gym/trainings")
@RestController
@RequiredArgsConstructor
public class TrainingController {


    private final TrainingServiceImpl trainingService;


    private final ReportProducer reportProducer;

    private final NotificationProducer notificationProducer;



    @PostMapping
    public ResponseEntity<Void> addTraining(@Valid @RequestBody TrainingRequest trainingDetails) throws JsonProcessingException {
        log.info(StringConstants.START_CONTROLLER_METHOD.getValue(), "addTraining", this.getClass().getName(), trainingDetails);
        TrainingOverallDTO trainingOverallDTO = trainingService.addTraining(trainingDetails);
        reportProducer.sendGymReport(trainingOverallDTO.getGymReportDTO());
        notificationProducer.sendTrainingNotification(NotificationMapper.getNotificationDTO(trainingOverallDTO.getTrainingMailDTO()));
        log.info(StringConstants.EXIT_CONTROLLER_METHOD.getValue(), "addTraining");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<TrainingRequest> getTraining(@Valid @RequestParam String trainingName) throws JsonProcessingException {
        log.info(StringConstants.START_CONTROLLER_METHOD.getValue(), "getTraining", this.getClass().getName(), trainingName);
        TrainingRequest trainingRequest = trainingService.getTraining(trainingName);
        log.info(StringConstants.EXIT_CONTROLLER_METHOD.getValue(), "getTraining");
        return new ResponseEntity<>(trainingRequest,HttpStatus.OK);
    }
}
