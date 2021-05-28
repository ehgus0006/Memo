package com.codesample.memo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codesample.memo.data.Memo;
import com.codesample.memo.data.MemoDatabase;
import com.codesample.memo.databinding.ActivityMainBinding;
import com.codesample.memo.widget.MemoAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MemoAdapter adapter;
    private MemoDatabase db;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 바인딩 하는 작업
        com.codesample.memo.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 디비와 핸들러 접근
        db = MemoDatabase.getInstance(getApplicationContext());
        handler = new Handler();

        binding.buttonAdd.setOnClickListener(v->{
            startActivity(new Intent(this, EditActivity.class));
        });

        LinearLayoutManager manager = new LinearLayoutManager(this);
        adapter = new MemoAdapter((((position, memo) -> {
            Intent intent = new Intent(this, EditActivity.class);
            intent.putExtra("id", memo.id);
            startActivity(intent);
        })));
        binding.recyclerViewMemos.setAdapter(adapter);
        binding.recyclerViewMemos.setLayoutManager(manager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(()->{
            List<Memo> list = db.memoDao().getMemoList();
            handler.post(()->adapter.setData(list));
        }).start();
    }
}