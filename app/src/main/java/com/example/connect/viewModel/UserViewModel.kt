package com.example.connect.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.connect.model.ThreadModel
import com.example.connect.model.UserModel
import com.example.connect.utils.SharedPref
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class UserViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val threadRef = db.getReference("threads")
    val userRef= db.getReference("users")

    private val _threads = MutableLiveData(listOf<ThreadModel>())
    val threads : LiveData<List<ThreadModel>> get() =_threads

    private val _followersList = MutableLiveData(listOf<String>())
    val followersList : LiveData<List<String>> get() =_followersList

    private val _followingList = MutableLiveData(listOf<String>())
    val followingList : LiveData<List<String>> get() =_followingList

    private val _users  = MutableLiveData(UserModel())
    val users : LiveData<UserModel> get() = _users

    fun fetchUsers(uid: String){
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = snapshot.getValue(UserModel::class.java)
                _users.postValue(users)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun fetchThreads(uid: String){
        threadRef.orderByChild("userId").equalTo(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val threadList = snapshot.children.mapNotNull {
                    it.getValue(ThreadModel::class.java)
                }
                _threads.postValue(threadList)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    val firestoreDb = Firebase.firestore

    fun followUsers(userId : String, currentUserId : String){

        val ref = firestoreDb.collection("following").document(currentUserId)
        val followerRef = firestoreDb.collection("followers").document(userId)

        ref.update("followingIds" , FieldValue.arrayUnion(userId))
        followerRef.update("followerIds" , FieldValue.arrayUnion(currentUserId))
    }

    fun getFollowers(userId: String){
        firestoreDb.collection("followers").document(userId).addSnapshotListener(){ value, error ->
            val followerIds = value?.get("followerIds") as? List<String> ?: emptyList()
            _followersList.postValue(followerIds )
        }
    }

    fun getFollowing(userId : String){
        firestoreDb.collection("following").document(userId).addSnapshotListener(){ value, error ->
            val followerIds = value?.get("followingIds") as? List<String> ?: emptyList()
            _followingList.postValue(followerIds )
        }
    }


}


