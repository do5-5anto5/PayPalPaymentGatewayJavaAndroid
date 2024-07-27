# PayPal Payment Gateway Demo

This project is a simple demonstration of how to integrate PayPal's payment gateway into an Android application using Java.

## Prerequisites

- Android Studio
- PayPal developer account
- PayPal Client ID (obtained from the Sandbox environment)

## Setup

1. Clone this repository.
2. Open the project in Android Studio.
3. Replace the `clientId` value in the `MainActivity` class with your PayPal Client ID.

## Running

1. Run the application on an emulator or physical device.
2. Enter an amount in the text field and click the "Pay" button.
3. You will be redirected to PayPal's PaymentActivity to complete the simulated payment.
4. After completing (or canceling) the payment, a success or error message will be displayed in the app.

## Notes

- This project uses PayPal's Sandbox environment, which allows you to test the payment flow without using real money.
- To use the payment gateway in production, you will need to migrate toPayPal's live environment and use your live credentials.

## Contributions

Contributions are welcome! Feel free to open issues or pull requests.