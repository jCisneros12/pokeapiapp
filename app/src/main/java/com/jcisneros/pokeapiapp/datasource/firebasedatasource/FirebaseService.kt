package com.jcisneros.pokeapiapp.datasource.firebasedatasource

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.jcisneros.pokeapiapp.datasource.models.Team
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.datasource.models.UserTeam
import com.jcisneros.pokeapiapp.ui.teams.NewTeamViewModel

class FirebaseService {
    //firestore db instance
    private val db = FirebaseFirestore.getInstance()

    //firestore auth instance
    private val auth = FirebaseAuth.getInstance()

    //for login user
    fun login(user: User): User {
        var userLogin = User("user@default.com", "asdf1234")
        auth.signInWithEmailAndPassword(user.emal, user.password)
            .addOnCompleteListener{
                 userLogin = User(
                    it.result?.user?.email.toString(),
                    "password",)
            }
        return  userLogin
    }

    //for register new user
     fun register(user: User) :Boolean{
        Log.i("REGISTER", "enter the method")
        var isLogin = false
        auth.createUserWithEmailAndPassword(user.emal, user.password)
            .addOnCompleteListener{
                if(it.isSuccessful){
                    isLogin = true
                    Log.i("REGISTER", "register success")
                }
            }
        return isLogin
    }

    //for save new team of user
    fun saveNewTeam(user: User, team: UserTeam): Boolean{
        var saveSuccess = false
        db.collection("users").document(user.emal)
                .collection("teams").document()
                .set(team).addOnCompleteListener{
                    if(it.isSuccessful){
                        saveSuccess = true
                    }
            }
        return saveSuccess
    }

    //for get teams of user
    fun getTeams(user: User) {
        lateinit var teamsVM: NewTeamViewModel
        var teamUser: List<UserTeam>
         db.collection("users").document(user.emal)
            .collection("teams").document()
            .get()

    }
    //for get teams of user
    fun getAllTeams(user: User) {
//        var teamsNames
        var teamUser: List<UserTeam>
        db.collection("users").document(user.emal)
            .collection("teams")
            .get().addOnSuccessListener { res->
                for (document in res){
                    val teamName = document.data
                    val pokemonName = document.data.get("pokemonTeam")
                    Log.d("FIRESTORE TEAMS NAME", "${document.data.get("teamName")}")
                    Log.d("FIRESTORE POKEMONS", "${document.data.get("pokemonTeam")}")
                }

            }

    }

    fun formatQueryToTeamObject(){

    }

    //for update team info
}
