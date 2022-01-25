package by.itstep.auction.service;

import by.itstep.auction.dao.model.Lot;
import by.itstep.auction.dao.model.enums.LotType;
import by.itstep.auction.service.exceptions.AutoSellException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LobbyAutoSellerThread extends Thread{

    private final LobbyService lobbyService;
    private final LotService lotService;

    public LobbyAutoSellerThread(LobbyService lobbyService, LotService lotService) {
        this.lobbyService = lobbyService;
        this.lotService = lotService;
    }

    @Override
    public void run() {
        while (true) {
            List<Lot> lots = (List<Lot>) lotService.findAllLots();
            for (Lot lot : lots) {
                if (LotType.LOBBY.equals(lot.getLotType())) {
                    if (lot.getExpirationTime().isBefore(LocalDateTime.now())) {
                        try {
                            lobbyService.lobbyAutoSell(lot);
                        } catch (AutoSellException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
