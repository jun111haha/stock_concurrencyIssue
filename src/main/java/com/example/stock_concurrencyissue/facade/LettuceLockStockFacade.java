package com.example.stock_concurrencyissue.facade;

import com.example.stock_concurrencyissue.repository.RedisLockRepository;
import com.example.stock_concurrencyissue.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;

    private final StockService stockService;

    public void decrease(Long key, Long quantity) throws InterruptedException {

        while (!redisLockRepository.lock(key)){
            Thread.sleep(100);
        }

        try {
            stockService.decrease(key, quantity);
        } finally {
            redisLockRepository.unLock(key);
        }
    }

}
