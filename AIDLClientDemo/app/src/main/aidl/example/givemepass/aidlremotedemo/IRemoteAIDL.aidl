// IRemoteAIDL.aidl
package example.givemepass.aidlremotedemo;
import example.givemepass.aidlremotedemo.IRemoteAIDLCallback;
interface IRemoteAIDL {
   oneway void getRemoteName(IRemoteAIDLCallback callback);
}
