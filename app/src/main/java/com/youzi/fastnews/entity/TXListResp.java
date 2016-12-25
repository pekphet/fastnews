package com.youzi.fastnews.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pekphet on 16/12/25.
 */

public class TXListResp {
    private List<TXListE> withdrawal_list = new ArrayList<>();

    public List<TXListE> getWithdrawal_list() {
        return withdrawal_list;
    }

    public void setWithdrawal_list(List<TXListE> withdrawal_list) {
        this.withdrawal_list = withdrawal_list;
    }
}
