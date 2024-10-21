package com.example.dagbook.controlador;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.dagbook.R;
import com.example.dagbook.databinding.ActivityInicioBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class InicioActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityInicioBinding binding;
    GoogleSignInClient gsc;
    GoogleSignInOptions gso;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInicioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Codigo Mensaje desde Aquí
        setSupportActionBar(binding.appBarInicio.toolbar);
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        binding.appBarInicio.fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                try {
                    String stringSenderEmail="bryan99mh@gmail.com";
                    String stringReceiverEmail =account.getEmail();
                    String stringPasswordSenderEmail = "xaeiyhevyasmnqxy";
                    String stringHost = "smtp.gmail.com";

                    Properties properties = System.getProperties();
                    properties.put("mail.smtp.host", stringHost);
                    properties.put("mail.smtp.port", "465");
                    properties.put("mail.smtp.ssl.enable", "true");
                    properties.put("mail.smtp.auth", "true");
                    javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(stringSenderEmail , stringPasswordSenderEmail);
                        }
                    });
                    MimeMessage mimeMessage = new MimeMessage(session);

                    mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

                    mimeMessage.setSubject("Dagbook Mensaje Recordatorio");
                    mimeMessage.setText("Hola Te recuerdo que mañana sera un gran día\n Comenzamos el trabajo");

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Transport.send(mimeMessage);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                    //Toast.makeText(getApplicationContext(),"Correo enviado exitosamente", Toast.LENGTH_LONG).show();
                    Snackbar.make(view, "Correo Enviado Exitosamente", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } catch (AddressException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }

        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //Nombre del Usuario
        View headView= navigationView.getHeaderView(0);
        TextView username= headView.findViewById(R.id.username);
        TextView email= headView.findViewById(R.id.mail);
        gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc= GoogleSignIn.getClient(this,gso);
        //GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(this);
        if(account!=null) {
            String Name=account.getDisplayName();
            String Mail=account.getEmail();
            username.setText(Name);
            email.setText(Mail);



        }


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.inicio, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_settings:
                gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(new Intent(InicioActivity.this, LoginActivity.class));
                    }
                });
              /*Intent myIntent = new Intent(InicioActivity.this, LoginActivity.class);
                startActivityForResult(myIntent,0);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}