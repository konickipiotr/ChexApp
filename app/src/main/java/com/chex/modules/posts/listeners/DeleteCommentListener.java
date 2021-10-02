package com.chex.modules.posts.listeners;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.chex.config.Settings;
import com.chex.modules.posts.ItemRemover;
import com.chex.utils.HttpRequestUtils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class DeleteCommentListener implements View.OnClickListener {
    private final Long commentid;
    private final Context context;
    private final ItemRemover itemRemover;
    private final int position;

    public DeleteCommentListener(Long commentid, Context context, ItemRemover itemRemover, int position) {
        this.commentid = commentid;
        this.context = context;
        this.itemRemover = itemRemover;
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setMessage("Napewno chcesz usunąć komentarz");
        dialog.setTitle("Usuwnanie komentarza");
        dialog.setPositiveButton("Tak",
                (dialog1, which) -> {
                    new RemoveCommentAsync().execute();

                    Toast.makeText(context, "Usunięto komentarz", Toast.LENGTH_SHORT).show();

                });

        dialog.setNegativeButton("Anuluj", (dialog12, which) -> Toast.makeText(context,"Anuluj",Toast.LENGTH_LONG).show());
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();

    }

    @SuppressLint("StaticFieldLeak")
    private class RemoveCommentAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            HttpRequestUtils requestUtils = new HttpRequestUtils();
            HttpHeaders requestHeaders = requestUtils.getRequestHeaders();
            RestTemplate restTemplate = requestUtils.getRestTemplate();
            String path = Settings.ROOT_PATH + "/post/comment/" + commentid;
            restTemplate.exchange(path, HttpMethod.DELETE, new HttpEntity<>(requestHeaders), Void.class);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            itemRemover.removeItem(position);
        }
    }
}
