package com.example.paint;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.UUID;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageButton currpaint,drawbtn,baru,erase,save;
    private DrawingView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       drawView=findViewById(R.id.drawing);
       drawbtn=findViewById(R.id.draw_btn);
        baru=findViewById(R.id.new_btn);
        erase=findViewById(R.id.erase_btn);
        save=findViewById(R.id.save_btn);

        LinearLayout paintLayout=findViewById(R.id.paint_colors);
        currpaint=(ImageButton)paintLayout.getChildAt(0);
        drawView.setColor(currpaint.getTag().toString());
        currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
        drawbtn.setOnClickListener(this);
        baru.setOnClickListener(this);
        erase.setOnClickListener(this);
        save.setOnClickListener(this);


    }
    public void paintClicked(View view){
        if(view!=currpaint){
            ImageButton imgView=(ImageButton) view;
            String color=view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currpaint=(ImageButton)view;
        }
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.draw_btn){
            drawView.setupDrawing();
        }
        if(v.getId()==R.id.erase_btn){
            drawView.setErase(true);
            drawView.setBrushSize(drawView.getBrushSize());
        }
        if(v.getId()==R.id.new_btn){
            AlertDialog.Builder newDialog=new AlertDialog.Builder(this);
            newDialog.setTitle("Nowy obrazek");
            newDialog.setMessage("Zacznij nowy obrazek");
            newDialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        if(v.getId()==R.id.save_btn){
            AlertDialog.Builder saveDialog=new AlertDialog.Builder(this);
            saveDialog.setTitle("Zapisac obrazek");
            saveDialog.setMessage("Zapisz obrazek do galerii urzadzenia");
            saveDialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    drawView.setDrawingCacheEnabled(true);
                    String imgsaved=MediaStore.Images.Media.insertImage(getContentResolver(),drawView.getDrawingCache(),
                            UUID.randomUUID().toString()+".png","drawing");
                    if(imgsaved!=null){
                        Toast savedtoast=Toast.makeText(getApplicationContext(),"Zapisano do galerii",Toast.LENGTH_SHORT);
                        savedtoast.show();
                    }
                    else{
                        Toast unsaved=Toast.makeText(getApplicationContext(),"Cos soe nie powiodlo",Toast.LENGTH_SHORT);
                    }
                }
            });
           saveDialog.setNegativeButton("Nie", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.cancel();
                }
            });
           saveDialog.show();
        }
    }
}
