package uk.co.alt236.btlescan.activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.alt236.bluetoothlelib.device.BluetoothLeDevice;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconType;
import uk.co.alt236.bluetoothlelib.device.beacon.BeaconUtils;
import uk.co.alt236.bluetoothlelib.device.beacon.ibeacon.IBeaconDevice;
import uk.co.alt236.btlescan.R;
import uk.co.alt236.btlescan.adapters.LeDeviceListAdapter;
import uk.co.alt236.btlescan.containers.BluetoothLeDeviceStore;
import uk.co.alt236.btlescan.util.BluetoothLeScanner;
import uk.co.alt236.btlescan.util.BluetoothUtils;
import uk.co.alt236.btlescan.util.Calculation;
import uk.co.alt236.btlescan.util.DrawObj;
import uk.co.alt236.btlescan.util.TimeFormatter;
import uk.co.alt236.btlescan.util.WriteFileData;
import uk.co.alt236.easycursor.objectcursor.EasyObjectCursor;
import uk.co.alt236.btlescan.util.CountObj;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tvItemCount)
    protected TextView mTvItemCount;
    @Bind(android.R.id.list)
    protected ListView mList;
    @Bind(android.R.id.empty)
    protected View mEmpty;


    @Bind(R.id.tv_estimates_x)
    protected TextView mestimates_x;
    @Bind(R.id.tv_estimates_y)
    protected TextView mestimates_y;
    @Bind(R.id.tv_ed)
    protected TextView merrord;
    @Bind(R.id.etSetTime)
    protected EditText mSetTime;
    @Bind(R.id.ed_realx)
    protected EditText mrealx;
    @Bind(R.id.ed_realy)
    protected EditText mrealy;

    @Bind(R.id.bt_calculate)
    protected Button mcalculate;
    @Bind(R.id.receive_bt)
    protected ToggleButton receive;
    @Bind(R.id.Ifwrite)
    protected CheckBox Ifwrite;

//    @Bind(R.id.btle_View)
//    protected RelativeLayout drawV;

    DrawView drawView;
    //    CountObj c1 = new CountObj(), c2 = new CountObj(), c4 = new CountObj(), c5 = new CountObj(), c6 = new CountObj(), c9 = new CountObj(),
//            c11 = new CountObj(), c12 = new CountObj(), c13 = new CountObj(), c14 = new CountObj(), c15 = new CountObj(), c16 = new CountObj(),
//            c17 = new CountObj(), c18 = new CountObj(), c19 = new CountObj();
    CountObj c1 = new CountObj(), c2 = new CountObj(), c3 = new CountObj();

    Calculation calculation;
    double Edistance;

    private int COUNT = 60;

    public WriteFileData mWriteFileData = new WriteFileData();
    public List<WriteFileData> wtdArray = new ArrayList<WriteFileData>();
    public List<DrawObj> BArray = new ArrayList<DrawObj>();

    public static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH-mm", Locale.TAIWAN);
    private final static String FILE_PATH = Environment.getExternalStorageDirectory().getPath() + "/positioning";
    public static String FILENAME = "/RSSI_" + sdf.format(System.currentTimeMillis());

    private NotificationManager manger;
    private Notification notification;

    public int MINOR = 0;
    private CountDownTimer timer;
    private boolean running = false;
    public int MAXbeaconNUM = -1, SubspceFingerPrint = -1, RSSISUM8Subspace = -1, chooseSubspace = -1;
    public boolean ThresholdPass = false;


    private BluetoothUtils mBluetoothUtils;
    private BluetoothLeScanner mScanner;
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothLeDeviceStore mDeviceStore;

    private final BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {

            final BluetoothLeDevice deviceLe = new BluetoothLeDevice(device, rssi, scanRecord, System.currentTimeMillis());

            if (BeaconUtils.getBeaconType(deviceLe) == BeaconType.IBEACON) {
                final IBeaconDevice beacon = new IBeaconDevice(deviceLe);

                if (device.getName().equals("USBeacon")) {//beacon過濾
                    mDeviceStore.addDevice(deviceLe);
                    if (receive.isChecked()) {
                        mWriteFileData = new WriteFileData();
                        mWriteFileData.TimeStamp = TimeFormatter.getIsoDateTime(deviceLe.getTimestamp());
                        mWriteFileData.Minor = beacon.getMinor();
                        mWriteFileData.RSSI = deviceLe.getRssi();
                        wtdArray.add(mWriteFileData);
                        mWriteFileData = null;
                        MINOR = beacon.getMinor();
                    }
                }
            }

            final EasyObjectCursor<BluetoothLeDevice> c = mDeviceStore.getDeviceCursor();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.swapCursor(c);
                    updateItemCount(mLeDeviceListAdapter.getCount());
                }
            });
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSetTime.setText(Integer.toString(COUNT));
        mcalculate.setOnClickListener(this);
        receive.setOnClickListener(this);
        receive.setText(Integer.toString(COUNT));

        manger = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notification = new Notification();
        notification.defaults = Notification.DEFAULT_SOUND;

        drawView = new DrawView(this);
