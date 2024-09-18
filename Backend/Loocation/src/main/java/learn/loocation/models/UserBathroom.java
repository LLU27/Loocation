package learn.loocation.models;

import java.util.Objects;

public class UserBathroom
{
    private int userId;
    private int bathroomId;

    public UserBathroom() {
    }

    public UserBathroom(int userId, int bathroomId) {
        this.userId = userId;
        this.bathroomId = bathroomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBathroomId() {
        return bathroomId;
    }

    public void setBathroomId(int bathroomId) {
        this.bathroomId = bathroomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBathroom that = (UserBathroom) o;
        return userId == that.userId && bathroomId == that.bathroomId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, bathroomId);
    }
}