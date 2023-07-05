package com.example.stock_concurrencyissue.facade;

import com.example.stock_concurrencyissue.service.OptimisticLockService;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockStockFacade {

    private OptimisticLockService optimisticLockService;

    public OptimisticLockStockFacade(OptimisticLockService optimisticLockService){
        this.optimisticLockService = optimisticLockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException{
        while (true){
            try {
                optimisticLockService.decrease(id, quantity);

                break;
            } catch (Exception e){
                Thread.sleep(50);
            }
        }
    }

}
