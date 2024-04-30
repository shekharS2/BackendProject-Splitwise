package dev.shekhar.SplitWise.service;


import dev.shekhar.SplitWise.entity.SettlementTransaction;

import java.util.List;

public interface GroupService {
     List<SettlementTransaction>  settleUp(int groupId);

}
