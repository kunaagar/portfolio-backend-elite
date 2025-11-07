package com.portfolio.elite.profile.service;

import com.portfolio.elite.profileservice.v1.model.GetProfileResponse;

public interface IProfileService {

    GetProfileResponse findProfileById(String id);
}
