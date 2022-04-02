package winkhanh.com.finalprojet;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("WrEHYEEwD34vyjZTB786eV7gxIYQyUpMZ8H3aLD5")
                .clientKey("BP44IYyy0EyOrf3GEzEgPyHQyd5znQFdhcvkwsmc")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
