package com.habibian.tsp.dto;

import lombok.*;

/**
 * @author Ali
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostCreateParam {
    private Long userId;
    private String title;
    private String body;
}
