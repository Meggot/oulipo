package com.common.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by bradleyw on 25/03/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Password{

    private int id;
    private String salt;
    private String hashValue;
}
