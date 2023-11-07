package com.mobileapp.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLikePost {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_user_like_post")
    private User userLikePost;
    @Id
    @ManyToOne
    @JoinColumn(name = "id_post_is_like")
    private Post PostIsUserLike;
}
