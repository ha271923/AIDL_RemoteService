package example.givemepass.aidlremotedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {
    private boolean flag;
    private final IRemoteAIDL.Stub remoteBinder = new IRemoteAIDL.Stub(){
        @Override
        public void getRemoteName(IRemoteAIDLCallback callback) throws RemoteException {
            while(!flag) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.handleMsg("remote service");
            }
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return remoteBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        flag = true;
        return super.onUnbind(intent);
    }

}
