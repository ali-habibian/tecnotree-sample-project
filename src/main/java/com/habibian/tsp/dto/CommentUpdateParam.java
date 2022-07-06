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
public class CommentUpdateParam {
    private String name;
    private String email;
    private String body;
}
