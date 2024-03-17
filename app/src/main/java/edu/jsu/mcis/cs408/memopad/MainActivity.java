package edu.jsu.mcis.cs408.memopad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.beans.PropertyChangeEvent;

import edu.jsu.mcis.cs408.memopad.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity /*implements AbstractView*/{

    public final String TAG = "MAIN";

    public ActivityMainBinding binding;
    private DatabaseHandler db;

//    private MemoPadController controller;
//    private MemoPadModel model;
    private RecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        model = new MemoPadModel();
//        controller = new MemoPadController(model, this);
//
//        controller.addView(this);
//        controller.addModel(model);

        db = new DatabaseHandler(this, null,null, 1);

        updateRecyclerView();

        binding.addButton.setOnClickListener(v -> {
            addMemo();
//            controller.addMemo();
        });

        binding.deleteButton.setOnClickListener(v -> {
            deleteMemo();
//            controller.deleteMemo();
        });


    }


    void addMemo(){
        String name = binding.inputField.getText().toString();
        Memo m = new Memo(name);
        db.addMemo(m);
        updateRecyclerView();

        Log.i(TAG, "Add Memo " + m);

    }

    void deleteMemo() {
        Memo m = adapter.selectedMemo;
        db.deleteMemo(m);
        updateRecyclerView();

        Log.i(TAG, "Deleted Memo " + m);
    }



    private void updateRecyclerView() {

        adapter = new RecyclerViewAdapter(db.getAllMemosAsList());
        binding.outputView.setHasFixedSize(true);
        binding.outputView.setLayoutManager(new LinearLayoutManager(this));
        binding.outputView.setAdapter(adapter);

        Log.i(TAG, "Updated Recycler View");
    }

//    @Override
//    public void modelPropertyChange(PropertyChangeEvent evt) {
//
//    }
}

