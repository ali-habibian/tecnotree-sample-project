package com.habibian.tsp.dto;

import lombok.*;

/**
 * @author Ali
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PostDto {
    private long id;
    private long userId;
    private String title;
    private String body;
}
