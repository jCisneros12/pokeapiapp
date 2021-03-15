package com.jcisneros.pokeapiapp.datasource.firebasedatasource

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.jcisneros.pokeapiapp.datasource.models.PokemonModel
import com.jcisneros.pokeapiapp.datasource.models.Team
import com.jcisneros.pokeapiapp.datasource.models.User
import com.jcisneros.pokeapiapp.datasource.models.UserTeam
import com.jcisneros.poketeamsapp.datasources.entities.PokemonInfo

class FirebaseService {
    //firestore db instance
    private val db = FirebaseFirestore.getInstance()

    //firestore auth instance
    private val auth = FirebaseAuth.getInstance()

    //for login user
    fun login(user: User): User {
        var userLogin = User("user@default.com", "asdf1234")
        auth.signInWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener {
                    userLogin = User(
                            it.result?.user?.email.toString(),
                            "password",
                    )
                }
        return userLogin
    }

    //for register new user
    fun register(user: User): Boolean {
        Log.i("REGISTER", "enter the method")
        var isLogin = false
        auth.createUserWithEmailAndPassword(user.email, user.password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        isLogin = true
                        Log.i("REGISTER", "register success")
                    }
                }
        return isLogin
    }

    //for save new team of user

    fun saveNewTeam(user: User, userTeam: UserTeam) {
        var saveSuccess = false
        val team = Team(userTeam.teamName, userTeam.teamToken)
        val pokemonOfTeams: List<PokemonInfo> = userTeam.pokemonTeam
        val docRef = db.collection("users").document(user.email)
                .collection("teams")

        docRef.add(team).addOnSuccessListener { doc ->
//                    Log.i("IDTEAM", doc.id)
                    for (pokemon in pokemonOfTeams) {
                        val pokemonModel = PokemonModel(
                                pokemon.name,
                                pokemon.types[0].type.name,
                                pokemon.order,
                                pokemon.sprites.sprite
                        )
                        docRef.document(doc.id)
                                .collection("team")
                                .document().set(pokemonModel)
                                .addOnSuccessListener {
                                    saveSuccess = true
                                }
                    }
                }
    }

    //for get teams of user
    fun getAllTeams(user: User): Task<QuerySnapshot> {
//        var teamsNames
        var teamUser: List<UserTeam>
        return db.collection("users").document(user.email)
                .collection("teams")
                .get()
    }

    //for get pokemon list of teams by id
    fun getPokemonTeamById(idTeam: String, user: User): CollectionReference {
        return db.collection("users")
                .document(user.email)
                .collection("teams")
                .document(idTeam)
                .collection("team")
    }

    //for update team info
}
