package com.frailty.backend.service;

import com.frailty.backend.entity.AppUser;
import com.frailty.backend.entity.Result;
import com.frailty.backend.repository.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ResultsService {
    private AppUserService appUserService;
    private ResultRepository resultRepository;

    public List<Result> getResults(String username, String email) {
        AppUser clinician = appUserService.getValidUser(username);
        AppUser patient = appUserService.getValidUser(email);
        return resultRepository.findByAppUser(patient);
    }
}
