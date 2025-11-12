package com.portfolio.elite.customerservice.provider.impl;

import com.portfolio.elite.clients.profileservice.v1.api.ProfilesApiClient;
import com.portfolio.elite.clients.profileservice.v1.model.GetProfileResponse;
import com.portfolio.elite.customerservice.provider.ProfileServiceProvider;
import io.micrometer.core.annotation.Timed;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
@RequiredArgsConstructor
public class ProfileServiceProviderImpl implements ProfileServiceProvider {

    private final ProfilesApiClient client;

    @Timed(value = "feign.call.profile.api", description = "Time taken to call profile service api")
    @Override
    public Optional<GetProfileResponse> getProfileById(String id) {
        final var response = client.getProfileById(id);
        if (!Objects.isNull(response) && response.getStatusCode().is2xxSuccessful()) {
            assert response.getBody() != null;
            return Optional.of(response.getBody());
        } else if (response.getStatusCode().is4xxClientError()) {
            throw new HttpClientErrorException(response.getStatusCode(),"4xx error occurred");
        } else if (response.getStatusCode().is5xxServerError()) {
            throw new HttpServerErrorException(response.getStatusCode(),"5xx error occurred");
        }
        return Optional.empty();
    }
}
