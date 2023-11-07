package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostReaded {
    @Id
    @ManyToOne
    @JoinColumn(name="idUserRead")
    private User userRead;
    @Id
    @ManyToOne
    @JoinColumn(name = "idPostBeenRead")
    private Post postBeenRead;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
}
