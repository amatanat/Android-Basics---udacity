package com.am.inventory;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.am.inventory.data.ProductContract.ProductEntry;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri mContentUri;
    private Uri mImageUri;

    private Button mIncrementQuantityButton;
    private Button mDecrementQuantityButton;
    private Button mSaveProductButton;
    private Button mUploadProductImageButton;
    private EditText mProductNameEditText;
    private EditText mProductPriceEditText;
    private EditText mProductQuantityEditText;
    private EditText mProductSupplierEditText;
    private EditText mProductSupplierEmail;
    private ImageView mProductImage;

    private final int LOADER_INIT = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 200;
    public static final int GET_IMAGE_FROM_GALLERY = 3;

    private boolean mProductHasChanged = false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mProductHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mProductNameEditText = (EditText) findViewById(R.id.productName);
        mProductPriceEditText = (EditText) findViewById(R.id.productPrice);
        mProductQuantityEditText = (EditText) findViewById(R.id.productQuantity);
        mProductSupplierEditText = (EditText) findViewById(R.id.productSupplier);
        mProductSupplierEmail = (EditText) findViewById(R.id.supplierEmail);
        mProductImage = (ImageView) findViewById(R.id.productImage);

        mProductNameEditText.setOnTouchListener(mTouchListener);
        mProductPriceEditText.setOnTouchListener(mTouchListener);
        mProductQuantityEditText.setOnTouchListener(mTouchListener);
        mProductSupplierEditText.setOnTouchListener(mTouchListener);
        mProductSupplierEmail.setOnTouchListener(mTouchListener);

        mUploadProductImageButton = (Button) findViewById(R.id.uploadImage);
        mUploadProductImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
                mProductHasChanged = true;
            }
        });

        mIncrementQuantityButton = (Button) findViewById(R.id.incrementQuantity);
        mIncrementQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incrementProductQuantity();
            }
        });

        mDecrementQuantityButton = (Button) findViewById(R.id.decrementQuantity);
        mDecrementQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrementProductQuantity();
            }
        });

        mSaveProductButton = (Button) findViewById(R.id.save);
        mSaveProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProduct();
            }
        });

        mContentUri = getIntent().getData();
        if (mContentUri == null) {
            // fab button is clicked
            getSupportActionBar().setTitle(R.string.addNewProduct);
            invalidateOptionsMenu();
        } else {
            // ListView item is clicked
            getSupportActionBar().setTitle(R.string.editProduct);
            mUploadProductImageButton.setVisibility(View.GONE);
            getSupportLoaderManager().initLoader(LOADER_INIT, null, this);
        }

    }

    private void checkPermission() {
        // check if read external storage permission is granted or not
        // if not request that permission

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

        } else {
            startActivityForResult(new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_IMAGE_FROM_GALLERY);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    startActivityForResult(new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_IMAGE_FROM_GALLERY);
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == GET_IMAGE_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            mImageUri = data.getData();
            mProductImage.setImageURI(mImageUri);
            mProductImage.invalidate();
            mUploadProductImageButton.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        // If this is a new product, hide the "Delete" and "Order" menu items.
        if (mContentUri == null) {
            MenuItem deleteItem = menu.findItem(R.id.delete);
            deleteItem.setVisible(false);
            MenuItem orderItem = menu.findItem(R.id.order);
            orderItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.order:
                orderProduct();
                return true;
            case R.id.delete:
                showDeleteConfirmationDialog();
                return true;
            case android.R.id.home:
                if (!mProductHasChanged) {
                    NavUtils.navigateUpFromSameTask(DetailsActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButton =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(DetailsActivity.this);
                            }
                        };
                showUnsavedChangesDialog(discardButton);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void onBackPressed() {
        if (!mProductHasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButton =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, finish this activity
                        finish();
                    }
                };

        // show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButton);
    }

    private void incrementProductQuantity() {
        int quantity;
        String quantityString = mProductQuantityEditText.getText().toString().trim();
        if (TextUtils.isEmpty(quantityString)) {
            quantity = 0;
        } else {
            quantity = Integer.parseInt(quantityString);
            quantity++;
        }
        mProductQuantityEditText.setText(Integer.toString(quantity));
    }

    private void decrementProductQuantity() {
        int quantity;
        String quantityString = mProductQuantityEditText.getText().toString().trim();
        if (TextUtils.isEmpty(quantityString)) {
            quantity = 0;
        } else {
            quantity = Integer.parseInt(quantityString);
            if (quantity == 0) {
                Toast.makeText(this, R.string.negative_quantity_alert, Toast.LENGTH_SHORT).show();
            } else if (quantity < 0) {
                Toast.makeText(this, R.string.negative_quantity_alert, Toast.LENGTH_SHORT).show();
            } else {
                quantity--;
            }
        }
        mProductQuantityEditText.setText(Integer.toString(quantity));
    }

    private void saveProduct() {
        String productPrice = "";
        int productQuantity = 0;

        String productName = mProductNameEditText.getText().toString().trim();
        String productSupplier = mProductSupplierEditText.getText().toString().trim();

        String price = mProductPriceEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(price)) {
            productPrice = price;
        }

        String quantity = mProductQuantityEditText.getText().toString().trim();
        if (!TextUtils.isEmpty(quantity)) {
            productQuantity = Integer.parseInt(quantity);
        }

        String email = mProductSupplierEmail.getText().toString().trim();
        String image = "";
        if (mImageUri != null) {
            image = mImageUri.toString();
        }


        // if product name or supplier's value is empty then finish {@link DetailsActivity}
        if (TextUtils.isEmpty(productName) || TextUtils.isEmpty(productSupplier) || TextUtils.isEmpty(productPrice) ||
                TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please feel empty fields", Toast.LENGTH_SHORT).show();

        } else if (productQuantity == 0 ) {
            Toast.makeText(this, "Product quantity cannot be 0", Toast.LENGTH_SHORT).show();
        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(ProductEntry.COLUMN_PRODUCT_NAME, productName);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, productSupplier);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_PRICE, productPrice);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, productQuantity);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL, email);
            contentValues.put(ProductEntry.COLUMN_PRODUCT_PICTURE, image);


            if (mContentUri == null) {
                // if fab button is clicked then insert new product
                Uri insertedProductUri = getContentResolver().insert(ProductEntry.CONTENT_URI, contentValues);

                if (insertedProductUri == null) {
                    Toast.makeText(this, R.string.error_in_inserting, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, R.string.product_added, Toast.LENGTH_SHORT).show();
                }

            } else {
                // if ListView item is clicked then update product data
                int updatedProductsRowNumber = getContentResolver().update(mContentUri, contentValues, null, null);
                showToastMessage(updatedProductsRowNumber, "update");
            }
            finish();
        }
    }

    private void deleteProduct() {
        if (mContentUri != null) {
            int deletedRowNumber = getContentResolver().delete(mContentUri, null, null);
            showToastMessage(deletedRowNumber, "delete");
        }
        finish();
    }

    private void showToastMessage(int receivedRowNumber, String methodName) {
        if (receivedRowNumber == 0) {
            Toast.makeText(this, getString(R.string.product_failed) + " " + methodName + " product",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getString(R.string.product_success) + " " + methodName + " success",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void orderProduct() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:" + mProductSupplierEmail.getText().toString().trim()));
        intent.putExtra(Intent.EXTRA_SUBJECT,  "Order product");
        intent.putExtra(Intent.EXTRA_TEXT, "I want to order from " +
                mProductNameEditText.getText().toString().trim());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else {
            Toast.makeText(this, "No email app.", Toast.LENGTH_SHORT).show();
        }

    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_confirmation_message);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the product
                deleteProduct();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so close the alert dialog
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ProductEntry._ID,
                ProductEntry.COLUMN_PRODUCT_NAME,
                ProductEntry.COLUMN_PRODUCT_QUANTITY,
                ProductEntry.COLUMN_PRODUCT_PRICE,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER,
                ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL,
                ProductEntry.COLUMN_PRODUCT_PICTURE
        };
        return new CursorLoader(this, mContentUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

        // get user clicked listItem index from db
        // get text of that index from db
        // set text of edittext accordingly

        if (cursor.moveToNext()) {
            int nameIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_NAME);
            int priceIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PRICE);
            int quantityIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_SUPPLIER);
            int supplierEmailIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_SUPPLIER_EMAIL);
            int imageIndex = cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_PICTURE);

            String nameText = cursor.getString(nameIndex);
            String priceValue = cursor.getString(priceIndex);
            int quantityValue = cursor.getInt(quantityIndex);
            String supplierText = cursor.getString(supplierIndex);
            String supplierEmail = cursor.getString(supplierEmailIndex);
            String imageUri = cursor.getString(imageIndex);

            if (TextUtils.isEmpty(imageUri)) {
                mUploadProductImageButton.setVisibility(View.VISIBLE);
            }

            mProductNameEditText.setText(nameText);
            mProductPriceEditText.setText(priceValue);
            mProductQuantityEditText.setText(Integer.toString(quantityValue));
            mProductSupplierEditText.setText(supplierText);
            mProductSupplierEmail.setText(supplierEmail);
            mImageUri = Uri.parse(imageUri);
            mProductImage.setImageURI(mImageUri);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // reset editTexts' value
        mProductNameEditText.setText("");
        mProductPriceEditText.setText("");
        mProductSupplierEditText.setText("");
        mProductQuantityEditText.setText("");
        mProductSupplierEmail.setText("");

    }
}
