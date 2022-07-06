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
public class CommentCreateParam {
    private long postId;
    private String name;
    private String email;
    private String body;
}
