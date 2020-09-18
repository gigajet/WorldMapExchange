package com.example.worldmapexchange;

import org.junit.Test;
import static org.junit.Assert.*;

public class UnitTestM {
    @Test
    public void StringAddInt () {   //is "list"+3 equal to "list3" ?
        String x="list";
        int y=3;
        assertEquals("list3",x+y);
        assertEquals("list0", "list"+0);
    }
}
