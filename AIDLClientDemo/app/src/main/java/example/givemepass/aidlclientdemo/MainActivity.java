package example.givemepass.aidlclientdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import example.givemepass.aidlremotedemo.IRemoteAIDL;
import example.givemepass.aidlremotedemo.IRemoteAIDLCallback;

public class MainActivity extends AppCompatActivity {
    private IRemoteAIDL mService;
    private TextView result;
    private Button connectRemote;
    private Button disconnectRemote;
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mService = IRemoteAIDL.Stub.asInterface(service);
            try {
                mService.getRemoteName(new IRemoteAIDLCallback.Stub(){
                    StringBuffer sb = new StringBuffer();
                    @Override
                    public void handleMsg(final String name){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sb.append(name + "\n");
                                result.setText(sb);
                            }
                        });
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        connectRemote = (Button) findViewById(R.id.connect_remote);
        disconnectRemote = (Button) findViewById(R.id.disconnect_remote);
        connectRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("service.remote");
                intent.setPackage("example.givemepass.aidlremotedemo");
                boolean ret = bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                Log.i("demo", "bindService ret="+ret);
            }
        });
        disconnectRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unbindService(mConnection);
            }
        });
    }
}
