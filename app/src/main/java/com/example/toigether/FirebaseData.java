package com.example.toigether;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.toigether.items.Organization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class FirebaseData {
    private Organization organization = new Organization();
    private ArrayList<Organization> organizations = new ArrayList<>();
    private ArrayList<Organization> organizationsSide = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public Organization getOrganization(String id) {
        Log.e("FireData", String.valueOf(organization==null));
        Log.e("FireData", organization.getId());
        return organization;
    }

    public interface OnGetDataListener {
        public void onStart();
        public void onSuccess(ArrayList<Organization> data);
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

        db.collection("organizations").orderBy("rating", Query.Direction.DESCENDING).whereArrayContainsAny("categories", categories)
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

//    public void getGenerated(ArrayList<String> categories, ArrayList<String> services, String city, final OnGetDataListener listener) {
//        listener.onStart();
//
//        getOrganizationsByParameter("categories", city, categories, new OnGetDataListener() {
//            @Override
//            public void onStart() {}
//
//            @Override
//            public void onSuccess(ArrayList<Organization> data) {
//                organizations = data;
//            }
//        });
//
//        getOrganizationsByParameter("gen_services", city, services, new OnGetDataListener() {
//            @Override
//            public void onStart() {}
//
//            @Override
//            public void onSuccess(ArrayList<Organization> data) {
//                organizationsSide = data;
//            }
//        });
//
//        Log.e("sizeArray", organizations.size() + " " + organizationsSide.size());
//
//        if(organizations.size()==0 || organizationsSide.size()==0)
//            organizations.addAll(organizationsSide);
//        else
//            organizations.retainAll(organizationsSide);
//
//        listener.onSuccess(organizations);
//    }

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

}
