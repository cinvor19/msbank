package cz.sima.msbank.shared

import com.google.firebase.database.FirebaseDatabase

/**
 * Created by Michal Šíma on 26.09.2020.
 */
class FirebaseRepository(private val firebaseDatabase: FirebaseDatabase) {

    fun writeDummyValue(value: String) {
        firebaseDatabase.reference.apply {
            setValue(value)
        }
    }
}
