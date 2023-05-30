package com.example.desafiobackvotos.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "SESSION_VOTE")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class SessionVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionVoteId;

    @OneToOne
    @JoinColumn(name = "poll_id")
    private Poll poll;

    private Boolean isSessionOpen;

    private Long startedTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SessionVote that = (SessionVote) o;
        return getSessionVoteId() != null && Objects.equals(getSessionVoteId(), that.getSessionVoteId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
