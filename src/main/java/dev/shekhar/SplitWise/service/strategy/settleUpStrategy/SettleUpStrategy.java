package dev.shekhar.SplitWise.service.strategy.settleUpStrategy;

import dev.shekhar.SplitWise.entity.Expense;
import dev.shekhar.SplitWise.entity.SettlementTransaction;

import java.util.List;

public interface SettleUpStrategy {
    List<SettlementTransaction> getSettlementTransactions(List<Expense> expenses);
}
