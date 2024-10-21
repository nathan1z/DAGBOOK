package com.example.dagbook.controlador;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dagbook.R;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity implements View.OnClickListener{
    private int dia,mes,año,hora1,minuto1,dia2,mes2,año2,hora2,minuto2;
    CheckBox duracion;
    Button agregar;
    EditText titulo;
    EditText descripcion;
    EditText lugar;

    //mostrar datos del data y time
    EditText taño;
    EditText thora;
    EditText taño2;
    EditText thora2;
    Button bfecha,bhora,bfecha2,bhora2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        thora=  findViewById(R.id.edhora);
        taño= findViewById(R.id.edfecha);
        thora2= findViewById(R.id.edhora2);
        taño2= findViewById(R.id.edfecha2);

        duracion = (CheckBox) findViewById(R.id.chDuracion);
        //instanciamos
        titulo = findViewById(R.id.etTitulo);
        descripcion = findViewById(R.id.etDescripcion);
        lugar = findViewById(R.id.etLugar);

        bhora = findViewById(R.id.bhora);
        bhora2 = findViewById(R.id.bhora2);
        bfecha = findViewById(R.id.bfecha);
        bfecha2 = findViewById(R.id.bfecha2);
        agregar = findViewById(R.id.bAgregar);

        bhora.setOnClickListener(this);
        bfecha.setOnClickListener(this);
        bhora2.setOnClickListener(this);
        bfecha2.setOnClickListener(this);
        agregar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == bfecha) {
           Calendar c = Calendar.getInstance();
            dia = c.get(Calendar.DAY_OF_MONTH);
            mes = c.get(Calendar.MONTH);
            año = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    año = year;
                    dia = dayOfMonth;
                    mes = monthOfYear;
                    taño.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                }
            }
                    , dia, mes, año);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();

        }
        if (v == bhora) {
            final Calendar c = Calendar.getInstance();
            hora1 = c.get(Calendar.HOUR_OF_DAY);
            minuto1 = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    minuto1 = minute;
                    hora1 = hourOfDay;
                    thora.setText(hourOfDay + ":" + minute);

                }

            }, hora1, minuto1, false);


            timePickerDialog.show();
        }
        if(v==bfecha2){
           Calendar c = Calendar.getInstance();
            dia2 = c.get(Calendar.DAY_OF_MONTH);
            mes2 = c.get(Calendar.MONTH);
            año2 = c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                   año2 = year;
                    dia2 = dayOfMonth;
                    mes2 = monthOfYear;
                    taño2.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
                }
            }
                    , dia2, mes2, año2);
            datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            datePickerDialog.show();
        }
        if (v == bhora2) {
            final Calendar c = Calendar.getInstance();
            hora2 = c.get(Calendar.HOUR_OF_DAY);
            minuto2 = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    minuto2 = minute;
                    hora2 = hourOfDay;
                    thora2.setText(hourOfDay + ":" + minute);

                }

            }, hora2, minuto2, false);
            timePickerDialog.show();
        }
        if(v == agregar){

            Agregar(v);

        }
    }

    public void Agregar(View v) {

        Calendar cal = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        boolean val = false;
        Intent intent = null;
        while(val == false){
            try{
                cal.set(Calendar.YEAR, año);
                cal.set(Calendar.MONTH, mes);
                cal.set(Calendar.DAY_OF_MONTH, dia);

                cal.set(Calendar.HOUR_OF_DAY, hora1);
                cal.set(Calendar.MINUTE, minuto1);

                cal2.set(Calendar.YEAR, año2);
                cal2.set(Calendar.MONTH, mes2);
                cal2.set(Calendar.DAY_OF_MONTH, dia2);

                cal2.set(Calendar.HOUR_OF_DAY, hora2);
                cal2.set(Calendar.MINUTE, minuto2);

                intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());  //fecha de inicio
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal2.getTimeInMillis());   //fecha fin

                intent.putExtra(CalendarContract.Events.ALL_DAY, duracion.isChecked());
                intent.putExtra(CalendarContract.Events.TITLE, titulo.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, lugar.getText().toString());

                startActivity(intent);
                val=true;


            }catch (Exception e){
                taño.setText("");
                thora.setText("");
                thora2.setText("");
                taño2.setText("");
                titulo.setText("");
                descripcion.setText("");
                lugar.setText("");
                Toast.makeText(getApplicationContext(), "fecha invalida", Toast.LENGTH_LONG).show();
            }
        }

    }
}
