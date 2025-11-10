package com.portfolio.elite.customerservice.provider;

import com.portfolio.elite.clients.profileservice.v1.model.GetProfileResponse;
import java.util.Optional;

public interface ProfileServiceProvider {

    Optional<GetProfileResponse> getProfileById(String id);

}
