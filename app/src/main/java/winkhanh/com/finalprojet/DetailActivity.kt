package winkhanh.com.finalprojet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.parse.GetCallback
import com.parse.ParseException
import com.parse.ParseQuery
import winkhanh.com.finalprojet.models.Post

class DetailActivity : AppCompatActivity() {
    lateinit var tvUser: TextView
    lateinit var tvContent: TextView
    lateinit var tvTitle: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvUser = findViewById(R.id.tvUser)
        tvContent = findViewById(R.id.tvContent)
        tvTitle = findViewById(R.id.tvTitleDetail)
        val id = intent.getStringExtra("id")
        fetchPost(id?:"")
    }
    private fun fetchPost(id: String){
        val query : ParseQuery<Post> = ParseQuery.getQuery(Post::class.java)
        query.include(Post.KEY_USER)
        query.getInBackground(id
        ) { post, e ->
            if (e != null) {
                //something went wrong with fetching
                Log.e("Detail", "can't fetch$id")
            }
            if (post != null) {
                tvUser.text = post.user.username
                tvTitle.text = post.title
                tvContent.text = post.detail
            }
        }
    }
}