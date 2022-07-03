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
public class ToDoDto {
    private long id;
    private long userId;
    private String title;
    private boolean completed;
}
