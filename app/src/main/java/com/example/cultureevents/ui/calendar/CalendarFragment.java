package com.example.cultureevents.ui.calendar;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cultureevents.R;
import com.example.cultureevents.ui.calendar.adapter.EventsAdapter;

import com.example.cultureevents.utils.DbConstants;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;


/**
 * ClendarFragment used in MainActivity
 */
public class CalendarFragment extends Fragment {

    private final static int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
    private CalendarView calendar;
    private View view;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private Button myAgenda;
    private Button agenda;
    private Dialog login_dialog;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private List<String> names = new ArrayList<>();
    private List<String> dates = new ArrayList<>();
    private List<String> times = new ArrayList<>();
    private List<String> description = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar1, container, false);

        initViews();

        initDatabase();

        Toolbar myToolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                final String datePicked = i2 + "-" + (i1 + 1) + "-" + i;
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        clearViews();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String date = snapshot.child("date").getValue(String.class);
                            if (date.equals(datePicked)) {
                                String n = snapshot.child("name").getValue(String.class);
                                String t = snapshot.child("time").getValue(String.class);
                                String d = snapshot.child("date").getValue(String.class).substring(0, 2);
                                String ds = snapshot.child("description").getValue(String.class);
                                names.add(n);
                                times.add(t);
                                dates.add(d);
                                description.add(ds);

                            }

                        }
                        EventsAdapter adapter = new EventsAdapter(names, dates, times, description);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        //No-op
                    }
                });

            }
        });

        agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearViews();

                names.add("?????????????? ???????????? ???? ??????????????????");
                names.add("?????????????? ???????? ??????????????");
                times.add("18:0");
                times.add("12:30");
                description.add("?????????????? ???????????? ???? ?????????????????? ?? ?????????????????????? ?????????????? ???????????????? ?????? ???? ?????????????????? ????????????.");
                description.add("?????????????????????? ???? ?????????????????????? ???? ??????????????");
                dates.add("24");
                dates.add("24");
                EventsAdapter adapter = new EventsAdapter(names, dates, times, description);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        });

        myAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrRemoveToFavourite(view);
            }
        });
        return view;
    }

    private void initViews() {
        login_dialog = new Dialog(getContext());
        recyclerView = view.findViewById(R.id.recyclerView);
        agenda = view.findViewById(R.id.agendaBtn);
        myAgenda = view.findViewById(R.id.myAgendaBtn);
        calendar = view.findViewById(R.id.calendar);
    }

    private void initDatabase() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference(DbConstants.DB_EVENTS);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    private void clearViews() {
        names.clear();
        times.clear();
        description.clear();
        dates.clear();
    }



    public void showPopUp(View v) {
        TextView txtClose;
        SignInButton google_signIn;
        login_dialog.setContentView(R.layout.login_popup);
        txtClose = login_dialog.findViewById(R.id.txtclose);
        google_signIn = login_dialog.findViewById(R.id.sign_in_button);
        txtClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login_dialog.dismiss();

            }
        });

        google_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signIn();

            }
        });

        login_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        login_dialog.show();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getActivity(), "Error occur while login", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                    }
                });
    }

    private void addOrRemoveToFavourite(View view) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            showPopUp(view);
        }
        clearViews();
        names.add("?????????????? ???????????? ???? ??????????????????");
        times.add("18:0");
        description.add("?????????????? ???????????? ???? ?????????????????? ?? ?????????????????????? ?????????????? ???????????????? ?????? ???? ?????????????????? ????????????.");
        dates.add("24");
        EventsAdapter adapter = new EventsAdapter(names, dates, times, description);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void updateUI(FirebaseUser user) {
        login_dialog.dismiss();
    }
}