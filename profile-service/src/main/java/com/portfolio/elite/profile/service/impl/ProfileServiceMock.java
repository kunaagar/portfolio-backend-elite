package com.portfolio.elite.profile.service.impl;

import com.portfolio.elite.errors.exception.ResourceNotFoundException;
import com.portfolio.elite.profile.service.IProfileService;
import com.portfolio.elite.profileservice.v1.model.GetProfileResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("mock")
@Slf4j
public class ProfileServiceMock implements IProfileService {

    @Override
    public GetProfileResponse findProfileById(String id) {
        log.error("Profile with id [{}] is not found", id);
        throw new ResourceNotFoundException("Profile with id ["+id+"] is not found");
    }

    @PostConstruct
    protected void log() {
        log.debug("ProfileService is activated in mock mode");
    }
}
