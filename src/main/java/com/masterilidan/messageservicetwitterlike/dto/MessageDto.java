package com.masterilidan.messageservicetwitterlike.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MessageDto {
    @ToString.Include
    private long id;
    @ToString.Include
    private String body;
    @ToString.Include
    private long createdBy;
    @ToString.Include
    private String createdAt;
}
