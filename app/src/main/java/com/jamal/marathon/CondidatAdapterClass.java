package com.jamal.marathon;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CondidatAdapterClass extends RecyclerView.Adapter<CondidatAdapterClass.ViewHolder> {
    List<Condidat> condidats;
    Context context;
    Database database;

    public CondidatAdapterClass(List<Condidat> condidats, Context context) {
        this.condidats = condidats;
        this.context = context;
        database = new Database(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.condidat_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Condidat condidat = condidats.get(position);
        holder.textViewId.setText(Integer.toString(condidat.getId()));
        holder.editText_nom.setText(condidat.getNom());
        holder.editText_prenom.setText(condidat.getPrenom());
        holder.editText_email.setText(condidat.getEmail());
        holder.modifier_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                condidat.setNom(holder.editText_nom.getText().toString());
                condidat.setPrenom(holder.editText_prenom.getText().toString());
                condidat.setEmail(holder.editText_email.getText().toString());
                Boolean ret = database.UpdateCondidat(condidat);
                if (ret == true)
                    Toast.makeText(context, "modifier terminer avec succés", Toast.LENGTH_SHORT).show();
                else if (ret == false)
                    Toast.makeText(context, "erreur de modifier", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "id est invalide", Toast.LENGTH_SHORT).show();
            }
        });
        holder.supprimer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean ret = database.deleteCondidat(condidat.getId());
                condidats.remove(position);
                notifyDataSetChanged();
                if (ret == true)
                    Toast.makeText(context, "supprimer terminer avec succés", Toast.LENGTH_SHORT).show();
                else if (ret == false)
                    Toast.makeText(context, "erreur de supprimer", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(context, "id est invalide", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return condidats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewId;
        EditText editText_nom, editText_prenom, editText_email;
        Button modifier_button, supprimer_button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = (TextView) itemView.findViewById(R.id.TextViewId);
            editText_nom = (EditText) itemView.findViewById(R.id.editTextNom);
            editText_prenom = (EditText) itemView.findViewById(R.id.editTextPrenom);
            editText_email = (EditText) itemView.findViewById(R.id.editTextEmail);
            modifier_button = (Button) itemView.findViewById(R.id.modifier_button);
            supprimer_button = (Button) itemView.findViewById(R.id.supprimer_button);

        }
    }
}
