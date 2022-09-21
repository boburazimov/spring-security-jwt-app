package uz.springsecurityapp.domain.enumeration;

public enum Permissions {

    ADMIN_RESOURCE_CREATE("user:create"),
    ADMIN_RESOURCE_READ("user:create");

    Permissions(String s) {
    }
}
