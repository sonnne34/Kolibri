package kolibri.example.kolibri.Main2Activity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Ticket implements Parcelable {

    private List<String> categories = new ArrayList<>();
    private String logo;
    private String name;
    private String picture;
    private long timeDelivery;
    private String timeWork;
    private String timeWorkStart;
    private String timeWorkEnd;
    private long fromSumDelivery;
    private String id;

    public Ticket(List<String> categories, String logo, String name, String picture, long timeDelivery,
                  String id, String timeWork, String timeWorkEnd, String timeWorkStart, long fromSumDelivery) {
        this.categories = categories;
        this.logo = logo;
        this.name = name;
        this.picture = picture;
        this.timeDelivery = timeDelivery;
        this.id = id;
        this.fromSumDelivery = fromSumDelivery;
        this.timeWork = timeWork;
        this.timeWorkStart = timeWorkStart;
        this.timeWorkEnd = timeWorkEnd;
    }

    public Ticket() {
    }

    protected Ticket(Parcel in) {
        categories = in.createStringArrayList();
        logo = in.readString();
        name = in.readString();
        picture = in.readString();
        timeDelivery = in.readLong();
        id = in.readString();
        timeWork = in.readString();
        timeWorkEnd = in.readString();
        timeWorkStart = in.readString();
        fromSumDelivery = in.readLong();
    }

    public static final Creator <Ticket> CREATOR = new Creator <Ticket>() {
        @Override
        public Ticket createFromParcel(Parcel in) {
            return new Ticket(in);
        }

        @Override
        public Ticket[] newArray(int size) {
            return new Ticket[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(categories);
        dest.writeString(logo);
        dest.writeString(name);
        dest.writeString(picture);
        dest.writeLong(timeDelivery);
        dest.writeString(id);
        dest.writeString(timeWork);
        dest.writeString(timeWorkEnd);
        dest.writeString(timeWorkStart);
        dest.writeLong(fromSumDelivery);
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public long getTimeDelivery() {
        return timeDelivery;
    }

    public void setTimeDelivery(long timeDelivery) {
        this.timeDelivery = timeDelivery;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getFromSumDelivery() {
        return fromSumDelivery;
    }

    public void setFromSumDelivery(long fromSumDelivery) {
        this.fromSumDelivery = fromSumDelivery;
    }

    public String getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(String timeWork) {
        this.timeWork = timeWork;
    }

    public String getTimeWorkStart() {
        return timeWorkStart;
    }

    public void setTimeWorkStart(String timeWorkStart) {
        this.timeWorkStart = timeWorkStart;
    }

    public String getTimeWorkEnd() {
        return timeWorkEnd;
    }

    public void setTimeWorkEnd(String timeWorkEnd) {
        this.timeWorkEnd = timeWorkEnd;
    }
}