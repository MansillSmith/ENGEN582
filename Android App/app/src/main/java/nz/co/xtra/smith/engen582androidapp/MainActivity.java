package nz.co.xtra.smith.engen582androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Socket socket;
    Thread threadConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void ButtonConnectToServer(View view){
        try{
            // Gets the details of the server
            String ipString = ((EditText)findViewById(R.id.textBoxIPAddress)).getText().toString();
            int port = Integer.parseInt(((EditText)findViewById(R.id.textBoxPort)).getText().toString());

            threadConnection = new Thread(new HandleConnection(ipString, port));
            threadConnection.start();

        }
        catch(Exception e){
            System.err.println("Error: " + e);
        }

    }

    private class HandleConnection implements Runnable{
        String ipString;
        int port;

        public HandleConnection(String ipString, int port){
            this.ipString = ipString;
            this.port = port;
        }

        @Override
        public void run(){
            try{
                socket = new Socket(ipString, port);
//                Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT);

                String stringToSend = ((EditText)findViewById(R.id.textBoxStringToSend)).getText().toString();
                BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                bos.write(stringToSend.getBytes());
                bos.close();

            }
            catch(Exception e){
                System.err.println("Error: " + e);
            }

        }
    }

    public void ButtonSendString(View view){
        String stringToSend = ((EditText)findViewById(R.id.textBoxStringToSend)).getText().toString();

        try{
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            bos.write(stringToSend.getBytes());
            bos.close();
        }
        catch(Exception e){

        }

    }
}
