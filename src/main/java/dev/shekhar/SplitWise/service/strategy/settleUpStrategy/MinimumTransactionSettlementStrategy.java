package dev.shekhar.SplitWise.service.strategy.settleUpStrategy;

import dev.shekhar.SplitWise.dto.UserAmount;
import dev.shekhar.SplitWise.entity.Expense;
import dev.shekhar.SplitWise.entity.SettlementTransaction;
import dev.shekhar.SplitWise.entity.User;
import dev.shekhar.SplitWise.entity.UserExpense;
import dev.shekhar.SplitWise.entity.constants.UserExpenseType;

import java.util.*;

public class MinimumTransactionSettlementStrategy implements SettleUpStrategy {
    @Override
    public List<SettlementTransaction> getSettlementTransactions(List<Expense> expenses) {
        HashMap<User, Double> map = getOutstandingBalances(expenses);
        Comparator<UserAmount> minHeapComparator = Comparator.comparingDouble(UserAmount::getAmount);
        PriorityQueue<UserAmount> minHeap = new PriorityQueue<>(minHeapComparator);
        Comparator<UserAmount> maxHeapComparator = Comparator.comparingDouble(UserAmount::getAmount).reversed();
        PriorityQueue<UserAmount> maxHeap = new PriorityQueue<>(maxHeapComparator);

        for(Map.Entry<User, Double> entry : map.entrySet()) {
            if(entry.getValue() < 0) {
                minHeap.add(new UserAmount(entry.getKey(), entry.getValue()));
            } if(entry.getValue() < 0) {
                maxHeap.add(new UserAmount(entry.getKey(), entry.getValue()));
            } else {
                System.out.println("User does not need to participate in settle up ");
            }
        }

        List<SettlementTransaction> settlementTransactions = new ArrayList<>();
        while(!minHeap.isEmpty() && !maxHeap.isEmpty()) {
            UserAmount borrower = minHeap.poll();
            UserAmount lender = maxHeap.poll();
            if(Math.abs(borrower.getAmount()) > Math.abs(lender.getAmount())) {
                borrower.setAmount(borrower.getAmount() + lender.getAmount());
                minHeap.add(borrower);
                SettlementTransaction settlementTransaction = new SettlementTransaction(borrower.getUser(), lender.getUser(), lender.getAmount());
                settlementTransactions.add(settlementTransaction);
            } if(Math.abs(borrower.getAmount()) < Math.abs(lender.getAmount())) {
                lender.setAmount(lender.getAmount() + borrower.getAmount());
                maxHeap.add(lender);
                SettlementTransaction settlementTransaction = new SettlementTransaction(borrower.getUser(), lender.getUser(), Math.abs(borrower.getAmount()));
                settlementTransactions.add(settlementTransaction);
            } else {
                System.out.println("Do nothing, both are equal");
                SettlementTransaction settlementTransaction = new SettlementTransaction(borrower.getUser(), lender.getUser(), lender.getAmount());
                settlementTransactions.add(settlementTransaction);
            }
        }
        return settlementTransactions;
    }

    private HashMap<User, Double> getOutstandingBalances(List<Expense> expenses) {
        HashMap<User, Double> expenseMap = new HashMap<>();
        for(Expense expense : expenses) {
            for(UserExpense userExpense : expense.getUserExpenses()) {
                User participant = userExpense.getUser();
                double amount = userExpense.getAmount();
                if(expenseMap.containsKey(participant)) {
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                        expenseMap.put(participant, expenseMap.get(participant) + amount);
                    } else if(userExpense.getUserExpenseType().equals(UserExpenseType.HAVE_TO_PAY)) {
                        expenseMap.put(participant, expenseMap.get(participant) - amount);
                    }
                } else {
                    if(userExpense.getUserExpenseType().equals(UserExpenseType.PAID)) {
                        expenseMap.put(participant, 0 + amount);
                    } else if(userExpense.getUserExpenseType().equals(UserExpenseType.HAVE_TO_PAY)) {
                        expenseMap.put(participant, 0 - amount);
                    }
                }
            }
        }
        return expenseMap;
    }
}
