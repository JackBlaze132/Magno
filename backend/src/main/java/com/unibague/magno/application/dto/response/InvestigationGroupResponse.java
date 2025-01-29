package com.unibague.magno.application.dto.response;

import com.unibague.magno.domain.model.LineOfResearch;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class InvestigationGroupResponse {

    private Long id;
    private String name;
    private Set<LineOfResearch> linesOfResearch;
}
