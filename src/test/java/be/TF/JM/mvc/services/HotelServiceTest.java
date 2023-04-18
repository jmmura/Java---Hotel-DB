package be.TF.JM.mvc.services;

import be.TF.JM.mvc.models.Hotel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelServiceTest {

    private final HotelService service = new HotelService(new RoomService());

    @Test
    void getById_validId() {
        Hotel h = service.getById(1);

        assertNotNull(h);
    }
}
