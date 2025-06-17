package com.example.connect.viewModel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.connect.model.UserModel
import com.example.connect.utils.SharedPref
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AuthViewModel : ViewModel() {

    val auth = FirebaseAuth.getInstance()

    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("users")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("users/${System.currentTimeMillis()}.jpg")

    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    init {
        _firebaseUser.value = auth.currentUser
    }

    fun login(email: String, password: String,context:Context) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _firebaseUser.postValue(auth.currentUser)
                getData(auth.currentUser!!.uid,context)
            } else {
                _error.postValue(it.exception!!.message)
            }
        }
    }

    private fun getData(uid: String, context: Context) {
        userRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(UserModel::class.java)
                SharedPref.storeData(userData!!.email,userData.name,userData.userName,userData.bio,userData.imageUri, context )
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun register(
        email: String,
        password: String,
        name: String,
        userName: String,
        bio: String,
        imageUri: Uri,
        context: Context
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _firebaseUser.postValue(auth.currentUser)
                saveImage(email,password,name,userName,bio,imageUri,auth.currentUser?.uid, context )
            } else {
                _error.postValue("Something went wrong")
            }
        }
    }

    private fun saveImage(
        email: String,
        password: String,
        name: String,
        userName: String,
        bio: String,
        imageUri: Uri,
        uid: String?,
        context: Context
    ) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener(){
            imageRef.downloadUrl.addOnSuccessListener(){
                saveData(email,password,name ,userName,bio,it.toString(),uid,context)
            }
        }
    }

    private fun saveData(
        email: String,
        password: String,
        name: String,
        userName: String,
        bio: String,
        imageUrl: String,
        uid: String?,
        context: Context
    ) {

        val firestoreDb = Firebase.firestore
        val followerRef = firestoreDb.collection("followers").document(uid!!)
        val followingRef = firestoreDb.collection("following").document(uid)

        followingRef.set(mapOf("followingIds" to listOf<String>()))
        followerRef.set(mapOf("followerIds" to listOf<String>()))

        val userData = UserModel(email,password,name,userName,bio,imageUrl,uid)

        userRef.child(uid).setValue(userData).addOnSuccessListener(){
            SharedPref.storeData(email,name,userName,bio,imageUrl, context )
        }.addOnFailureListener(){

        }
    }

    fun signOut(){
        auth.signOut()
        _firebaseUser.postValue(null)
    }


}


