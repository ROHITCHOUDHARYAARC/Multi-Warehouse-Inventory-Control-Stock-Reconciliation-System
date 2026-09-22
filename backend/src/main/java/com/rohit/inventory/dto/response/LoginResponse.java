package com.rohit.inventory.dto.response;
public record LoginResponse(String accessToken, String tokenType, long expiresIn, UserResponse user) { }
