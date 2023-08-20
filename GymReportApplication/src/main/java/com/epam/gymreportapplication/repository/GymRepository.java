package com.epam.gymreportapplication.repository;

import com.epam.gymreportapplication.model.TrainingReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GymRepository extends MongoRepository<TrainingReport,String> {
}
