package edu.westga.jeffrichardsdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class DatabaseActivity extends AppCompatActivity {

    private TextView idView;
    private EditText productBox;
    private EditText quantityBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        this.idView = (TextView) findViewById(R.id.productID);
        this.productBox = (EditText) findViewById(R.id.productName);
        this.quantityBox = (EditText) findViewById(R.id.productQuantity);
    }

    public void newProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        int quantity;
        try {
            quantity = Integer.parseInt(quantityBox.getText().toString());
        } catch (NumberFormatException e) {
            quantity = 0;
        }

        Product product = new Product(productBox.getText().toString(), quantity);

        dbHandler.addProduct(product);
        idView.setText(getResources().getString(R.string.record_added));
        productBox.setText("");
        quantityBox.setText("");
    }

    public void lookupProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        Product product =
                dbHandler.findProduct(productBox.getText().toString());

        if (product != null) {
            idView.setText(String.valueOf(product.getID()));

            quantityBox.setText(String.valueOf(product.getQuantity()));
        } else {
            idView.setText(getResources().getString(R.string.no_match_found));
        }
    }

    public void removeProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);

        boolean result = dbHandler.deleteProduct(
                productBox.getText().toString());

        if (result)
        {
            idView.setText(getResources().getString(R.string.record_deleted));
            productBox.setText("");
            quantityBox.setText("");
        }
        else
            idView.setText(getResources().getString(R.string.no_match_found));
    }

    public void updateProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null,
                null, 1);
        int productId;
        int quantity;
        try{
            productId = Integer.parseInt(idView.getText().toString());
            quantity = Integer.parseInt(quantityBox.getText().toString());
        } catch (NumberFormatException e) {
            idView.setText(getResources().getString(R.string.invalid_update));
            return;
        }

        Product product = new Product(productId, productBox.getText().toString(), quantity);
        boolean result = dbHandler.updateProduct(product);

        if (result)
        {
            idView.setText(getResources().getString(R.string.record_updated));
        }
        else
        {
            idView.setText(getResources().getString(R.string.no_match_found));
        }
    }

    public void deleteAllProduct (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        int count = dbHandler.deleteAllProduct();
        idView.setText("" + count + " " + getResources().getString(R.string.all_deleted));
        productBox.setText("");
        quantityBox.setText("");
    }
}
