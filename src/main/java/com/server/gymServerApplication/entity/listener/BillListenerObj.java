package com.server.gymServerApplication.entity.listener;

import com.server.gymServerApplication.entity.mysql.Bill;
import com.server.gymServerApplication.infor.MEMBERSHIP_TYPE;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.util.Calendar;
import java.util.Date;

public class BillListenerObj {

    @PrePersist
    @PreUpdate
    private void init(Bill bill) {
        double total = bill.getService().getPrice() * bill.getQuantity();
        bill.setTotal_amount(total);

        // ngay bat dau dang ki
        Date startdate = new Date();  // ngay giao dich

        // ngay het han dang ki
        int valueOfService = bill.getService().getValueTimeout() * bill.getQuantity();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startdate);
        calendar.add(Calendar.DAY_OF_YEAR, valueOfService);
        Date endDate = calendar.getTime();

        bill.getMembership().setEndDate(endDate);
        bill.getMembership().setStartDate(startdate);
        bill.getMembership().setService(bill.getService());
        bill.getMembership().setUser(bill.getUser());

        int totalNumberOfTransactions = (int) (bill.getUser().getBills().stream().mapToDouble(Bill::getTotal_amount).sum());


        MEMBERSHIP_TYPE membershipType = switch (totalNumberOfTransactions) {
            default -> {
                if (totalNumberOfTransactions < 1000) yield MEMBERSHIP_TYPE.SILVER;
                else if (totalNumberOfTransactions < 5000) yield MEMBERSHIP_TYPE.GOLD;
                else yield MEMBERSHIP_TYPE.DIAMOND;
            }
        };
        bill.getMembership().setMembershipType(membershipType);
    }
}
