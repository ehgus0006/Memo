package com.codesample.memo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.codesample.memo.data.Memo;
import com.codesample.memo.data.MemoDatabase;
import com.codesample.memo.databinding.ActivityEditBinding;
import com.codesample.memo.databinding.ActivityMainBinding;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    private MemoDatabase db;
    private Handler handler;
    private Memo memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = MemoDatabase.getInstance(getApplicationContext());
        handler = new Handler();

        int id = getIntent().getIntExtra("id", 0);
        loadMemo(id);

        binding.buttonCancel.setOnClickListener(v -> finish());
        binding.buttonSave.setOnClickListener(this::onSave);
        binding.buttonDelete.setOnClickListener(v -> {
            new Thread(() -> {
                if (memo != null && memo.id != 0) {
                    db.memoDao().deleteMemo(memo);
                    finish();
                }
            }).start();
        });
    }

    private void loadMemo(int id) {
        if (id != 0) {
            new Thread(() -> {
                memo = db.memoDao().getMemo(id);
                handler.post(() -> {
                    binding.editTextTitle.setText(memo.title);
                    binding.editTextBody.setText(memo.body);
                });
            }).start();

        }
    }

    private void onSave(View v) {
        String title = binding.editTextTitle.getText().toString();
        String body = binding.editTextBody.getText().toString();
        if (title.isEmpty() || body.isEmpty()) return;

        if (memo == null)
            memo = new Memo();
        memo.title = title;
        memo.body = body;
        memo.time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);

        new Thread(() -> {
            if (memo.id == 0)
                db.memoDao().addMemo(memo);
            else
                db.memoDao().updateMemo(memo);
            finish();
        }).start();
    }
}


