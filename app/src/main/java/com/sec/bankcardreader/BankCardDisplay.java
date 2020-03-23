package com.sec.bankcardreader;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.sec.bankcardreader.creditCardNfcReader.CardNfcAsyncTask;
import android.widget.Toast;

import com.sec.bankcardreader.creditCardNfcReader.CardNfcAsyncTask;
import com.sec.bankcardreader.creditCardNfcReader.utils.CardNfcUtils;

public class BankCardDisplay extends AppCompatActivity implements CardNfcAsyncTask.CardNfcInterface{
    private NfcAdapter NfcAndroidAdapter;
    private PendingIntent NfcIntent;
    private  TextView mTextView;
    private CardNfcUtils mCardNfcUtils;
    private CardNfcAsyncTask mCardNfcAsyncTask;

    public static final String MIME_TEXT_PLAIN = "text/plain";
    public static final String TAG = "NfcDemo";
    private boolean mIntentFromCreate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_display);

         mTextView = (TextView) findViewById(R.id.textID);

        NfcAndroidAdapter = NfcAdapter.getDefaultAdapter(this);
         mCardNfcUtils = new CardNfcUtils(this);
        //next few lines here needed in case you will scan credit card when app is closed

        mIntentFromCreate = true;
        handleIntent(getIntent());


    }
    protected void onPause() {


        super.onPause();
        mCardNfcUtils.disableDispatch();
    }
    public void onResume() {


        mCardNfcUtils.enableDispatch();
        super.onResume();
    }
    private void handleIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            String type = intent.getType();
            mTextView.setText("NDEF");

        } else if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {

            mTextView.setText("TECH");
            if (NfcAndroidAdapter != null && NfcAndroidAdapter.isEnabled()) {
                //this - interface for callbacks
                //intent = intent :)
                //mIntentFromCreate - boolean flag, for understanding if onNewIntent() was called from onCreate or not
                 mCardNfcAsyncTask = new CardNfcAsyncTask.Builder(this, intent, mIntentFromCreate)
                        .build();
            }
        }
    }
    public void onNewIntent(Intent intent) {

        super.onNewIntent(intent);

        Log.v("TAP", "onNewIntent");
        handleIntent(intent);
    }

    public static void setupForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        final Intent intent = new Intent(activity.getApplicationContext(), activity.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        final PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(), 0, intent, 0);

        IntentFilter[] filters = new IntentFilter[1];
        String[][] techList = new String[][]{};

        // Notice that this is the same filter as in our manifest.
        filters[0] = new IntentFilter();
        filters[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filters[0].addCategory(Intent.CATEGORY_DEFAULT);
        try {
            filters[0].addDataType(MIME_TEXT_PLAIN);
        } catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
        adapter.enableForegroundDispatch(activity, pendingIntent, filters, techList);
    }

    public static void stopForegroundDispatch(final Activity activity, NfcAdapter adapter) {
        adapter.disableForegroundDispatch(activity);
    }
    @Override
    public void startNfcReadCard() {

    }

    @Override
    public void cardIsReadyToRead() {
        String card = mCardNfcAsyncTask.getCardNumber();
        String expiredDate = mCardNfcAsyncTask.getCardExpireDate();
        String cardType = mCardNfcAsyncTask.getCardType();
        mTextView = (TextView) findViewById(R.id.textID);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setText(" "+card);
        TextView mExpDate  = (TextView) findViewById(R.id.expDate);
        mExpDate.setTextColor(Color.WHITE);
        mExpDate.setText(" "+expiredDate);


    }

    @Override
    public void doNotMoveCardSoFast() {

    }

    @Override
    public void unknownEmvCard() {

    }

    @Override
    public void cardWithLockedNfc() {

    }

    @Override
    public void finishNfcReadCard() {

    }
}

