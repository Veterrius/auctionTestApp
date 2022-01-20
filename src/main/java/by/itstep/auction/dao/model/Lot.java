package by.itstep.auction.dao.model;

import by.itstep.auction.dao.model.enums.LotType;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "lot")
@Entity
public class Lot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item_id")
    private Item item;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User seller;

    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;

    @Column(name = "type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LotType lotType;

    public LotType getLotType() {
        return lotType;
    }

    public void setLotType(LotType lotType) {
        this.lotType = lotType;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User owner) {
        this.seller = owner;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
