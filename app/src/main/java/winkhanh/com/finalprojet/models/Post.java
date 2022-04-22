package winkhanh.com.finalprojet.models;

import android.util.Pair;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DETAIL = "Detail";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_USER = "User";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_LAT = "latitude";
    public static final String KEY_LONG = "longitude";

    public String getDetail(){
        return getString(KEY_DETAIL);
    }

    public void setDetail(String Detail) {
        put(KEY_DETAIL, Detail);
    }

    public String getTitle(){
        return getString(KEY_TITLE);
    }

    public void seTitle(String Title) {
        put(KEY_DETAIL, Title);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public Pair<Double, Double> getLocation() { return new Pair<Double, Double>(getNumber(KEY_LAT).doubleValue(),getNumber(KEY_LONG).doubleValue());}

    public void setLocation(Number latitude, Number longitude){ put(KEY_LAT, latitude); put(KEY_LONG, longitude);}

}
