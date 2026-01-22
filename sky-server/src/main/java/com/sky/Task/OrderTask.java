package com.sky.Task;

import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;


    @Scheduled(cron = "0 0 * * * *")
    public void processTimeoutTask(){
        Integer status = Orders.PENDING_PAYMENT;
        LocalDateTime time = LocalDateTime.now().plusMinutes(-15);
        List<Orders> list = orderMapper.getByStatusandOrderTime(status,time);
        if(list.size() > 0 && list != null) {
            for (Orders orders : list) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("订单超时");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void processDliveryTask(){
        Integer status = Orders.DELIVERY_IN_PROGRESS;
        LocalDateTime time = LocalDateTime.now();
        time = time.plusMinutes(-60);
        List<Orders> list = orderMapper.getByStatusandOrderTime(status,time);
        if(list.size() > 0 && list != null) {
            for (Orders orders : list) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelTime(LocalDateTime.now());
                orders.setCancelReason("订单超时");
                orderMapper.update(orders);
            }
        }
    }
}

