package com.bl.addressbook.controller;

import com.bl.addressbook.model.Contact;
import com.bl.addressbook.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController

public class ContactController {

    @Autowired
    ContactService contactService;

    //http:localhost/address-book/ADDRESS_BOOK_1/contacts
    @PostMapping("/{addressBookId}/contacts")
    public Contact addContactToAddressBook(
            @PathVariable String addressBookId, @Valid @RequestBody Contact newContact) {
        return contactService.addContact(addressBookId, newContact);
    }

    //http:localhost/address-book/ADDRESS_BOOK_1/contacts/1
    @DeleteMapping("/{addressBookId}/contacts/{contactId}")
    public Contact removeContactFromAddressBook(@PathVariable String addressBookId,
                                                @PathVariable String contactId) {
        return contactService.removeContact(addressBookId, contactId);
    }

    //http:localhost/address-book/ADDRESS_BOOK_1/contacts
    @GetMapping("/{addressBookId}/contacts")
    public List<Contact> retrieveContactsFromAddressBook(@PathVariable String addressBookId) {
        return contactService.retrieveContacts(addressBookId);
    }

    @GetMapping("/contacts")
    public List<Contact> retrieveUniqueContactsFromAllAddressBooks(@RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        return contactService.retrieveAllUniqueContacts(unique);
    }
}