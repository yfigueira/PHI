package com.example.backend.friendrequest.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "friend_request")
public class FriendRequestEntity {

    @Id
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Version
    @Column(name = "version")
    private Long version;

    @Column(name = "sender")
    private UUID sender;

    @Column(name = "receiver")
    private UUID receiver;

    @Column(name = "status")
    @Convert(converter = RequestStatusConverter.class)
    private Integer status;
}
