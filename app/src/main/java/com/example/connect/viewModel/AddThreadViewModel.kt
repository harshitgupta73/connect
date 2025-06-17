package com.example.connect.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.connect.model.ThreadModel
import com.example.connect.model.UserModel
import com.example.connect.utils.SharedPref
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class AddThreadViewModel : ViewModel() {

    private val db = FirebaseDatabase.getInstance()
    val userRef = db.getReference("threads")

    private val storageRef = Firebase.storage.reference
    private val imageRef = storageRef.child("threads/${System.currentTimeMillis()}.jpg")

    private val _isPosted = MutableLiveData<Boolean>()
    val isPosted: LiveData<Boolean> = _isPosted

    fun saveImage(
        thread: String,
        userId: String,
        imageUri: Uri,
    ) {
        val uploadTask = imageRef.putFile(imageUri)
        uploadTask.addOnSuccessListener(){
            imageRef.downloadUrl.addOnSuccessListener(){
                saveData(thread,userId,it.toString())
            }
        }
    }

    fun saveData(
        thread: String,
        userId: String,
        imageUri: String,
    ) {
        val threadData = ThreadModel(thread,imageUri,userId, System.currentTimeMillis().toString())

        userRef.child(userRef.push().key!!).setValue(threadData).addOnSuccessListener(){
            _isPosted.postValue(true)
        }.addOnFailureListener(){
            _isPosted.postValue(false)
        }
    }


}


