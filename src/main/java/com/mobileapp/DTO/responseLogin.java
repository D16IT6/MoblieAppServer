package com.mobileapp.DTO;

import lombok.Data;

@Data
public class responseLogin {
    private String jwtToken ;
    private boolean status;
}
