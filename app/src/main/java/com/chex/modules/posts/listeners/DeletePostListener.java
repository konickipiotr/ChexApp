package com.chex.modules.posts.listeners;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.chex.config.Settings;
import com.chex.modules.posts.PostUpdater;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class DeletePostListener implements View.OnClickListener {

    private final Activity activity;
    private final Long postid;

    public DeletePostListener(Activity activity, Long postid) {
        this.activity = activity;
        this.postid = postid;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(activity);
        dialog.setMessage("Napewno chcesz usunąć komentarz");
        dialog.setTitle("Usuwnanie komentarza");
        dialog.setPositiveButton("Tak",
                (dialog1, which) -> {
                    new DeletePostAsync().execute();
                    Toast.makeText(activity, "Usunięto komentarz", Toast.LENGTH_SHORT).show();
                });

        dialog.setNegativeButton("Anuluj", (dialog12, which) -> Toast.makeText(activity,"Anuluj",Toast.LENGTH_LONG).show());
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    @SuppressLint("StaticFieldLeak")
    class DeletePostAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            HttpRequestUtils requestUtils = new HttpRequestUtils();
            HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
            RestTemplate restTemplate = requestUtils.getRestTemplate();
            String path = Settings.ROOT_PATH + "/post/" + postid;
            restTemplate.exchange(path, HttpMethod.DELETE, new HttpEntity<>(requestHeaders), Void.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            PostUpdater updater = (PostUpdater) activity;
            updater.updatePosts();
        }
    }
}
