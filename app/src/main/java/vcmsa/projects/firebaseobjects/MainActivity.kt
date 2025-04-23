package vcmsa.projects.firebaseobjects

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import vcmsa.projects.firebaseobjects.R.layout.listitems


class MainActivity : AppCompatActivity() {
    
    private lateinit var rootNote: FirebaseDatabase
    private lateinit var userReference: DatabaseReference
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        listView = findViewById(R.id.isOutput)
        rootNote = FirebaseDatabase.getInstance()// is the database
        userReference = rootNote.getReference("users") // the child of the database
        
        val dc = Database("b Tapes", "Black Tapes", 1, 10) // the object
        userReference.child(dc.id.toString()).setValue(dc)// set the object using the id
        
        //pulling the data back
        val list = ArrayList<String>()
        val adapter = ArrayAdapter<String>(this, R.layout.listitems, list)
        listView.adapter = adapter
        userReference.addValueEventListener(object : ValueEventListener {
            //pulls everytime the data changes
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()
                for (snapshot1 in snapshot.children) {
                    val dc2 = snapshot1.getValue(Database::class.java)
                    val txt = "Name is ${dc2?.name} and Description is ${dc2?.description}"
                    txt?.let { list.add(it) }
                }
                adapter.notifyDataSetChanged() // update the list
            }
            override fun onCancelled(error: DatabaseError)
            {
            
            
            
            }
        })
    }
    
    
}