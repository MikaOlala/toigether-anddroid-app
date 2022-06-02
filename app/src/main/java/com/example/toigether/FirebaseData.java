package com.example.toigether;

import android.app.Dialog;
import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
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

import com.example.toigether.items.Category;
import com.example.toigether.items.Event;
import com.example.toigether.items.Organization;
import com.example.toigether.items.Request;
import com.example.toigether.items.Service;
import com.example.toigether.items.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FirebaseData {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private Dialog dialog;

    public interface OnGetDataListener {
        void onStart();
        void onSuccess(ArrayList<Organization> data);
    }

    public interface OnGetOneListener {
        void onStart();
        void onSuccess(Organization data);
    }

    public interface OnGetEventsListener {
        void onStart();
        void onSuccess(ArrayList<Event> events);
    }

    public interface OnGetEventListener {
        void onStart();
        void onSuccess(Event event);
    }

    public interface OnGetUserListener {
        void onStart();
        void onSuccess(User user);
    }

    public interface OnGetCategoriesListener {
        void onStart();
        void onSuccess(ArrayList<Category> categories);
    }

    public interface OnGetServicesListener {
        void onStart();
        void onSuccess(ArrayList<Service> services);
    }

    public void getOrganization(String id, final OnGetOneListener listener) {
        listener.onStart();

        db.collection("organizations").document(id)
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Organization organization = documentSnapshot.toObject(Organization.class);
                listener.onSuccess(organization);
            }
        });
    }

    public void getTopRating(final OnGetDataListener listener) {
        listener.onStart();
        ArrayList<Organization> organizations = new ArrayList<>();
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

    public void getOrganizationByName(String name, final OnGetDataListener listener) {
        listener.onStart();
        ArrayList<Organization> organizations = new ArrayList<>();
        db.collection("organizations").whereEqualTo("name", name)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Organization organization = document.toObject(Organization.class);
                        organization.setId(document.getId());
                        organizations.add(organization);
                    }
                }
                else
                    Log.e("search by Name", "error");
                listener.onSuccess(organizations);
            }
        });
    }

    public void getOrganizationByCategory(String category, final OnGetDataListener listener) {
        listener.onStart();
        ArrayList<Organization> organizations = new ArrayList<>();
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

    public void getOrgServicesById(String orgId, final OnGetServicesListener listener) {
        listener.onStart();
        ArrayList<Service> services = new ArrayList<>();
        db.collection("services").whereEqualTo("organizator_id", orgId)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Service service = document.toObject(Service.class);
                        services.add(service);
                    }
                }
                else
                    Log.i("getServices", "failed");
                
                listener.onSuccess(services);
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

    public void doRequest(Request request) {
        db.collection("request").add(request)
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("doRequest", e.toString());
            }
        });
    }

    public void createUser(String email, String phone) {
        String name = email.substring(0, email.indexOf('@'));
        db.collection("users").add(new User(email, phone, name, null, null))
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.e("UserCreating", "success");
                }
            });
    }

    public void editAvatar(User user) {
        db.collection("users").document(user.getId())
                .update(
                        "avatar", user.getAvatar()
                );
    }
    
    public void changeFavourite(User user, boolean isFavourite, String id) {
        if (user.getFavourite() == null) {
            ArrayList<String> favourites = new ArrayList<>();
            favourites.add(id);
            user.setFavourite(favourites);
        } else {
            if (isFavourite) {
                ArrayList<String> favourites = user.getFavourite();
                favourites.remove(id);
                user.setFavourite(favourites);
            } else
                user.getFavourite().add(id);
        }

        db.collection("users").document(user.getId())
                .update(
                        "favourite", user.getFavourite()
                );

    }

    public void plusInterestToCategory(Category category) {
        db.collection("categories").document(category.getId())
            .update(
                "interestIndex", category.getInterestIndex() + 1
            );
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

    public void getCategories(final OnGetCategoriesListener listener) {
        listener.onStart();
        ArrayList<Category> categories = new ArrayList<>();

        db.collection("categories").orderBy("interestIndex", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Category category = document.toObject(Category.class);
                        category.setId(document.getId());
                        categories.add(category);
                    }
                }
                else
                    Log.e("FirebaseData", "getCategories task is not successful");

                listener.onSuccess(categories);
            }
        });
    }

    public boolean isAuthenticated() {
        FirebaseUser user = auth.getCurrentUser();
        return user != null;
    }

    public void getUser(String email, final OnGetUserListener listener) {
        listener.onStart();
        db.collection("users").whereEqualTo("email", email)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    User user = new User();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        user = document.toObject(User.class);
                        user.setId(document.getId());
                    }
                    listener.onSuccess(user);
                }
            }
        });
    }
    
    public void getFavouriteList(User user, final OnGetDataListener listener) {
        listener.onStart();
        ArrayList<Organization> organizations = new ArrayList<>();
        int indicator = user.getFavourite().size();
        for (String org : user.getFavourite()) {
            db.collection("organizations").document(org)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Organization organization = documentSnapshot.toObject(Organization.class);
                    organization.setId(org);
                    organizations.add(organization);
                    if (organizations.size()==indicator)  // stupid firebase
                        listener.onSuccess(organizations);
                }
            });
        }
    }

    public String getCurrentUserEmail() {
        return auth.getCurrentUser().getEmail();
    }

    public void makeTextUnderlined(TextView text) {
        SpannableString content = new SpannableString(text.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        text.setText(content);
    }

    public void openDialog(Context context) {
        dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
        dialog.setContentView(R.layout.dialog_loading);

        dialog.show();
    }

    public void openToast(View layout, Context context) {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, 30);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    public void closeDialog() {
        dialog.cancel();
    }
}
