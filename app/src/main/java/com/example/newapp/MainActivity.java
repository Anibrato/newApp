package com.example.newapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText edname,edloc;
    Button btn;
    TextView tv;

    String sname,sloc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edname=findViewById(R.id.editTextTextPersonName);
        edloc=findViewById(R.id.editTextTextPersonName2);
        btn=findViewById(R.id.button);
        tv=findViewById(R.id.textView);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sname=edname.getText().toString();
                sloc=edloc.getText().toString();
//                tv.setText(sname+" "+sloc);

                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Name",sname);
                user.put("Location",sloc);


        // Add a new document with a generated ID
                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("abceTAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("abceTAG", "Error adding document", e);
                            }
                        });

                db.collection("users")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    String result="";
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("abceTAG", document.getId() + " => " + document.getData());
//                                        result += document.getData().get("Location").toString()+" "+document.getData().get("Name").toString()+"\n";
                                        result +=document.getData().toString();
                                    }
                                    tv.setText(result);
                                } else {
                                    Log.w("abceTAG", "Error getting documents.", task.getException());
                                }
                            }
                        });

            }
        });




    }
}