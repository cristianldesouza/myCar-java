package com.example.mycar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mycar.Model.Fuel;
import com.example.mycar.Model.FuelDao;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;

import fr.ganfra.materialspinner.MaterialSpinner;

public class FormActivity  extends AppCompatActivity {
    private Fuel fuelObject;
    private String fuelId;
    private TextInputEditText etKm;
    private TextInputEditText etLiters;
    private TextInputEditText etDate;
    private MaterialSpinner spBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_form);
        spBrand = findViewById(R.id.spBrand);
        etKm = findViewById(R.id.etKm);
        etLiters = findViewById(R.id.etLiters);
        etDate = findViewById(R.id.etDate);
        etDate.setKeyListener(null);

        String[] brandOptions = getResources().getStringArray(R.array.brandOptions);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, brandOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spBrand.setAdapter(adapter);

        fuelId = getIntent().getStringExtra("fuelId");
        if(fuelId == null){
            fuelObject = new Fuel();

        }else{
            fuelObject = FuelDao.getInstance().getObjectById(fuelId);

            updatePictureOnScreen();

            etKm.setText( String.valueOf(fuelObject.getCurrentKm()) );
            etLiters.setText( String.valueOf(fuelObject.getLitersFuelled()) );

            for(int i = 0; i < spBrand.getAdapter().getCount(); i++){
                if (spBrand.getAdapter().getItem(i).toString().equals(fuelObject.getBrand())){
                    spBrand.setSelection(i+1);
                    break;
                }
            }
            DateFormat formatter = android.text.format.DateFormat.getDateFormat( getApplicationContext() );
            String formatedDate = formatter.format( fuelObject.getDate().getTime() );
            etDate.setText( formatedDate );
        }

    }

    public void save(){

        fuelObject.setCurrentKm(Double.parseDouble(etKm.getText().toString()));
        fuelObject.setLitersFuelled(Double.parseDouble(etLiters.getText().toString()));
        fuelObject.setBrand( spBrand.getSelectedItem().toString() );

        if (FuelDao.getInstance().getList().size() >= 1) {
            Fuel lastFuel = FuelDao.getInstance().getList().get(0);

            if (lastFuel.getCurrentKm() > fuelObject.getCurrentKm()) {
                setResult(400);
            } else {
                if(fuelId == null) {
                    FuelDao.getInstance().addItem(fuelObject);
                    setResult(201);
                }else{
                    int objectPosition = FuelDao.getInstance().updateItem(fuelObject);
                    Intent closeFormIntention = new Intent();
                    closeFormIntention.putExtra("objectPosition", objectPosition);
                    setResult(200, closeFormIntention);
                }
            }
        } else {
            if(fuelId == null) {
                FuelDao.getInstance().addItem(fuelObject);
                setResult(201);
            }else{
                int objectPosition = FuelDao.getInstance().updateItem(fuelObject);
                Intent closeFormIntention = new Intent();
                closeFormIntention.putExtra("objectPosition", objectPosition);
                setResult(200, closeFormIntention);
            }
        }

        finish();
    }

    public void selectData(View v){
        Calendar datePatern = fuelObject.getDate();;
        if(datePatern == null){
            datePatern = Calendar.getInstance();
        }

        DatePickerDialog dateDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year,month,dayOfMonth);
                        fuelObject.setDate(selectedDate);

                        DateFormat formatter = android.text.format.DateFormat.getDateFormat( getApplicationContext() );
                        String formatedDate = formatter.format( selectedDate.getTime() );
                        etDate.setText( formatedDate );
                    }
                },
                datePatern.get(Calendar.YEAR),
                datePatern.get(Calendar.MONTH),
                datePatern.get(Calendar.DAY_OF_MONTH)
        );
        dateDialog.show();
    }

    public void delete(){
        new AlertDialog.Builder(this)
                .setTitle("Você tem certeza?")
                .setMessage("Você quer mesmo excluir?")
                .setPositiveButton("Excluir", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int objectPosition = FuelDao.getInstance().deleteItem(fuelObject);
                        Intent intencaoDeFechamentoDaActivityFormulario = new Intent();
                        intencaoDeFechamentoDaActivityFormulario.putExtra("objectPosition", objectPosition);
                        setResult(202, intencaoDeFechamentoDaActivityFormulario);
                        finish();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate( R.menu.menu_form, menu );

        if(fuelId == null){
            menu.removeItem(R.id.menuDelete);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuSave){
            save();
        }
        if (item.getItemId() == R.id.menuDelete){
            delete();
        }

        return super.onOptionsItemSelected(item);
    }

    String picturePath = null;

    private File createPictureFile() throws IOException {

        String pictureName = java.util.UUID.randomUUID().toString();
        File path = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File picture = File.createTempFile(pictureName,".jpg", path);

        picturePath = picture.getAbsolutePath();
        return picture;
    }

    public void openCam(View v){

        Intent camIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File pictureFile = null;
        try {
            pictureFile = createPictureFile();
        } catch (IOException ex) {
            Toast.makeText(this, "Não foi possível criar arquivo para foto", Toast.LENGTH_LONG).show();
        }
        if (pictureFile != null) {
            Uri fotoURI = FileProvider.getUriForFile(this,
                    "com.example.mycar.fileprovider",
                    pictureFile);
            camIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
            startActivityForResult(camIntent, 1);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                fuelObject.setPicturePath( picturePath );
                updatePictureOnScreen();

            }
        }
    }

    private void updatePictureOnScreen(){
        if(fuelObject.getPicturePath() != null){
            ImageView ivPicture = findViewById(R.id.ivPicture);
            ivPicture.setImageURI(Uri.parse(fuelObject.getPicturePath()));
        }
    }

}
