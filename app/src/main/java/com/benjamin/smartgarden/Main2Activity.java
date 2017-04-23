package com.benjamin.smartgarden;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Set;
import java.util.UUID;

public class Main2Activity extends AppCompatActivity {
    TextView myLabel;
    EditText myTextbox;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    PrintStream printStream;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    String curData;
    volatile boolean stopWorker;
    TextView textView;
    String value;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //elemen
            //elements in xml
            Bundle extras = getIntent().getExtras();

            if(extras !=null) {
                value = extras.getString("1");
                Sensor();
        }
            Button openButton = (Button) findViewById(R.id.open);
            Button sendButton = (Button) findViewById(R.id.send);
            Button closeButton = (Button) findViewById(R.id.close);
            myLabel = (TextView) findViewById(R.id.label);
            myTextbox = (EditText) findViewById(R.id.entry);
            textView = (TextView) findViewById(R.id.textView1);

            printStream = new PrintStream(mmOutputStream); // wrap print stream to output stream





        //Open Button
            openButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        findBT();
                        openBT();
                    } catch (IOException ex) {
                    }
                }
            });

            //Send Button
            sendButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        sendData();
                    } catch (IOException ex) {
                        showMessage("SEND FAILED");
                    }
                }
            });

            //Close button
            closeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        closeBT();
                    } catch (IOException ex) {
                    }
                }
            });
        }

        //check for bluetooth adapter
    void Sensor(){
        int y = Integer.parseInt(value);
        if (y == 1){
            printStream.print("1");
        }

        else if (y==2) {
            printStream.print("2");
        }

        else if (y==3){
            printStream.print("3");
        }
        else if (y==4){
            printStream.print("4");
        }

        else if (y==5){
            printStream.print("5");
        }
        else if (y==6){
            printStream.print("6");
        }
        else if (y==7){
            printStream.print("7");
        }
        else if (y==8){
            printStream.print("8");
        }
        else{
            printStream.print("");
        }

    }
    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            myLabel.setText("No bluetooth adapter available");
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, 0);
        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("HC-05")) {
                    mmDevice = device;
                    break;
                }
            }
        }
        myLabel.setText("Bluetooth Device Found");
    }
    //Display text when bluetooth connection is opened
    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();

        mmInputStream = mmSocket.getInputStream();
        beginListenForData();
        myLabel.setText("Bluetooth Opened");

    }

    //listen for data from the arduino
    void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];//read arduino data byte by byte
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    curData = data;
                                    readBufferPosition = 0;




                                    handler.post(new Runnable() {
                                        public void run() {
                                            //display the data
                                            counter++;
                                            myLabel.setText(data);



                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });


        workerThread.start();
    }

    //display text if data is successuly sent to the arduino
    void sendData() throws IOException {
        String msg = myTextbox.getText().toString();
        msg += "\n";
        //mmOutputStream.write(msg.getBytes());
        mmOutputStream.write('A');
        myLabel.setText("Data Sent");
    }


    void closeBT() throws IOException {
        stopWorker = true;
        mmOutputStream.close();
        mmInputStream.close();
        mmSocket.close();
        myLabel.setText("Bluetooth Closed");
    }

    private void showMessage(String theMsg) {
        Toast msg = Toast.makeText(getBaseContext(),
                theMsg, (Toast.LENGTH_LONG));
        msg.show();
    }

    public void checkbut(View v){
        int realData = Integer.parseInt(curData.trim());
        if (realData <= 300){

            textView.setText("The plant needs water");
        }

        else if (realData > 300 && realData <= 720)
        {

            textView.setText("Ze plant has zenough vata");
        }

        else {

            textView.setText("Ze plant has to much vata :(");
        }
    }



}
