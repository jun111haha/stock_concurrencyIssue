package com.example.stock_concurrencyissue.facade;

import com.example.stock_concurrencyissue.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;

    public void decrease(Long key, Long quantity) throws InterruptedException {
        RLock rLock = redissonClient.getLock(key.toString());

        try {
           boolean available =  rLock.tryLock(5, 1, TimeUnit.SECONDS);

           if(!available){
               System.out.println("Lock 획득 실패");
               return;
           }

            stockService.decrease(key, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        } finally {
            rLock.unlock();
        }

    }
}
