package com.epam.gymapp.service;

import com.epam.gymapp.customexceptions.TraineeException;
import com.epam.gymapp.customexceptions.UserException;
import com.epam.gymapp.dto.TrainingOverallDTO;
import com.epam.gymapp.dto.report.GymReportDTO;
import com.epam.gymapp.dto.report.TrainingMailDTO;
import com.epam.gymapp.dto.request.TrainingRequest;
import com.epam.gymapp.model.*;
import com.epam.gymapp.repository.TrainingRepository;
import com.epam.gymapp.helper.ValueMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService{



    private final TrainingRepository trainingRepository;

    private final UserServiceImpl userService;

    private final TrainingTypeServiceImpl trainingTypeService;

    @Override
    public TrainingOverallDTO addTraining(TrainingRequest trainingDetails) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(), ADD_TRAINING_TYPE,this.getClass().getName(),trainingDetails);
        Trainer trainer = Optional.of(userService.getUser(trainingDetails.getTrainerUserName()).getTrainer()).orElseThrow(() -> new UserException("No Trainer with that username"));
        Trainee trainee = Optional.of(userService.getUser(trainingDetails.getTraineeUserName()).getTrainee()).orElseThrow(() -> new UserException("No Trainee with that username"));
        if(trainee.getTrainerList().contains(trainer)) {
           Training training = new Training();
                training.setTrainingType(trainer.getSpecialization());
                training.setTrainee(trainee);
                training.setTrainer(trainer);
                training.setName(trainingDetails.getTrainingName());
                training.setDate(trainingDetails.getTrainingDate());
                training.setDuration(trainingDetails.getTrainingDuration());
                trainingRepository.save(training);

                TrainingOverallDTO trainingOverallDTO = new TrainingOverallDTO();
                //add report
                GymReportDTO gymReportDTO = ValueMapper.trainingToGymReportDTO(trainer);
                gymReportDTO.setDate(training.getDate());
                gymReportDTO.setDuration(training.getDuration());
                trainingOverallDTO.setGymReportDTO(gymReportDTO);

                //sendNotification
                TrainingMailDTO trainingMailDTO = TrainingMailDTO.builder()
                        .trainingName(trainingDetails.getTrainingName())
                        .trainerName(trainer.getUser().getUserName())
                        .traineeName(trainee.getUser().getUserName())
                        .trainerEmail(trainer.getUser().getEmail())
                        .traineeEmail(trainee.getUser().getEmail())
                        .build();
                trainingOverallDTO.setTrainingMailDTO(trainingMailDTO);
                log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(), ADD_TRAINING_TYPE);
                return trainingOverallDTO;
        }
        else{
            log.info(StringConstants.ERROR_MESSAGE.getValue(), "addTraining", this.getClass().getName());
                throw new TraineeException("Trainer not assigned to Trainee");

        }
    }

    @Override
    public List<Training> getTrainingsForTrainee(Trainee trainee, LocalDate startDate, LocalDate endDate, int trainingTypeId) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(), ADD_TRAINING_TYPE,this.getClass().getName(),trainee);
        TrainingType trainingType = null;
        try{
            trainingType = trainingTypeService.getTrainingType(trainingTypeId);
        } catch ( UserException ignored)
        {
            //ignored
        }
        List<Training> trainingList = trainingRepository.findAllTrainingInBetween(startDate,endDate, trainee,trainingType);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(), ADD_TRAINING_TYPE);
        return trainingList;
    }

    @Override
    public List<Training> getTrainingsForTrainer(Trainer trainer, LocalDate startDate, LocalDate endDate, String userName) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(),"getTrainingsForTrainer",this.getClass().getName(),trainer);
        Trainee trainee = null;
        try{
            trainee = userService.getUser(userName).getTrainee();
        }
        catch (UserException ignored){
            //ignored
        }
        List<Training> trainingList = trainingRepository.findAllTrainingInBetween(startDate,endDate,trainer, trainee);
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(),"getTrainingsForTrainer");
        return trainingList;
    }

    @Override
    public TrainingRequest getTraining(String trainingName) {
        log.info(StringConstants.START_SERVICE_METHOD.getValue(), "getTraining",this.getClass().getName(),trainingName);
        Training training = Optional.of(trainingRepository.findByName(trainingName)).orElseThrow(()-> new TraineeException("No Training with that name"));
        TrainingRequest trainingRequest = TrainingRequest.builder()
                .trainingDate(training.getDate())
                .trainingDuration(training.getDuration())
                .traineeUserName(training.getTrainee().getUser().getUserName())
                .trainingName(training.getName())
                .trainerUserName(training.getTrainer().getUser().getUserName())
                .build();
        log.info(StringConstants.EXIT_SERVICE_METHOD.getValue(), "getTraining");
        return trainingRequest;
    }
}
