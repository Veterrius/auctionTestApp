package by.itstep.auction.dao.model.enums;

public enum Permission {
    ITEMS_READ("items:read"),
    ITEMS_WRITE("items:write"),
    USERS_READ("users:read"),
    USERS_WRITE("users:write"),
    USERS_MONEY("users:money"),
    LOTS_READ("lots:read"),
    LOTS_WRITE("lots:write"),
    LOBBY_ALL("lobby:all"),
    TRANSACTION("transaction");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
