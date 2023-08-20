package com.epam.gymapp.dto.report;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GymReportDTO {
    private String trainerUserName;

    private String trainerFirstName;

    private String trainerLastName;

    private LocalDate date;

    private boolean isActive;

    private int duration;

}
