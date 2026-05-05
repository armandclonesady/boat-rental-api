package com.java.tp.boat.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.java.tp.boat.rental.model.business.Boat;
import com.java.tp.boat.rental.model.entity.BoatTypes;

public class BoatTest {
    
    Boat boat1;

    @BeforeEach
    public void setup() {
        boat1 = new Boat(1L, "Cavoboat", BoatTypes.MOTOR, 5, 420F, 150D, 20D, true);
    }

    @Test
    public void testUpdateWithOverridesWhenValueIsPresent() {
        Boat boat2 = new Boat(2L, "Knot Working", BoatTypes.SAILING, 6, 450F, 200D, 25D, false);
        boat1.updateWith(boat2);
        assert(boat1.getName().equals("Knot Working"));
        assert(boat1.getType().equals(BoatTypes.SAILING));
        assert(boat1.getMaxCapacity().equals(6));
        assert(boat1.getLength().equals(450F));
        assert(boat1.getDailyRate().equals(200D));
        assert(boat1.getDeposit().equals(25D));
        assert(boat1.getNeedsLicense().equals(false));
    }

    @Test
    public void testUpdateWithOverridesOnlyValuesThatArePresent() {
        Boat boat2 = new Boat(2L, "Knot Working", BoatTypes.SAILING, null, null, null, null, null);
        boat1.updateWith(boat2);
        assert(boat1.getName().equals("Knot Working"));
        assert(boat1.getType().equals(BoatTypes.SAILING));
        assert(boat1.getMaxCapacity().equals(5));
        assert(boat1.getLength().equals(420F));
        assert(boat1.getDailyRate().equals(150D));
        assert(boat1.getDeposit().equals(20D));
        assert(boat1.getNeedsLicense().equals(true));
    }
}
