package com.myproject2.demo.entity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * @author Ramesh Fadatare
 *
 */

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;

    @Size(min = 10, message = "Enter at least 10 Characters...")
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate targetDate;

    public Todo() {
        super();
    }

    public Todo(String user, String desc, LocalDate targetDate, boolean isDone) {
        super();
        this.userName = user;
        this.description = desc;
        this.targetDate = targetDate;
    }


}