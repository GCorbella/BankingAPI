package com.bank.bank.controllers;

import com.bank.bank.controllers.dto.AccountHolderDTO;
import com.bank.bank.models.ThirdParty;
import com.bank.bank.services.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class ThirdPartyController {

    @Autowired
    ThirdPartyService thirdPartyService;

    public ThirdPartyController(ThirdPartyService thirdPartyService) {
        this.thirdPartyService = thirdPartyService;
    }


    @PostMapping("/new-third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestParam String name, @RequestParam String hashedKey) {
        return thirdPartyService.createThirdParty(name, hashedKey);
    }

    @PatchMapping("/send/{hashedKey}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void receiveFromThirdParty(@PathVariable String hashedKey,
                                      @RequestParam int amount,
                                      @RequestParam String accountId,
                                      @RequestParam String accountSecretKey) {
        thirdPartyService.receiveFromThirdParty(hashedKey, amount, accountId, accountSecretKey);
    }
}
