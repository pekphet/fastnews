package com.youzi.fastnews.entity;

import java.util.List;

/**
 * Created by fish on 16-12-28.
 */

public class InvRankResp {
    private List<InvRankE>  invite_rank;
    private String          rule;

    public List<InvRankE> getInvite_rank() {
        return invite_rank;
    }

    public void setInvite_rank(List<InvRankE> invite_rank) {
        this.invite_rank = invite_rank;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
}
