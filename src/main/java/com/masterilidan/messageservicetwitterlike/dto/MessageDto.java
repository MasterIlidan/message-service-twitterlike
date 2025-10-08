package com.masterilidan.messageservicetwitterlike.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDto {
    private long id;
    private String body;
    private long createdBy;
    private String createdAt;
}
