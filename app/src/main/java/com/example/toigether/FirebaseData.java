package com.example.toigether;

import android.app.Dialog;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.toigether.items.Event;
import com.example.toigether.items.Organization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class FirebaseData {
    private Organization organization = new Organization();
    private Event event = new Event();
    private ArrayList<Organization> organizations = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public interface OnGetDataListener {
        public void onStart();
        public void onSuccess(ArrayList<Organization> data);
    }

    public interface OnGetOneListener {
        public void onStart();
        public void onSuccess(Organization data);
    }

    public interface OnGetEventsListener {
        public void onStart();
        public void onSuccess(ArrayList<Event> events);
    }

    public interface OnGetEventListener {
        public void onStart();
        public void onSuccess(Event event);
    }

    public void getOrganization(String id, final OnGetOneListener listener) {
        listener.onStart();

        db.collection("organizations").document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                organization = documentSnapshot.toObject(Organization.class);
                listener.onSuccess(organization);
            }
        });
    }

    public void getTopRating(final OnGetDataListener listener) {
        listener.onStart();

        db.collection("organizations").orderBy("rating", Query.Direction.DESCENDING).limit(5)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Organization organization = document.toObject(Organization.class);
                        organization.setId(document.getId());
                        organizations.add(organization);
                    }
                } else {
                    Log.d("ResultFragment getOrganizations", "Error getting documents: ", task.getException());
                }

                listener.onSuccess(organizations);
            }
        });
    }

    public void getOrganizationByCategory(String category, final OnGetDataListener listener) {
        listener.onStart();

        ArrayList<String> categories = new ArrayList<>();
        categories.add(category);

        db.collection("organizations")
                .whereArrayContainsAny("categories", categories)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Organization organization = document.toObject(Organization.class);
                        organization.setId(document.getId());
                        organizations.add(organization);
                    }
                } else {
                    Log.d("ResultFragment getOrganizations", "Error getting documents: ", task.getException());
                }

                listener.onSuccess(organizations);
            }
        });
    }

    public void getOrganizationsByParameter(String parameter, String city, ArrayList<String> attribute, final OnGetDataListener listener) {
        listener.onStart();

        ArrayList<Organization> org = new ArrayList<>();

        db.collection("organizations")
                .whereEqualTo("town", city).whereArrayContainsAny(parameter, attribute)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Organization organization = document.toObject(Organization.class);
                        organization.setId(document.getId());
                        org.add(organization);
                    }
                } else {
                    Log.d("ResultFragment getOrganizations", "Error getting documents: ", task.getException());
                }

                listener.onSuccess(org);
            }
        });
    }

    public void getPortfolioByOrganization(String id, final OnGetEventsListener listener) {
        listener.onStart();
        ArrayList<Event> events = new ArrayList<>();

        db.collection("events").whereEqualTo("organizator_id", id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Event event = document.toObject(Event.class);
                        event.setId(document.getId());
                        events.add(event);
                    }
                }
                else
                    Log.e("FirebaseData", "portfolio data getting error");
                listener.onSuccess(events);
            }
        });
    }

    public void getEvent(String id, final OnGetEventListener listener) {
        listener.onStart();
        db.collection("events").document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Event event = documentSnapshot.toObject(Event.class);
                listener.onSuccess(event);
            }
        });
    }

    public boolean isAuthenticated() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }


    public void makeTextUnderlined(TextView text) {
        SpannableString content = new SpannableString(text.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        text.setText(content);
    }

}
