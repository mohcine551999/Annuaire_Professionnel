package ma.enset.annuaire_professionnel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    // Initialize variable
    private List<Contact> list;
    private Activity context;
    private ma.enset.annuaire_professionnel.RoomDB db;

    //Create constructor
    public ContactAdapter(Activity context, List<Contact> list)
    {
       this.context=context;
       this.list=list;
       notifyDataSetChanged();
    }
    {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Initialize view
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        // Initialize main data
        Contact data=list.get(position);

        // Initialize db
        db= ma.enset.annuaire_professionnel.RoomDB.getInstance(context);

        holder.imageView.setTag(data);
        // Set text on text view
        holder.firstName.setText(data.getfirstName());
        holder.lastName.setText(data.getlastName());
        holder.job.setText(data.getJob());
        holder.email.setText(data.getEmail());
        holder.tel.setText(data.gettel());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact d=list.get(holder.getAdapterPosition());
                Intent myInent=new Intent(context,MainActivity3.class);
                myInent.putExtra("Contact", d);
                context.startActivity(myInent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Contact d=list.get(holder.getAdapterPosition());
                        db.contactDao().delete(d);
                        list.clear();
                        list.addAll(db.contactDao().getAll());
                        notifyDataSetChanged();
                        Toast toast=new Toast(context);
                        toast.makeText(context,"the contact was successfully deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("confirmation");
                alertDialog.setMessage("are you sure you want to delete this");
                alertDialog.show();

                return true;
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Contact contact = (Contact) view.getTag();
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + contact.gettel()));
                context.startActivity(intent);
            }
        });

/*        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tel= v.findViewById(R.id.Tel);
               Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+tel.getText()));
                if (ActivityCompat.checkSelfPermission(context,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(context, android.Manifest.permission.CALL_PHONE)) {
                        startActivity(callIntent);
                    } else {
                        ActivityCompat.requestPermissions(context,
                                new String[]{android.Manifest.permission.CALL_PHONE},1);
                        startActivity(callIntent);
                    }
               }
                startActivity(callIntent);
            }
        });*/
        /* holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact d=list.get(holder.getAdapterPosition());
                Intent myInent=new Intent(context,MainActivity3.class);
                myInent.putExtra("Contact", d);
                context.startActivity(myInent);
            }
        });*/
       /* holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Contact d=list.get(holder.getAdapterPosition());
                        db.contactDao().delete(d);
                        list.clear();
                        list.addAll(db.contactDao().getAll());
                        notifyDataSetChanged();
                        Toast toast=new Toast(context);
                        toast.makeText(context,"the contact was successfully deleted",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("confirmation");
                alertDialog.setMessage("are you sure you want to delete this");
                alertDialog.show();

                return true;
            }
        });*/



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Initialize variable
        TextView firstName;
        TextView lastName;
        TextView job;
        TextView tel;
        TextView email;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Assign variable

            firstName=itemView.findViewById(R.id.firstName);
            lastName=itemView.findViewById(R.id.lastName);
            job=itemView.findViewById(R.id.job);
            tel=itemView.findViewById(R.id.tel);
            email=itemView.findViewById(R.id.email);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
    public void searching(String name){
        list.clear();
        list=db.contactDao().getAllByName(name);
        System.out.println("*********************************************************************");
        System.out.println(name);
        System.out.println(list.size());
        System.out.println("*********************************************************************");
        notifyDataSetChanged();

    }
}
