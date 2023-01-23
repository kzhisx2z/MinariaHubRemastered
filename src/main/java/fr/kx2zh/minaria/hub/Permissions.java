package fr.kx2zh.minaria.hub;

public enum Permissions {

    DOUBLE_JUMP("minaria.doublejump");

    private final String permission;

    Permissions(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
