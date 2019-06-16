package com.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dmax.dialog.SpotsDialog;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.recyclerview.Interface.firebaseLoadListener;
import com.recyclerview.adapter.itemAdapter;
import com.recyclerview.adapter.itemGroupAdapter;
import com.recyclerview.model.ItemData;
import com.recyclerview.model.ItemGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements firebaseLoadListener {

    // bind views
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    AlertDialog dialog;
    firebaseLoadListener firebaseLoadListener;
    DatabaseReference myData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initFirebase();
        getFirebaseData();
    }


    private void getFirebaseData() {
        dialog.show();
        myData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ItemGroup> itemGroups = new ArrayList<>();
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren())
                {
                        ItemGroup itemGroup = new ItemGroup();
                        itemGroup.setHeaderTitle(groupSnapshot.child("headerTitle").getValue(true).toString());
                        GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>(){};
                        itemGroup.setListItem(groupSnapshot.child("listItem").getValue(genericTypeIndicator));
                        itemGroups.add(itemGroup);


                }

                firebaseLoadListener.onFirebaseLoadSuccess(itemGroups);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                       firebaseLoadListener.onFirebaseLoadFailed(databaseError.getMessage());
            }
        });

    } // get firebase data

    private void initFirebase() {
        myData = FirebaseDatabase.getInstance().getReference("MyData");
        dialog = new SpotsDialog.Builder().setContext(this).build();
        firebaseLoadListener = this;
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    } // initialize firebase

    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        itemGroupAdapter adapter = new itemGroupAdapter(MainActivity.this , itemGroupList);
        recyclerView.setAdapter(adapter);
        dialog.dismiss();

    }

    @Override
    public void onFirebaseLoadFailed(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
    }
}
