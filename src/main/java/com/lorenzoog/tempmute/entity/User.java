package com.lorenzoog.tempmute.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class User {

    private final String username;
    private boolean isMuted;

}
