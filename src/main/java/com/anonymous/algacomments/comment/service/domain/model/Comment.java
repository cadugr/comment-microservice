package com.anonymous.algacomments.comment.service.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @AttributeOverride(name = "value",
            column = @Column(name = "id", columnDefinition = "uuid"))
    private CommentId id;
    private String text;
    private String author;
    @CreationTimestamp
    private OffsetDateTime createdAt;
}
