package learn.loocation.data;

import learn.loocation.models.Bathroom;

import java.util.List;

public interface BathroomRepository {
    List<Bathroom> findAll();

    Bathroom findById(int bathroomId);

    Bathroom add(Bathroom bathroom);

    boolean update(Bathroom bathroom);

    boolean deleteById(int bathroomId);
}