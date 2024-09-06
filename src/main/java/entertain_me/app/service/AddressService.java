package entertain_me.app.service;

import entertain_me.app.dto.user.AddressDto;
import entertain_me.app.model.Address;
import entertain_me.app.model.User;
import entertain_me.app.repository.AddressRepository;
import entertain_me.app.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveAddress(AddressDto addressDto) {
        Optional<Address> address = addressRepository.findById(addressDto.addressId());
        Optional<User> user = userRepository.findById(addressDto.userId());

        address.ifPresentOrElse(existingAddress -> {
            existingAddress.setCep(addressDto.cep());
            existingAddress.setCity(addressDto.city());
            existingAddress.setEstate(addressDto.estate());
            existingAddress.setCountry(addressDto.country());
            existingAddress.setStreet(addressDto.street());

            addressRepository.save(existingAddress);
        }, () -> {
            Address newAddress = new Address();

            user.ifPresentOrElse(newAddress::setUser, () ->{
                throw new UsernameNotFoundException("User not found");
            });

            newAddress.setCep(addressDto.cep());
            newAddress.setCity(addressDto.city());
            newAddress.setEstate(addressDto.estate());
            newAddress.setCountry(addressDto.country());
            newAddress.setStreet(addressDto.street());

            addressRepository.save(newAddress);
        });
    }
}
