package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Conversations {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "conversationsId", nullable = false)
    private int conversationsId;
    @Basic
    @Column(name = "conversationsName", nullable = true, length = 100,columnDefinition = "nvarchar(100)")
    private String conversationsName;
    @Basic
    @Column(name = "type", nullable = true, length = 30,columnDefinition = "nvarchar(30)")
    private String type;
    @ManyToOne
    @JoinColumn(name = "creatorUserID")
    private User creatorUser;
    @OneToMany(mappedBy = "conversationsParticipants")
    private Set<Participants> participantsSet;
    @OneToMany(mappedBy = "conversationsMessage")
    private Set<Message> messages;
}
