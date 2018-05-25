package com.example.acer.my_login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6, et7;
    String email, pass;
    TextView tv;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mref;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        et7 = (EditText) findViewById(R.id.et7);
        tv = (TextView) findViewById(R.id.tv);
        // progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mref = firebaseDatabase.getReference("welcome");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        int id = item.getItemId();
        switch (id) {
            case R.id.m1: {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this); //Home is name of the activity
                builder.setMessage("Do you want to Logout?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        Intent i = new Intent();
                        i.putExtra("finish", true);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
                        //startActivity(i);
                        finish();

                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

                AlertDialog alert = builder.create();
                alert.show();


                break;
            }


        }}}

    public void submit(View view)
    {
        final String name=et1.getText().toString().trim();
        final String num=et2.getText().toString().trim();
        final String add=et3.getText().toString().trim();
        final String email=et4.getText().toString().trim();
        final String pass=et5.getText().toString().trim();
        if(et1.getText().equals(null)||et2.getText().equals(null)||et3.getText().equals(null)||et4.getText().equals(null)||et1.getText().equals(null))
        {
            et1.setError("fill the value");
        }

//        tv.setText("Name is: "+name+"\n"+"Mobile number is: "+num+"\n"+"Address is: "+add+"\n"+"Email Address is: "+email+"\n");
        Intent intent=new Intent(this,login_page.class);
        intent.putExtra("k1",name);
        intent.putExtra("k2",num);
        intent.putExtra("k3",add);
        intent.putExtra("k4",email);
        intent.putExtra("k5",pass);
//
//        startActivity(intent);

        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           // progressBar.setVisibility(View.GONE);
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Signup", "createUserWithEmail:success");

                            String uid = mAuth.getCurrentUser().getUid();
                            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("student").child(uid);
                            databaseReference.child("name").setValue(name).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    databaseReference.child("contact").setValue(num);
                                    databaseReference.child("address").setValue(add);
                                    databaseReference.child("email").setValue(email);
                                    databaseReference.child("password").setValue(pass);


                                    //startActivity(new Intent(MainActivity.this,login_page.class));
                                    Toast.makeText(MainActivity.this, "You Signed up Successfully",
                                            Toast.LENGTH_SHORT).show();
//                                    et1.clearFocus();
//                                    et2.clearFocus();
//                                    et3.clearFocus();
//                                    et4.clearFocus();
//                                    et5.clearFocus();
                                }

                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Signup Failed", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });



    }
    public void sign_in(View view)
    {
        final String email=et6.getText().toString().trim();
        final String pass=et7.getText().toString().trim();
        if (email==null||pass==null)
        {
            et6.setError("Enter the email or password");
            et6.clearFocus();
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(MainActivity.this, "AUser not logged in.",
                    Toast.LENGTH_SHORT).show();

        }
        else {
                 mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d("Sign In ", "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                startActivity(new Intent(MainActivity.this, login_page.class));
                                et6.clearFocus();
                                et7.clearFocus();

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("Sign in failed", "signInWithEmail:failure", task.getException());
                                Toast.makeText(MainActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }

                            // ...
                        }
                    });
        }
    }

}
