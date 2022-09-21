package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.repository.AddressRepository;
import com.mycompany.myapp.service.AddressService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Address}.
 */
@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        log.debug("Request to save Address : {}", address);
        return addressRepository.save(address);
    }

    @Override
    public Address update(Address address) {
        log.debug("Request to update Address : {}", address);
        return addressRepository.save(address);
    }

    @Override
    public Optional<Address> partialUpdate(Address address) {
        log.debug("Request to partially update Address : {}", address);

        return addressRepository
            .findById(address.getId())
            .map(existingAddress -> {
                if (address.getTitleUz() != null) {
                    existingAddress.setTitleUz(address.getTitleUz());
                }
                if (address.getTitleRu() != null) {
                    existingAddress.setTitleRu(address.getTitleRu());
                }
                if (address.getTitleKr() != null) {
                    existingAddress.setTitleKr(address.getTitleKr());
                }
                if (address.getContentUz() != null) {
                    existingAddress.setContentUz(address.getContentUz());
                }
                if (address.getContentRu() != null) {
                    existingAddress.setContentRu(address.getContentRu());
                }
                if (address.getContentKr() != null) {
                    existingAddress.setContentKr(address.getContentKr());
                }

                return existingAddress;
            })
            .map(addressRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findAll() {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        return addressRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        addressRepository.deleteById(id);
    }
}
