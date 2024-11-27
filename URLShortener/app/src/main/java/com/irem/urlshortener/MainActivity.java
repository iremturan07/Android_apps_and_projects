package com.irem.urlshortener;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText editTextLongUrl;
    private EditText editTextShortenedUrl;
    private Button buttonShorten;
    private Button buttonCopy;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextLongUrl = findViewById(R.id.edittext_url);
        editTextShortenedUrl = findViewById(R.id.edittext_shorten);
        buttonShorten = findViewById(R.id.button);
        buttonCopy = findViewById(R.id.btn_copy);

        buttonShorten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String longUrl = editTextLongUrl.getText().toString();
                shortenUrl(longUrl);
            }
        });

        buttonCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyToClipboard(editTextShortenedUrl.getText().toString());
            }
        });
    }
    private void shortenUrl(String longUrl) {
        // API isteği göndermek için ApiService kullanın
        ShortenRequest request = new ShortenRequest(longUrl);
        ApiService apiService = new ApiService();
        Call<ShortenResponse> call = apiService.shortenUrl(request);

        call.enqueue(new Callback<ShortenResponse>() {
            @Override
            public void onResponse(Call<ShortenResponse> call, Response<ShortenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    editTextShortenedUrl.setText(response.body().getLink());
                } else {
                    Toast.makeText(MainActivity.this, "Kısaltma başarısız!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ShortenResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Hata: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void copyToClipboard(String url) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Kısa URL", url);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(this, "Kopyalandı: " + url, Toast.LENGTH_SHORT).show();
    }
}