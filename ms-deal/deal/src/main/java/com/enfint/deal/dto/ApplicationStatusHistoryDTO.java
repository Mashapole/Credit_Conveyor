package com.enfint.deal.dto;

import com.enfint.deal.dto.enumm.ChangeType;
import com.enfint.deal.entity.model.ApplicationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@AllArgsConstructor
public class ApplicationStatusHistoryDTO {

    private ApplicationStatus status;
    private LocalDateTime time;
    private ChangeType changeType;
}
