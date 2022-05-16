package winkhanh.com.finalprojet.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.parse.ParseUser
import winkhanh.com.finalprojet.MainActivity
import winkhanh.com.finalprojet.R
import winkhanh.com.finalprojet.models.Post

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var btnNote: Button
    lateinit var etTitle: EditText
    lateinit var etContent: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NoteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNote = view.findViewById(R.id.btnNote)
        etContent = view.findViewById(R.id.etContent)
        etTitle = view.findViewById(R.id.etTitle)
        btnNote.setOnClickListener { submit() }
    }

    private fun submit(){
        if (etContent.text.isEmpty()){
            Toast.makeText( context, "Content cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (etTitle.text.isEmpty()){
            Toast.makeText( context, "Title cannot be empty", Toast.LENGTH_SHORT)
                .show()
            return
        }
        if (!(context as MainActivity).isLocationValid){
            Toast.makeText( context, "Cannot get user location", Toast.LENGTH_SHORT)
                .show()
            return
        }
        saveNote(
            etTitle.text.toString(),
            etContent.text.toString(),
            (context as MainActivity).latitude,
            (context as MainActivity).longitude,)
    }

    private fun saveNote(title: String, content: String, lat: Double, lon: Double){
        val post = Post()
        post.setDetail(content)
        post.setTitle(title)
        post.setLocation(lat, lon)
        post.setUser(ParseUser.getCurrentUser())
        Log.d("Post", "$title $content $lat $lon")
        post.saveInBackground {
            if (it != null){
                Toast.makeText( context, "Something went wrong, please contact dev", Toast.LENGTH_SHORT)
                    .show()
            }
            etContent.setText("")
            etTitle.setText("")
            (context as MainActivity).goBackHome()
        }
    }
}