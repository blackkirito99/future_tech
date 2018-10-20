package domain;

import auth.AppSession;

public class Retailer extends User {
    public static final String TypeString = AppSession.RETAILER_ROLE;
    public Retailer(int id, String username, String type, String email, String avatar, boolean newCreated) {
        super(id, username, type, email, avatar, newCreated);
    }
    @Override
    public String getType() {
        return TypeString;
    }

}