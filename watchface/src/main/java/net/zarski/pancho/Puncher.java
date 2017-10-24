package net.zarski.pancho;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.util.Collection;
import java.util.HashSet;

public class Puncher {
    private static final String TAG = "net.zarski.pancho";
    private boolean state;
    private PunchInListener punchInListener;
    private PunchOutListener punchOutListener;
    private GoogleApiClient client;

    public void setPunchInListener(PunchInListener listener) {
        this.punchInListener = listener;
    }

    public void setPunchOutListener(PunchOutListener listener) {
        this.punchOutListener = listener;
    }

    public Puncher(Context applicationContext) {
        this.state = false;
        getGoogleApiClient(applicationContext);
        client.connect();
    }

    public void destroy(){
        client.disconnect();
    }

    public static final long CONNECTION_TIME_OUT_MS = 30;

    public GoogleApiClient getGoogleApiClient(Context context) {
        if (null == client) {
            client = new GoogleApiClient.Builder(context)
                    .addApi(Wearable.API)
                    .build();
        }
        return client;
    }


    public void punchIn(Context context) {
        Log.d("TEST_TEST", "Set state true");
        this.state = true;

        new PunchInActivityTask().execute();
    }

    public void punchOut(Context context) {
        Log.d("TEST_TEST", "Set state false");
        this.state = false;

        new PunchOutActivityTask().execute();

    }

    private class PunchInActivityTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            Collection<String> nodes = getNodes();
            for (String node : nodes) {
                sendPunchInTo(node);
            }
            return null;
        }
    }

    private Collection<String> getNodes() {
        HashSet<String> results = new HashSet<>();
        NodeApi.GetConnectedNodesResult nodes =
                Wearable.NodeApi.getConnectedNodes(client).await();

        for (com.google.android.gms.wearable.Node node : nodes.getNodes()) {
            results.add(node.getId());
        }

        return results;
    }


    private void sendPunchInTo(String node) {
        Wearable.MessageApi.sendMessage(
                client, node, "/punchin", new byte[0]).setResultCallback(
                sendMessageResult -> {
                    if (!sendMessageResult.getStatus().isSuccess()) {
                        Log.e(TAG, "Failed to send message with status code: "
                                + sendMessageResult.getStatus().getStatusCode());
                    }
                    punchInListener.onPunchIn();
                }
        );
    }

    private void sendPunchOutTo(String node) {
        Wearable.MessageApi.sendMessage(
                client, node, "/punchout", new byte[0]).setResultCallback(
                sendMessageResult -> {
                    if (!sendMessageResult.getStatus().isSuccess()) {
                        Log.e(TAG, "Failed to send message with status code: "
                                + sendMessageResult.getStatus().getStatusCode());
                    }
                    punchOutListener.onPunchOut();
                }
        );
    }


    public boolean isPunchIn() {
        return state;
    }

    public interface PunchInListener {
        void onPunchIn();
    }

    public interface PunchOutListener {
        void onPunchOut();
    }

    private class PunchOutActivityTask  extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            Collection<String> nodes = getNodes();
            for (String node : nodes) {
                sendPunchOutTo(node);
            }
            return null;
        }
    }
}
