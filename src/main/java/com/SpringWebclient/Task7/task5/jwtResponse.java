package com.SpringWebclient.Task7.task5;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class jwtResponse {
    private  String AccessToken;
    private  String refreshToken;
}
