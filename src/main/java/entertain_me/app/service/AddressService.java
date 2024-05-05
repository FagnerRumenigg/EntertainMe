package entertain_me.app.service;

import entertain_me.app.dto.user.AddressDto;
import entertain_me.app.model.Address;
import entertain_me.app.model.User;
import entertain_me.app.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    private AddressRepository addressRepository;

    public void saveAddress(AddressDto addressDto, User user){
        Optional<Address> newAddress = addressRepository.findById(addressDto.address_id() != null ? addressDto.address_id() : 0);

        newAddress.ifPresent(existingAddress ->{
            existingAddress.setUser(user);
            existingAddress.setCep(addressDto.cep());
            existingAddress.setCity(addressDto.city());
            existingAddress.setEstate(addressDto.estate());
            existingAddress.setCountry(addressDto.country());
            existingAddress.setStreet(addressDto.street());
            addressRepository.save(existingAddress);

        });


    }

}
