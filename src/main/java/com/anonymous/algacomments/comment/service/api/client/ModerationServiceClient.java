package com.anonymous.algacomments.comment.service.api.client;

import com.anonymous.algacomments.comment.service.api.model.ModerationInput;
import com.anonymous.algacomments.comment.service.api.model.ModerationOutput;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/api/moderate")
public interface ModerationServiceClient {
    @PostExchange
    ModerationOutput getModeration(@RequestBody ModerationInput input);
}
