package learn.loocation.data;

import learn.loocation.models.Bathroom;

import java.util.List;

public interface BathroomRepository {
    List<Bathroom> findAllBathrooms();

    Bathroom findBathroomById(int bathroomId);
    Bathroom findByAddressIdAndName(int addressId, String name);
    Bathroom addBathroom(Bathroom bathroom);

    boolean updateBathroom(Bathroom bathroom);

    boolean deleteBathroomById(int bathroomId);
}