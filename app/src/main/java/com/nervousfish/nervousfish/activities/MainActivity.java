package com.nervousfish.nervousfish.activities;

import com.nervousfish.nervousfish.data_objects.Contact;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.nervousfish.nervousfish.ConstantKeywords;
import com.nervousfish.nervousfish.R;
import com.nervousfish.nervousfish.data_objects.IKey;
import com.nervousfish.nervousfish.data_objects.SimpleKey;
import com.nervousfish.nervousfish.modules.database.IDatabase;
import com.nervousfish.nervousfish.service_locator.IServiceLocator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The main activity class that shows a list of all people with their public keys
 */
public final class MainActivity extends AppCompatActivity {

    private static final Logger LOGGER = LoggerFactory.getLogger("MainActivity");

    private IServiceLocator serviceLocator;
    private List<Contact> contacts;
    private static final int NUMBER_OF_SORTING_MODES = 2;
    private static final int SORT_BY_NAME = 0;
    private static final int SORT_BY_KEY_TYPE = 1;
    private Integer currentSorting = 0;


    private Comparator<Contact> nameSorter = new Comparator<Contact>() {
        @Override
        public int compare(Contact o1, Contact o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };


    /**
     * Creates the new activity, should only be called by Android
     *
     * @param savedInstanceState Don't touch this
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        this.serviceLocator = (IServiceLocator) intent.getSerializableExtra(ConstantKeywords.SERVICE_LOCATOR);
        this.setContentView(R.layout.activity_main);

        try {
            fillDatabaseWithDemoData();
            this.contacts = serviceLocator.getDatabase().getAllContacts();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onClick(final View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        sortOnName();

        final FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onClick(final View view) {
                switchSorting();
            }

        });

        LOGGER.info("MainActivity created");
    }

    /**
     * Temporary method to open the {@link ContactActivity} for a contact.
     *
     * @param index The index of the contact in {@code this.contacts}.
     */
    private void openContact(final int index) {
        final Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra(ConstantKeywords.SERVICE_LOCATOR, this.serviceLocator);
        intent.putExtra(ConstantKeywords.CONTACT, this.contacts.get(index));
        this.startActivity(intent);
    }

    /**
     * Temporarily fill the database with demo data for development.
     */
    private void fillDatabaseWithDemoData() throws IOException {
        final IDatabase database = this.serviceLocator.getDatabase();
        try {
            final Collection<IKey> keys = new ArrayList<>();
            keys.add(new SimpleKey("Webmail", "jdfs09jdfs09jfs0djfds9jfsd0"));
            keys.add(new SimpleKey("Webserver", "jasdgoijoiahl328hg09asdf322"));
            final Contact a = new Contact("Eric", keys);
            final Contact b = new Contact("Stas", new SimpleKey("FTP", "4ji395j495i34j5934ij534i"));
            final Contact c = new Contact("Joost", new SimpleKey("Webserver", "dnfh4nl4jknlkjnr4j34klnk3j4nl"));
            final Contact d = new Contact("Kilian", new SimpleKey("Webmail", "sdjnefiniwfnfejewjnwnkenfk32"));
            final Contact e = new Contact("Cornel", new SimpleKey("Awesomeness", "nr23uinr3uin2o3uin23oi4un234ijn"));
            if (!database.getAllContacts().isEmpty()) {
                database.deleteContact(a);
                database.deleteContact(b);
                database.deleteContact(c);
                database.deleteContact(d);
                database.deleteContact(e);
            }

            database.addContact(a);
            database.addContact(b);
            database.addContact(c);
            database.addContact(d);
            database.addContact(e);
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all types of keys in the database
     *
     * @return a List with the types of keys.
     */
    private List<String> getKeyTypes() {
        Set<String> typeSet = new HashSet<>();
        for (Contact c : contacts) {
            for (IKey k : c.getKeys()) {
                typeSet.add(k.getType());
            }
        }
        return new ArrayList<>(typeSet);
    }

    /**
     * Switches the sorting mode.
     */
    private void switchSorting() {
        currentSorting++;
        if (currentSorting >= NUMBER_OF_SORTING_MODES) {
            currentSorting = 0;
        }
        final ViewFlipper flipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        flipper.showNext();
        switch (currentSorting) {
            case SORT_BY_NAME:
                sortOnName();
                break;
            case SORT_BY_KEY_TYPE:
                sortOnKeyType();
                break;

        }
    }

    /**
     * Sorts contacts by name
     */
    private void sortOnName() {
        final ListView lv = (ListView) findViewById(R.id.listView);
        final ContactListAdapter contactListAdapter = new ContactListAdapter(this, this.contacts);
        contactListAdapter.sort(nameSorter);
        lv.setAdapter(contactListAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int index, final long id) {
                openContact(index);
            }

        });

    }

    /**
     * Sorts contacts by key type
     */
    private void sortOnKeyType() {
        final ExpandableListView ev = (ExpandableListView) findViewById(R.id.expandableListView);
        final ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter(this, getKeyTypes(), contacts);
        ev.setAdapter(expandableListAdapter);
        ev.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            /**
             * {@inheritDoc}
             */
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int index, final long id) {
                openContact(index);
            }

        });

    }

}

/**
 * An Adapter which converts a list with contacts into List entries.
 */
final class ContactListAdapter extends ArrayAdapter<Contact> {

    /**
     * Create and initialize a ContactListAdapter.
     *
     * @param context  the Context where the ListView is created
     * @param contacts the list with contacts
     */
    ContactListAdapter(final Context context, final List<Contact> contacts) {
        super(context, 0, contacts);
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public View getView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            final LayoutInflater vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.contact_list_entry, null);
        }

        final Contact contact = getItem(position);

        if (contact != null) {
            final TextView name = (TextView) v.findViewById(R.id.name);

            if (name != null) {
                name.setText(contact.getName());
            }
        }

        return v;
    }

}


final class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Map<String, List<Contact>> groupedContacts;
    private List<String> types;
    private Activity context;

    public ExpandableListAdapter(Activity context, List<String> types, List<Contact> contacts) {
        this.context = context;
        this.types = types;
        groupedContacts = new HashMap<>();
        for (String type : types) {
            groupedContacts.put(type, new ArrayList<Contact>());
        }
        for (Contact contact : contacts) {
            for (String type : types) {
                if (!groupedContacts.get(type).contains(contact)) {
                    groupedContacts.get(type).add(contact);
                }
            }
        }


    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return groupedContacts.get(types.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final Contact contact = (Contact) getChild(groupPosition, childPosition);
        View v = convertView;

        if (v == null) {
            final LayoutInflater vi = context.getLayoutInflater();
            v = vi.inflate(R.layout.contact_list_entry, null);
        }


        if (contact != null) {
            final TextView name = (TextView) v.findViewById(R.id.name);

            if (name != null) {
                name.setText(contact.getName());
            }
        }

        return v;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groupedContacts.get(types.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return types.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return types.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String type = (String) getGroup(groupPosition);
        if (convertView == null) {
            final LayoutInflater vi = context.getLayoutInflater();
            convertView = vi.inflate(R.layout.key_type, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.type);
        item.setTypeface(null, Typeface.BOLD);
        item.setText("Keytype: "+type);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


