package com.bl.addressbook.service;

import com.bl.addressbook.exceptions.ValidationException;
import com.bl.addressbook.model.Contact;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private static final String ADDRESS_BOOK_1 = "address-book-1";
    private static final String ADDRESS_BOOK_2 = "address-book-2";

    private final Map<String, List<Contact>> addressBooks = new HashMap<>();

    public ContactService() {
        addressBooks.put(ADDRESS_BOOK_1, new ArrayList<>());
        addressBooks.put(ADDRESS_BOOK_2, new ArrayList<>());

        this.addContact(ADDRESS_BOOK_1, new Contact("Abhinav","Thakur" , "at@gmail.com" ,"Delhi", Arrays.asList("9013341138")));
        this.addContact(ADDRESS_BOOK_1, new Contact("Arpit","Thakur" , "at2@gmail.com" ,"Delhi", Arrays.asList("9013341139")));
        this.addContact(ADDRESS_BOOK_2, new Contact("Prajwal","Rao" , "pr@gmail.com" ,"Karnataka", Arrays.asList("9013341140")));
        this.addContact(ADDRESS_BOOK_2, new Contact("Manmeet","Jha" , "mj@gmail.com" ,"Delhi", Arrays.asList("9013341141")));
    }

    public Contact addContact(String addressBookId, Contact contact) {
        contact.setId(UUID.randomUUID().toString());
        if (!addressBooks.containsKey(addressBookId)) {
            addressBooks.put(addressBookId, new ArrayList<>());
        }
        addressBooks.get(addressBookId).add(contact);
        return contact;
    }

    public Contact removeContact(String addressBookId, final String contactId) {
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ValidationException("AddressBook with the given Id does not exist");
        }
        Optional<Contact> contactToBeRemoved = addressBooks.get(addressBookId).stream()
                .filter(contact -> contact.getId().equals(contactId)).findFirst();
        if (contactToBeRemoved.isPresent()) {
            addressBooks.get(addressBookId).remove(contactToBeRemoved.get());
            return contactToBeRemoved.get();
        } else {
            throw new ValidationException("The given contact does not exist!");
        }
    }

    public List<Contact> retrieveContacts(String addressBookId) {
        if (!addressBooks.containsKey(addressBookId)) {
            throw new ValidationException("AddressBook with the given Id does not exist");
        }
        return addressBooks.get(addressBookId);
    }

    public List<Contact> retrieveAllUniqueContacts(boolean unique) {
        // assumed that the contact is unique it's name and numbers matches
        List<Contact> contacts = new ArrayList<>();
        addressBooks.values().forEach(contacts::addAll);
        if (unique) {
            return contacts.stream().distinct().collect(Collectors.toList());
        }
        return contacts;
    }
}