package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.PointerIcon;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.nio.file.attribute.PosixFileAttributes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private void RequestWeather(String nameCity) {
        final TextView textView = (TextView) findViewById(R.id.nameCity);
        final TextView tempText = (TextView) findViewById(R.id.tempText);
        final ImageView imageView = (ImageView) findViewById(R.id.weatherImg);
        final TextView overcastView = (TextView) findViewById(R.id.humidity);
        final TextView pressureView = (TextView) findViewById(R.id.pressure);
        final TextView speedView = (TextView) findViewById(R.id.speed);
        final TextView cloudsView = (TextView) findViewById(R.id.clouds);
        final TextView degView = (TextView) findViewById(R.id.deg);
        NetworkService.getInstance()
                .jsonGet()
                .getCurrentWeather(nameCity,"135da5b38a698367b4629d9d799b6b2d","metric")
                .enqueue(new Callback<Model>() {
                    @Override
                    public void onResponse(@NonNull Call<Model> call,@NonNull Response<Model> response) {
                        if (response.isSuccessful()) {
                            Model model = response.body();
                            textView.setText(model.getCityName());
                            tempText.setText(Float.toString(model.getMain().getTemp()) + "\u00B0" + "C");
                            if(model.getMain().getTemp() < 0) {
                                imageView.setImageResource(R.drawable.snes);
                            }
                            else {
                                imageView.setImageResource(R.drawable.sun_and_cloud);
                            }
                            overcastView.setText(Float.toString(model.getMain().getHumidity()) + "%");
                            pressureView.setText(Float.toString(model.getMain().getPressure()) + " мм.рт.ст");
                            speedView.setText(Float.toString(model.getWind().getSpeed()) + " м/с");
                            cloudsView.setText(Float.toString(model.getCloud().getAll()) + '%');
                            degView.setText(Float.toString(model.getWind().getDegree()) + "\u00B0");

                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Model> call, @NonNull  Throwable t) {
                        textView.setText("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }

    public void ClickButton(View view) {
        final EditText editText = (EditText) findViewById(R.id.textEdit);
        String nameCity = editText.getText().toString();
        RequestWeather(nameCity);
        editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestWeather("Minsk");
    }
}

