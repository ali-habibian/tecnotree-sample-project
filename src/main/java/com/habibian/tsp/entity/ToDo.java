package com.habibian.tsp.entity;

import lombok.*;

import javax.persistence.*;

/**
 * @author Ali
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "completed", nullable = false)
    private Boolean completed = false;

    public ToDo(Long userId, String title) {
        this.userId = userId;
        this.title = title;
    }
}
