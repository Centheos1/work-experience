package com.fitnessplayground.service;

import com.fitnessplayground.dao.CommunicationsDto.InternalCommsMCNotesResponse;
import com.fitnessplayground.dao.domain.temp.UIGym;

public interface CommunicationsService {

    InternalCommsMCNotesResponse getMCNotesComms(UIGym uiGym);
}
