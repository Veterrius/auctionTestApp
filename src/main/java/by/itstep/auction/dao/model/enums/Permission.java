package by.itstep.auction.dao.model.enums;

public enum Permission {
    ITEMS_READ("items:read"),
    ITEMS_WRITE("items:write");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
