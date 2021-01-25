package com.bytebanana.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class VoteResponse {
    public Long voteCount;
    public Boolean currentUserVoted;
}
