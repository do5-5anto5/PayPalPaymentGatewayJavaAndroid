package com.example.paypalpaymentgatewayjava;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    EditText edtAmount;
    Button btnPayment;

    String clientId = "Mock Client ID";
    int PAYPAL_REQUEST_CODE = 123;
    public static PayPalConfiguration configuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtAmount = findViewById(R.id.edtAmount);
        btnPayment = findViewById(R.id.btnPay);

        // Create PayPalConfiguration object with necessary settings
        configuration = new PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(clientId);

        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPayment();
            }
        });
    }

    // Create getPayment() method to initiate the payment process
    private void getPayment() {

        String amount = edtAmount.getText().toString();

        // Create PayPalPayment object with payment details
        PayPalPayment payment = new PayPalPayment(new BigDecimal(
                String.valueOf(amount)),
                "USD", "Code with Arvind",
                PayPalPayment.PAYMENT_INTENT_SALE
        );

        //  Create Intent to launch PayPal's PaymentActivity
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        //  Called startActivityForResult() to start the payment activity and await the result
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    //  Implement onActivityResult()method to receive the result from PaymentActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  Check result code and extracted PaymentConfirmation object
        if (requestCode == PAYPAL_REQUEST_CODE) {

            if (data != null) {
                PaymentConfirmation paymentConfirmation = data.getParcelableExtra(
                        PaymentActivity.EXTRA_RESULT_CONFIRMATION
                );

                if (paymentConfirmation != null) {
                    String paymentDetails = paymentConfirmation.toJSONObject().toString();

                    try {
                        JSONObject object = new JSONObject(paymentDetails);
                    } catch (JSONException e) {
                        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                //  Display success or error messages based on payment result
                else if (requestCode == Activity.RESULT_CANCELED) {

                    Toast.makeText(this, "Error, Payment Cancelled", Toast.LENGTH_SHORT).show();

                }
            } else if (requestCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                Toast.makeText(this, "Error, Invalid Payment", Toast.LENGTH_SHORT).show();

            }
        }
    }
}