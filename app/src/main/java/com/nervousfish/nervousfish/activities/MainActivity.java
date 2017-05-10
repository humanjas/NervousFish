package com.nervousfish.nervousfish.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.nervousfish.nervousfish.ConstantKeywords;
import com.nervousfish.nervousfish.R;
import com.nervousfish.nervousfish.data_objects.Contact;
import com.nervousfish.nervousfish.data_objects.SimpleKey;
import com.nervousfish.nervousfish.modules.database.IDatabase;
import com.nervousfish.nervousfish.service_locator.IServiceLocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main activity class that shows a list of all people with their public keys
 */
@SuppressWarnings("PMD.AtLeastOneConstructor")
public final class MainActivity extends AppCompatActivity {

    private IServiceLocator serviceLocator;

    /**
     * Creates the new activity, should only be called by Android
     *
     * @param savedInstanceState Don't touch this
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @SuppressWarnings("PMD.MethodCommentRequirement")
            @Override
            public void onClick(final View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        final ListView lv = (ListView) findViewById(R.id.listView);

        final Intent intent = getIntent();
        serviceLocator = (IServiceLocator) intent.getSerializableExtra(ConstantKeywords.SERVICE_LOCATOR);

        fillDatabaseWithDemoData();

        try {
            lv.setAdapter(new ContactListAdapter(this, serviceLocator.getDatabase().getAllContacts()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Temporarily fill the database with demo data for development.
     */
    private void fillDatabaseWithDemoData() {
        IDatabase database = serviceLocator.getDatabase();
        try {
            Contact c = new Contact("Eric", new SimpleKey("jdfs09jdfs09jfs0djfds9jfsd0"));
            database.deleteContact(c);
            database.addContact(c);
            c = new Contact("Stas", new SimpleKey("4ji395j495i34j5934ij534i"));
            database.deleteContact(c);
            database.addContact(c);
            c = new Contact("Joost", new SimpleKey("dnfh4nl4jknlkjnr4j34klnk3j4nl"));
            database.deleteContact(c);
            database.addContact(c);
            c = new Contact("Kilian", new SimpleKey("sdjnefiniwfnfejewjnwnkenfk32"));
            database.deleteContact(c);
            database.addContact(c);
            c = new Contact("Cornel", new SimpleKey("nr23uinr3uin2o3uin23oi4un234ijn"));
            database.deleteContact(c);
            database.addContact(c);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


class ContactListAdapter extends ArrayAdapter<Contact> {

    public ContactListAdapter(final Context context, final List<Contact> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            final LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.contact_list_entry, null);
        }

        final Contact contact = getItem(position);

        if (contact != null) {
            final TextView name = (TextView) v.findViewById(R.id.name);
            final TextView pubKey = (TextView) v.findViewById(R.id.pubKeySnippet);

            if (name != null) {
                name.setText(contact.getName());
            }

            if (pubKey != null) {
                final String publicKey = contact.getPublicKey().getKey();
                if(publicKey.length() > 30) {
                    final String pubKeySnippet = contact.getPublicKey().getKey().substring(0, 30) + "...";
                    pubKey.setText(pubKeySnippet);
                } else {
                    pubKey.setText(publicKey);
                }
            }
        }

        return v;
    }

}