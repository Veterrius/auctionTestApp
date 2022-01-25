package by.itstep.auction.service;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.service.exceptions.AutoSellException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LotAutoSellerThread extends Thread{

    private final LotService lotService;

    public LotAutoSellerThread(LotService lotService) {
        this.lotService = lotService;
    }

    @Override
    public void run() {
        while (true) {
            List<Lot> lots = (List<Lot>) lotService.findAllLots();
            for (Lot lot : lots) {
                if (LotType.DYNAMIC.equals(lot.getLotType())) {
                    if (lot.getExpirationTime().isBefore(LocalDateTime.now())) {
                        try {
                            lotService.autoSell(lot);
                        } catch (AutoSellException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
