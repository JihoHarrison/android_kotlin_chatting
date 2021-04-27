package com.example.chatting_video2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import com.example.chatting_video2.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth// ...

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button_main.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        join_button.setOnClickListener {

            val email = email_area.text.toString();
            val password = userpassword.text.toString();

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "성공")

                        val uid = FirebaseAuth.getInstance().uid ?: ""

                        val user = User(uid, username.text.toString())
                        // 이 부분에서 데이터베이스에 사용자 정보 넣기
                        // Access a Cloud Firestore instance from your Activity
                        val db = FirebaseFirestore.getInstance().collection("users")
                        db.document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "데이터베이스 저장 성공")
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "데이터베이스 저장 오류")
                            }
                        val intent = Intent(this, ChatListActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Log.d(TAG, "실패")
                    }
                }
        }
    }
}