//        drawV.addView(drawView);
        BArray = drawView.BeaconArray;

        mList.setEmptyView(mEmpty);
        mDeviceStore = new BluetoothLeDeviceStore();
        mBluetoothUtils = new BluetoothUtils(this);
        mScanner = new BluetoothLeScanner(mLeScanCallback, mBluetoothUtils);
        updateItemCount(0);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        if (!mScanner.isScanning()) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_progress_indeterminate);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                startScan();
                break;
            case R.id.menu_stop:
                mScanner.scanLeDevice(-1, false);
                invalidateOptionsMenu();
                break;
        }
        return true;
    }

    private void writeFile() {
        if (wtdArray != null) {
            File dir = new File(FILE_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(FILE_PATH + FILENAME + " " + mSetTime.getText() + "s" + " " + MINOR + ".csv");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                    Toast.makeText(this, file.toString(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                FileOutputStream fOut = new FileOutputStream(file);
                OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);

//                myOutWriter.append("No.1, No.2, No.4, No.5, No.6, No.9, No.11, No.12, No.13, No.14, No.15, No.16, No.17, No.18, No.19 \n");
                myOutWriter.append("No.1, No.2, No.3\n");//minor編號

//                myOutWriter.append(c1.getSize() + "," +c2.getSize() + "," +c4.getSize() + "," +c5.getSize() + "," +c6.getSize() + "," +c9.getSize() + "," +
//                        c11.getSize() + "," +c12.getSize() + ","+c13.getSize() + "," +c14.getSize() + "," +c15.getSize() + "," +c16.getSize() + "," +
//                        c17.getSize() + "," +c18.getSize() + "," +c19.getSize() + "\n");
                myOutWriter.append(c1.getSize() + "," + c2.getSize() + "," + c3.getSize() + "\n");//蒐集訊號數

//                myOutWriter.append(c1.getAvg() + "," + c2.getAvg() + "," + c4.getAvg() + "," + c5.getAvg() + "," + c6.getAvg() + "," + c9.getAvg() + "," +
//                        c11.getAvg() + "," + c12.getAvg() + "," + c13.getAvg() + "," + c14.getAvg() + "," + c15.getAvg() + "," + c16.getAvg() + "," +
//                        c17.getAvg() + "," + c18.getAvg() + "," + c19.getAvg() + "\n");
                myOutWriter.append(c1.getAvg() + "," + c2.getAvg() + "," + c3.getAvg() + "\n");//訊號平均

//                myOutWriter.append(c1.getStandardD() + "," + c2.getStandardD() + "," + c4.getStandardD() + "," + c5.getStandardD() + "," +
//                        c6.getStandardD() + "," + c9.getStandardD() + "," + c11.getStandardD() + "," + c12.getStandardD() + "," +
//                        c13.getStandardD() + "," + c14.getStandardD() + "," + c15.getStandardD() + "," + c16.getStandardD() + "," +
//                        c17.getStandardD() + "," + c18.getStandardD() + "," + c19.getStandardD() + "\n");
                myOutWriter.append(c1.getStandardD() + "," + c2.getStandardD() + "," + c3.getStandardD() + "\n");//訊號標準差

//                myOutWriter.append(c1.getMax() + "," + c2.getMax() + "," + c4.getMax() + "," + c5.getMax() + "," + c6.getMax() + "," + c9.getMax() + "," +
//                        c11.getMax() + "," + c12.getMax() + "," + c13.getMax() + "," + c14.getMax() + "," + c15.getMax() + "," + c16.getMax() + "," +
//                        c17.getMax() + "," + c18.getMax() + "," + c19.getMax() + "\n");
                myOutWriter.append(c1.getMax() + "," + c2.getMax() + "," + c3.getMax() + "\n");//訊號最大值

//                myOutWriter.append(c1.getMin() + "," + c2.getMin() + "," + c4.getMin() + "," + c5.getMin() + "," + c6.getMin() + "," + c9.getMin() + "," +
//                        c11.getMin() + "," + c12.getMin() + "," + c13.getMin() + "," + c14.getMin() + "," + c15.getMin() + "," + c16.getMin() + "," +
//                        c17.getMin() + "," + c18.getMin() + "," + c19.getMin() + "\n");
                myOutWriter.append(c1.getMin() + "," + c2.getMin() + "," + c3.getMin() + "\n");

                myOutWriter.append("MaxBeacon, RSSI Sum, Choose Subspace" + "\n");
                myOutWriter.append(MAXbeaconNUM + "," + RSSISUM8Subspace + "," + chooseSubspace);

                myOutWriter.append("\n\nNo.1\n");
                for (int i = 0; i < c1.getSize(); i++) {
                    myOutWriter.append(c1.getindex(i) + ",");
                }
                myOutWriter.append("\nNo.2\n");
                for (int i = 0; i < c2.getSize(); i++) {
                    myOutWriter.append(c2.getindex(i) + ",");
                }
                myOutWriter.append("\nNo.3\n");
                for (int i = 0; i < c3.getSize(); i++) {
                    myOutWriter.append(c3.getindex(i) + ",");
                }

                myOutWriter.close();
                fOut.close();

                Toast.makeText(this, "write", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getApplicationContext(), "NO USBeacon Signal", Toast.LENGTH_SHORT).show();
        }
    }

    private void cleanCountObj() {
//        c1 = null;
//        c2 = null;
//        c4 = null;
//        c5 = null;
//        c6 = null;
//        c9 = null;
//        c11 = null;
//        c12 = null;
//        c13 = null;
//        c14 = null;
//        c15 = null;
//        c16 = null;
//        c17 = null;
//        c18 = null;
//        c19 = null;
        c1 = null;
        c2 = null;
        c3 = null;
    }

    private void separateBeacon() {
//        c1 = new CountObj();
//        c2 = new CountObj();
//        c4 = new CountObj();
//        c5 = new CountObj();
//        c6 = new CountObj();
//        c9 = new CountObj();
//        c11 = new CountObj();
//        c12 = new CountObj();
//        c13 = new CountObj();
//        c14 = new CountObj();
//        c15 = new CountObj();
//        c16 = new CountObj();
//        c17 = new CountObj();
//        c18 = new CountObj();
//        c19 = new CountObj();
        c1 = new CountObj();
        c2 = new CountObj();
        c3 = new CountObj();

        for (int i = 0; i < wtdArray.size(); i++) {
            if (wtdArray.get(i).Minor == 1) {
                c1.inputArray(wtdArray.get(i).RSSI);
            }
            if (wtdArray.get(i).Minor == 2) {
                c2.inputArray(wtdArray.get(i).RSSI);
            }
            if (wtdArray.get(i).Minor == 3) {
                c3.inputArray(wtdArray.get(i).RSSI);
            }
//            if (wtdArray.get(i).Minor == 5) {
//                c5.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 6) {
//                c6.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 9) {
//                c9.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 11) {
//                c11.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 12) {
//                c12.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 13) {
//                c13.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 14) {
//                c14.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 15) {
//                c15.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 16) {
//                c16.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 17) {
//                c17.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 18) {
//                c18.inputArray(wtdArray.get(i).RSSI);
//            }
//            if (wtdArray.get(i).Minor == 19) {
//                c19.inputArray(wtdArray.get(i).RSSI);
//            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_calculate:
                if (!mrealx.getText().toString().equals("") && !mrealy.getText().toString().equals("") && calculation != null) {
                    double tmp = Math.sqrt(Math.pow(Double.parseDouble(mrealx.getText().toString()) - calculation.X, 2) + Math.pow(Double.parseDouble(mrealy.getText().toString()) - calculation.Y, 2));
                    if (tmp == 0) {
                        merrord.setText(0.1 + "");
                    } else {
                        merrord.setText(tmp + "");
                    }
                } else if (mrealx.getText().toString().equals("") && !mrealy.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Enter REAL position!", Toast.LENGTH_SHORT).show();
                } else if (calculation == null) {
                    Toast.makeText(getApplicationContext(), "Position First!", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.receive_bt:
                if (receive.isChecked()) {
                    drawView.draw = false;
                    COUNT = Integer.parseInt(mSetTime.getText().toString());
                    receive.setText(Integer.toString(COUNT));
                    wtdArray = null;
                    wtdArray = new ArrayList<WriteFileData>();
                    calculation = new Calculation();
                    if (!running) {

                        running = true;


                        timer = new CountDownTimer(COUNT * 1000, 1000) {

                            @Override
                            public void onFinish() {
                                separateBeacon();//初始化
                                positioning();//定位

                                if (Ifwrite.isChecked()) {//是否寫檔
                                    writeFile();
                                }
                                //-------嗶生提醒量測完畢
                                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME);
                                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                                //-------------------------------------------
                                running = false;
                                receive.setChecked(false);
                                receive.setText(Integer.toString(COUNT));
                                manger.notify(1, notification);
                                cleanCountObj();
                            }

                            @Override
                            public void onTick(long millisUntilFinished) {
                                receive.setText(Long.toString(millisUntilFinished / 1000));
                            }

                        }.start();
                    }
                } else {
                    timer.cancel();
                    receive.setChecked(false);
                    receive.setText(Integer.toString(COUNT));
                    running = false;
                }
                break;
        }
    }

    private void positioning() {
//        double[] calbeacon = {c1.getAvg(), c2.getAvg(), c4.getAvg(),
//                c5.getAvg(), c6.getAvg(), c9.getAvg(), c11.getAvg(),
//                c12.getAvg(), c13.getAvg(), c14.getAvg(), c15.getAvg(),
//                c16.getAvg(), c17.getAvg(), c18.getAvg(), c19.getAvg()};
        double[] calbeacon = {c1.getAvg(), c2.getAvg(), c3.getAvg()};

//			MAXbeaconNUM = calculation.calculateMAXbeacon(calbeacon); //取最大Beacon訊號值所在當選擇的子區域
//			Log.v("p", "MAXbeaconNUM:" + MAXbeaconNUM);

        SubspceFingerPrint = calculation.CustomSubspceFingerPrint(calbeacon); //整個場域的fingerprint加選子區域
        Log.v("p", "FingerPrint:" + SubspceFingerPrint);

//			ThresholdPass = calculation.calculateThreshold(MAXbeaconNUM, calbeacon);
//			Log.v("p", "ThresholdPass:" + ThresholdPass);
//			if (!ThresholdPass) { //
//				RSSISUM8Subspace = calculation.calculate8SubspaceSUM(calbeacon); //計算8個子區域各個子區域的訊號值總合，取最大的為選擇的子區域
//				 Log.v("p", "RSSISUM8Subspace:" + RSSISUM8Subspace);

//				chooseSubspace = calculation.calculateSubspace(MAXbeaconNUM, RSSISUM8Subspace, calbeacon);
//				Log.v("p", "ChooseSubspace:" + chooseSubspace);

//				calculation.calculatePosition(chooseSubspace, calbeacon);
//				calculation.calculatePosition(SubspceFingerPrint, calbeacon); //KNN+三角定位
//			}else{
        calculation.setFingerPrintPosition();
//			}
        Log.v("p", "X:" + calculation.X + " Y:" + calculation.Y);

        drawView.X = 100 + calculation.X * 50;
        drawView.Y = 275 - calculation.Y * 50;
        mestimates_x.setText(calculation.X + "");
        mestimates_y.setText(calculation.Y + "");

        drawView.draw = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScanner.scanLeDevice(-1, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        receive.setText(Integer.toString(COUNT));
        invalidateOptionsMenu();
    }

    private void startScan() {
        final boolean mIsBluetoothOn = mBluetoothUtils.isBluetoothOn();
        final boolean mIsBluetoothLePresent = mBluetoothUtils.isBluetoothLeSupported();
        mDeviceStore.clear();
        updateItemCount(0);

        FILENAME = "/RSSI_" + sdf.format(System.currentTimeMillis());
        Log.v("p", FILENAME);

        mLeDeviceListAdapter = new LeDeviceListAdapter(this, mDeviceStore.getDeviceCursor());
        mList.setAdapter(mLeDeviceListAdapter);

        mBluetoothUtils.askUserToEnableBluetoothIfNeeded();
        if (mIsBluetoothOn && mIsBluetoothLePresent) {
            mScanner.scanLeDevice(-1, true);
            invalidateOptionsMenu();
        }
    }

    private void updateItemCount(final int count) {
        mTvItemCount.setText(
                getString(
                        R.string.formatter_item_count,
                        String.valueOf(count)));
    }

}
