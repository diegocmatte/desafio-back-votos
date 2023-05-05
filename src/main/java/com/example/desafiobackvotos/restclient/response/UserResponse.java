package com.example.desafiobackvotos.restclient.response;

import com.example.desafiobackvotos.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {

    private String status;

    public Boolean isAbleToVote(){
        return getStatus().equalsIgnoreCase(Constants.ABLE_TO_VOTE);
    }
}
