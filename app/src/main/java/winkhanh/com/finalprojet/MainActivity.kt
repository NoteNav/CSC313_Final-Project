package winkhanh.com.finalprojet

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import winkhanh.com.finalprojet.fragments.MapsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.LogOut) {
            // Log Out has been selected
            Toast.makeText(this, "Log out!", Toast.LENGTH_SHORT).show()
            // Navigate to the log out activity
            val intent = Intent(this, LogoutActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val flContainer : FrameLayout = findViewById(R.id.flContainer)
        val fragment : Fragment = MapsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit()
    }
}