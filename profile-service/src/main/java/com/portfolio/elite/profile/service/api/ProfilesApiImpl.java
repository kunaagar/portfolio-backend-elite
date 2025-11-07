package com.portfolio.elite.profile.service.api;

import com.portfolio.elite.profile.service.IProfileService;
import com.portfolio.elite.profileservice.v1.api.ProfilesApi;
import com.portfolio.elite.profileservice.v1.model.GetProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProfilesApiImpl implements ProfilesApi {

    private final IProfileService profileService;

    @Override
    public ResponseEntity<GetProfileResponse> getProfileById(String id) {
        log.debug("Get Profile by id {}", id);
        return ResponseEntity.ok(profileService.findProfileById(id));
    }
}
