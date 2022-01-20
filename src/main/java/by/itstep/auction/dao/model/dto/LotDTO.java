package by.itstep.auction.dao.model.dto;

import by.itstep.auction.dao.model.enums.LotType;

public class LotDTO {
    private Long itemId;
    private LotType type;
    private Long validity;


    public Long getValidity() {
        return validity;
    }

    public void setValidity(Long validity) {
        this.validity = validity;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public LotType getType() {
        return type;
    }

    public void setType(LotType type) {
        this.type = type;
    }
}
