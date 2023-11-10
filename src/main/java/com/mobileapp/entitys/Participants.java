package com.mobileapp.entitys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
public class Participants {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Participant_id", nullable = false)
    private int participantId;
    @ManyToOne
    @JoinColumn(name = "conversationsId")
    private Conversations conversationsParticipants;
    @ManyToOne
    @JoinColumn(name = "UserID")
    private User userParticipants;
    @Basic
    @Column(name = "Role", nullable = true, length = 10)
    private String role;
    @Basic
    @Column(name = "NicknameUser", nullable = true, length = 50,columnDefinition = "nvarchar(50)")
    private String nicknameUser;
    @OneToMany(mappedBy = "senderUser")
    private Set<Message> messageSet;
    @Basic
    @Column(name = "is_hide", nullable = true)
    private boolean isHide;
}
