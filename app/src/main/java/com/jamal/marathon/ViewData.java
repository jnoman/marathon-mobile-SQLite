package com.jamal.marathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ViewData extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_data);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        Database database = new Database(this);
        List<Condidat> condidats = database.getAllCondidats();
        if(condidats.size()>0){
            CondidatAdapterClass condidatAdapterClass = new CondidatAdapterClass(condidats, ViewData.this);
            recyclerView.setAdapter(condidatAdapterClass);
        } else {
            Toast.makeText(this, "Aucun condidat dans database", Toast.LENGTH_SHORT).show();
        }
    }
}